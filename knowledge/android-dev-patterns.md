---
title: Android Development Patterns & Gotchas
category: reference
tags: [android, compose, kotlin, patterns, gotchas]
created: 2026-02-24
status: active
related: [android-phone-remote-control, app-agent-architecture]
---

# Android Development Patterns & Gotchas

Patterns and lessons learned from building Android apps with Compose. Shared reference for all app agents.

## Compose Lifecycle

### ActivityResultLauncher
```kotlin
// ❌ CRASH: Registered after STARTED state
val launcher = activity.registerForActivityResult(...)

// ✅ Use Compose API
val launcher = rememberLauncherForActivityResult(
    ActivityResultContracts.RequestPermission()
) { granted -> /* handle */ }
```
**Rule**: Never use `registerForActivityResult` in Activity when using Compose. Always use `rememberLauncherForActivityResult` inside `@Composable`.

### Side Effects
```kotlin
// ❌ init{} runs before Compose is ready
class MyViewModel : ViewModel() {
    init { loadData() } // OK for ViewModel
}

// ❌ Don't trigger side effects directly in composables
@Composable fun MyScreen() {
    viewModel.loadData() // Runs on every recomposition!
}

// ✅ Use LaunchedEffect for one-time side effects
@Composable fun MyScreen() {
    LaunchedEffect(Unit) { viewModel.loadData() }
}
```

### State Management
```kotlin
// ✅ Sealed interface for UI state
sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Success(val data: Weather) : WeatherUiState
    data class Error(val message: String) : WeatherUiState
}

// ✅ StateFlow in ViewModel
private val _uiState = MutableStateFlow<WeatherUiState>(Loading)
val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

// ✅ Collect in Compose
val state by viewModel.uiState.collectAsStateWithLifecycle()
```

## Location

### Strategy (priority order)
1. **`lastLocation`** — instant, may be null or stale
2. **`getCurrentLocation`** — accurate but slow (10-30s+)
3. **Fallback city** — hardcoded default when all else fails

### Timeouts
- **Minimum**: 30s for `getCurrentLocation`
- Indoor/no-SIM: GPS will almost always timeout
- Always implement fallback — don't let the app hang

### Permission Flow
```kotlin
// Check → Request → Handle result → Retry or fallback
// Use rememberLauncherForActivityResult for the request
// Check both COARSE and FINE location permissions
```

### Common Pitfalls
- `lastLocation` returns null if no app has recently requested location
- GPS indoor without cellular = no fix (satellite-only is slow/unreliable)
- Always provide user feedback during location fetch ("Buscando ubicación...")
- Silent fallback to default city = bad UX. Tell the user what happened.

## Hilt DI

### Module Setup Order
1. `NetworkModule` — Retrofit, OkHttp, API services
2. `DatabaseModule` — Room database, DAOs
3. `RepositoryModule` — Binds interface to implementation

### ViewModel Injection
```kotlin
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel()
```

## Material 3

### Required Files
- `ui/theme/Color.kt` — Color definitions
- `ui/theme/Type.kt` — Typography
- `ui/theme/Theme.kt` — Theme composable with dynamic colors

### Dynamic Colors (API 31+)
```kotlin
val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        if (darkTheme) dynamicDarkColorScheme(context)
        else dynamicLightColorScheme(context)
    }
    darkTheme -> DarkColorScheme
    else -> LightColorScheme
}
```

## Build Configuration

### Debug vs Release Values
```kotlin
// ✅ Use BuildConfig for environment-specific values
val timeout = if (BuildConfig.DEBUG) 5_000L else 30_000L

// ❌ Never hardcode test values with a "TODO: change later" comment
private const val TIMEOUT = 5_000L // TODO: increase for production  ← DANGER
```

## Testing on Device

### Pre-QA Checklist
1. Set screen timeout to 5min: `settings put system screen_off_timeout 300000`
2. Verify GPS/location services enabled (if needed)
3. Wake + unlock screen

### Post-QA Cleanup
1. Restore screen timeout: `settings put system screen_off_timeout 15000`
2. Sleep screen: `input keyevent KEYCODE_SLEEP`

### Screenshot Verification
1. Verify app is in foreground: `dumpsys window | grep mCurrentFocus`
2. Wait for UI to settle (Loading → Success/Error)
3. Capture: `screencap -p /sdcard/screen.png`
4. Transfer + analyze with image tool using specific QA prompts

---

**Last updated:** 2026-02-24
