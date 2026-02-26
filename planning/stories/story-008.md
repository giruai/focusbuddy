# Story: story-008 - Implement settings screen UI

**Epic:** epic-003 - Settings  
**Priority:** Must Have  
**Estimate:** M  
**Created:** 2026-02-27  
**Status:** planned

---

## User Story

**As a** user  
**I want** cambiar configuraciones fácilmente  
**So that** personalize la app a mi gusto

---

## Acceptance Criteria

### AC-1: Focus duration selector
**Given** Settings screen  
**When** I view it  
**Then** I see "Focus Duration" label with píldoras: 25m, 30m, 45m

### AC-2: Break duration selector
**Given** Settings screen  
**When** I view it  
**Then** I see "Break Duration" label with píldoras: 5m, 10m, 15m

### AC-3: Haptics toggle
**Given** Settings screen  
**When** I view it  
**Then** I see "Haptic Feedback" with toggle switch

### AC-4: Selection active state
**Given** I tap "45m"  
**When** I look at the pill  
**Then** it has cyan/azul fill (active), others have transparent + border

### AC-5: Toggle works
**Given** haptics are on  
**When** I tap the toggle  
**Then** haptics turn off and toggle slides to off position

---

## Tasks

- [ ] **TASK-1:** Create `DurationSelector` component with pills → validates AC-1, AC-2
- [ ] **TASK-2:** Create `SettingsToggle` component → validates AC-3
- [ ] **TASK-3:** Build Settings screen layout → validates AC-1, AC-2, AC-3
- [ ] **TASK-4:** Implement active/inactive pill styling → validates AC-4
- [ ] **TASK-5:** Wire toggles to ViewModel → validates AC-5
- [ ] **TASK-6:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- `SettingsViewModel` with `StateFlow<SettingsUiState>`
- Pill buttons: `OutlinedButton` vs `Button` based on selection
- Toggle: `Switch` from Material3

### Component API
```kotlin
@Composable
fun DurationSelector(
    label: String,
    options: List<Int>, // minutes
    selected: Int,
    onSelect: (Int) -> Unit
)
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Screenshot of Settings screen
- [ ] Tap each duration pill, verify selection changes
- [ ] Toggle haptics on/off, verify switch moves
- [ ] Colors match spec (cyan/azul for active)

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device with screenshots
- [ ] Git commit: `feat(settings): implement settings screen UI (closes #8)`
