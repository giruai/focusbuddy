# Architecture Document: Focus Buddy

**Version:** 1.0  
**Created:** 2026-02-27  
**Status:** Ready for Story Creation

---

## Technology Stack

### Language & Framework
- **Language:** Kotlin 1.9.22
- **UI Framework:** Jetpack Compose 1.6.x (BOM 2024.02.00)
- **Minimum SDK:** 26 (Android 8.0)
- **Target SDK:** 34 (Android 14)
- **Compile SDK:** 34

### Architecture Pattern
**MVVM (Model-View-ViewModel)** with Repository pattern

**Why MVVM:**
- Clear separation of concerns
- Testable ViewModels
- Lifecycle-aware components
- Works well with Compose
- Estado unidireccional (UI state → ViewModel → UI)

---

## Module Structure

```
app/
├── src/main/
│   ├── java/com/giruai/focusbuddy/
│   │   ├── FocusBuddyApplication.kt    # @HiltAndroidApp
│   │   ├── di/                          # Hilt modules
│   │   │   ├── AppModule.kt
│   │   │   └── DataStoreModule.kt
│   │   ├── data/                        # Data layer
│   │   │   ├── local/
│   │   │   │   └── SettingsDataStore.kt # DataStore preferences
│   │   │   ├── model/
│   │   │   │   └── TimerSettings.kt     # Data models
│   │   │   └── repository/
│   │   │       └── SettingsRepository.kt
│   │   ├── domain/                      # Business logic
│   │   │   └── model/
│   │   │       └── TimerState.kt        # Domain models
│   │   ├── ui/                          # Presentation layer
│   │   │   ├── timer/                   # Timer screen feature
│   │   │   │   ├── TimerScreen.kt
│   │   │   │   └── TimerViewModel.kt
│   │   │   ├── settings/                # Settings screen feature
│   │   │   │   ├── SettingsScreen.kt
│   │   │   │   └── SettingsViewModel.kt
│   │   │   ├── components/              # Reusable composables
│   │   │   │   ├── GlassmorphismCard.kt
│   │   │   │   ├── ProgressRing.kt
│   │   │   │   ├── TimerButton.kt
│   │   │   │   └── DurationSelector.kt
│   │   │   ├── theme/                   # Material theme + custom
│   │   │   │   ├── Color.kt
│   │   │   │   ├── Theme.kt
│   │   │   │   └── Type.kt
│   │   │   └── navigation/
│   │   │       └── AppNavigation.kt
│   │   └── util/                        # Utilities
│   │       └── HapticsHelper.kt
│   └── res/
│       ├── values/
│       │   ├── strings.xml
│       │   ├── colors.xml
│       │   └── themes.xml
│       └── raw/                         # Sound files
│           └── timer_complete.mp3
└── build.gradle.kts
```

---

## Data Layer

### Local Storage: DataStore

```kotlin
// data/local/SettingsDataStore.kt
@Singleton
class SettingsDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val dataStore = context.dataStore
    
    // Keys
    companion object {
        val FOCUS_DURATION = intPreferencesKey("focus_duration")
        val BREAK_DURATION = intPreferencesKey("break_duration")
        val HAPTICS_ENABLED = booleanPreferencesKey("haptics_enabled")
        val SOUND_ENABLED = booleanPreferencesKey("sound_enabled")
        
        // Defaults
        const val DEFAULT_FOCUS_DURATION = 25
        const val DEFAULT_BREAK_DURATION = 5
        const val DEFAULT_HAPTICS_ENABLED = true
        const val DEFAULT_SOUND_ENABLED = false
    }
    
    val settingsFlow: Flow<TimerSettings> = dataStore.data.map { prefs ->
        TimerSettings(
            focusDuration = prefs[FOCUS_DURATION] ?: DEFAULT_FOCUS_DURATION,
            breakDuration = prefs[BREAK_DURATION] ?: DEFAULT_BREAK_DURATION,
            hapticsEnabled = prefs[HAPTICS_ENABLED] ?: DEFAULT_HAPTICS_ENABLED,
            soundEnabled = prefs[SOUND_ENABLED] ?: DEFAULT_SOUND_ENABLED
        )
    }
    
    suspend fun updateFocusDuration(minutes: Int) {
        dataStore.edit { it[FOCUS_DURATION] = minutes }
    }
    
    suspend fun updateBreakDuration(minutes: Int) {
        dataStore.edit { it[BREAK_DURATION] = minutes }
    }
    
    suspend fun updateHapticsEnabled(enabled: Boolean) {
        dataStore.edit { it[HAPTICS_ENABLED] = enabled }
    }
    
    suspend fun updateSoundEnabled(enabled: Boolean) {
        dataStore.edit { it[SOUND_ENABLED] = enabled }
    }
}

// Extension property
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "focusbuddy_settings")
```

### Data Models

```kotlin
// data/model/TimerSettings.kt
data class TimerSettings(
    val focusDuration: Int,      // minutes
    val breakDuration: Int,      // minutes
    val hapticsEnabled: Boolean,
    val soundEnabled: Boolean
)
```

### Repository Pattern

```kotlin
// domain/model/TimerState.kt (Domain model)
sealed class TimerState {
    data class Idle(val durationMinutes: Int) : TimerState()
    data class Running(
        val totalSeconds: Int,
        val remainingSeconds: Int,
        val progress: Float // 0.0 to 1.0
    ) : TimerState()
    data class Paused(
        val totalSeconds: Int,
        val remainingSeconds: Int,
        val progress: Float
    ) : TimerState()
    object Completed : TimerState()
}

enum class TimerMode {
    FOCUS, BREAK
}
```

```kotlin
// data/repository/SettingsRepository.kt
interface SettingsRepository {
    fun getSettings(): Flow<TimerSettings>
    suspend fun setFocusDuration(minutes: Int)
    suspend fun setBreakDuration(minutes: Int)
    suspend fun setHapticsEnabled(enabled: Boolean)
    suspend fun setSoundEnabled(enabled: Boolean)
}

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: SettingsDataStore
) : SettingsRepository {
    override fun getSettings(): Flow<TimerSettings> = dataStore.settingsFlow
    override suspend fun setFocusDuration(minutes: Int) = dataStore.updateFocusDuration(minutes)
    override suspend fun setBreakDuration(minutes: Int) = dataStore.updateBreakDuration(minutes)
    override suspend fun setHapticsEnabled(enabled: Boolean) = dataStore.updateHapticsEnabled(enabled)
    override suspend fun setSoundEnabled(enabled: Boolean) = dataStore.updateSoundEnabled(enabled)
}
```

---

## Presentation Layer

### Navigation Graph

```kotlin
// ui/navigation/AppNavigation.kt
sealed class Screen(val route: String) {
    object Timer : Screen("timer")
    object Settings : Screen("settings")
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Timer.route
    ) {
        composable(Screen.Timer.route) {
            TimerScreen(
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
            )
        }
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
```

### Timer ViewModel

```kotlin
// ui/timer/TimerViewModel.kt
@HiltViewModel
class TimerViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val hapticsHelper: HapticsHelper
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(TimerUiState())
    val uiState: StateFlow<TimerUiState> = _uiState.asStateFlow()
    
    private var timerJob: Job? = null
    
    init {
        viewModelScope.launch {
            settingsRepository.getSettings().collect { settings ->
                _uiState.update { 
                    it.copy(
                        settings = settings,
                        // Update display duration if idle
                        timerState = if (it.timerState is TimerState.Idle) {
                            TimerState.Idle(settings.focusDuration)
                        } else it.timerState
                    )
                }
            }
        }
    }
    
    fun startTimer() {
        val currentState = _uiState.value.timerState
        val totalSeconds = when (currentState) {
            is TimerState.Idle -> currentState.durationMinutes * 60
            is TimerState.Paused -> currentState.remainingSeconds
            else -> return
        }
        
        hapticsHelper.tick()
        
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            var remaining = totalSeconds
            while (remaining > 0) {
                _uiState.update {
                    it.copy(
                        timerState = TimerState.Running(
                            totalSeconds = _uiState.value.timerState.let { state ->
                                when (state) {
                                    is TimerState.Running -> state.totalSeconds
                                    is TimerState.Paused -> state.totalSeconds
                                    is TimerState.Idle -> state.durationMinutes * 60
                                    else -> totalSeconds
                                }
                            },
                            remainingSeconds = remaining,
                            progress = remaining.toFloat() / totalSeconds.toFloat()
                        )
                    )
                }
                delay(1000)
                remaining--
            }
            completeTimer()
        }
    }
    
    fun pauseTimer() {
        timerJob?.cancel()
        val current = _uiState.value.timerState as? TimerState.Running ?: return
        _uiState.update {
            it.copy(
                timerState = TimerState.Paused(
                    totalSeconds = current.totalSeconds,
                    remainingSeconds = current.remainingSeconds,
                    progress = current.progress
                )
            )
        }
        hapticsHelper.tick()
    }
    
    fun resetTimer() {
        timerJob?.cancel()
        val settings = _uiState.value.settings
        _uiState.update {
            it.copy(timerState = TimerState.Idle(settings.focusDuration))
        }
        hapticsHelper.tick()
    }
    
    private fun completeTimer() {
        _uiState.update { it.copy(timerState = TimerState.Completed) }
        hapticsHelper.complete()
        // Play sound if enabled
        if (_uiState.value.settings.soundEnabled) {
            // TODO: Play completion sound
        }
    }
    
    data class TimerUiState(
        val timerState: TimerState = TimerState.Idle(25),
        val settings: TimerSettings = TimerSettings(25, 5, true, false),
        val mode: TimerMode = TimerMode.FOCUS
    )
}
```

### Haptics Helper

```kotlin
// util/HapticsHelper.kt
@Singleton
class HapticsHelper @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository
) {
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    
    private suspend fun isHapticsEnabled(): Boolean {
        return settingsRepository.getSettings().first().hapticsEnabled
    }
    
    fun tick() {
        vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK))
    }
    
    fun complete() {
        vibrate(VibrationEffect.createWaveform(longArrayOf(0, 100, 100, 100, 100, 200), -1))
    }
    
    private fun vibrate(effect: VibrationEffect) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(effect)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(50)
        }
    }
}
```

---

## Dependency Injection (Hilt)

### Application Setup

```kotlin
// FocusBuddyApplication.kt
@HiltAndroidApp
class FocusBuddyApplication : Application()
```

### Modules

```kotlin
// di/AppModule.kt
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        impl: SettingsRepositoryImpl
    ): SettingsRepository
}
```

```kotlin
// di/DataStoreModule.kt
@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideSettingsDataStore(
        @ApplicationContext context: Context
    ): SettingsDataStore = SettingsDataStore(context)
}
```

---

## Build Configuration

### build.gradle.kts (app level)

```kotlin
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.giruai.focusbuddy"
    compileSdk = 34
    
    defaultConfig {
        applicationId = "com.giruai.focusbuddy"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    kotlinOptions {
        jvmTarget = "17"
    }
    
    buildFeatures {
        compose = true
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}

dependencies {
    // Compose BOM
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    
    // DataStore
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    
    // Testing
    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.13.9")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
}
```

---

## Testing Strategy

### Unit Tests (ViewModels)

```kotlin
// Example test
class TimerViewModelTest {
    @Test
    fun `when startTimer called, state changes to Running`() = runTest {
        // Arrange
        val viewModel = TimerViewModel(mockSettingsRepo, mockHaptics)
        
        // Act
        viewModel.startTimer()
        
        // Assert
        assertTrue(viewModel.uiState.value.timerState is TimerState.Running)
    }
}
```

### UI Tests (Compose)

```kotlin
class TimerScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun timerScreen_displaysCorrectly() {
        composeTestRule.setContent {
            TimerScreen(onNavigateToSettings = {})
        }
        composeTestRule.onNodeWithText("Focus Buddy").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("Start").assertIsDisplayed()
    }
}
```

### Integration Tests (on device)
- Build APK: `ANDROID_HOME=~/android-sdk ./gradlew assembleDebug`
- Deploy via SFTP to node
- Install and verify ACs with screenshots

---

## Performance Considerations

- `remember { }` para computaciones costosas en Compose
- `derivedStateOf` para estado computado
- Flow con `stateIn()` para optimizar upstream
- Timer usa `delay(1000)` en coroutine (no handler/posts)

---

## Accessibility

- Todos los botones: `contentDescription`
- Touch targets mínimo 48dp
- Contrast ratio 4.5:1 para texto
- TalkBack labels descriptivas

---

## Security

- No hardcoded secrets
- Datos sensibles en DataStore (no necesita encryption para MVP)
- ProGuard en release builds

---

## Sign-Off

**Ready for Epic/Story creation:** ✅ Yes

**Architecture questions:** None
