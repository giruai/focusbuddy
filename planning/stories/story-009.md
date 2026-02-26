# Story: story-009 - Connect settings to timer

**Epic:** epic-003 - Settings  
**Priority:** Must Have  
**Estimate:** M  
**Created:** 2026-02-27  
**Status:** planned

---

## User Story

**As a** user  
**I want** que el timer use mis configuraciones  
**So that** dure el tiempo que elegí

---

## Acceptance Criteria

### AC-1: Settings affect timer display
**Given** I set focus duration to 45m  
**When** I go to Timer screen  
**Then** it shows "45" minutes

### AC-2: Timer uses configured duration
**Given** focus duration is 30m  
**When** I start timer  
**Then** it counts down from 30:00

### AC-3: Haptics setting respected
**Given** haptics are disabled  
**When** I tap buttons  
**Then** no vibration occurs

### AC-4: Changes apply to next timer
**Given** timer is running at 25m  
**When** I change to 45m in Settings  
**Then** current timer continues at 25m, next reset shows 45m

---

## Tasks

- [ ] **TASK-1:** Inject SettingsRepository into TimerViewModel → validates AC-1
- [ ] **TASK-2:** Read settings on ViewModel init → validates AC-1
- [ ] **TASK-3:** Use settings duration for new timers → validates AC-2
- [ ] **TASK-4:** Check haptics setting before vibrating → validates AC-3
- [ ] **TASK-5:** Ensure changes don't affect running timer → validates AC-4
- [ ] **TASK-6:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- `TimerViewModel` collects `settingsFlow` in init
- Settings changes update `TimerState.Idle` duration
- Haptics helper checks settings before vibrating

### Implementation
```kotlin
init {
    viewModelScope.launch {
        settingsRepository.getSettings().collect { settings ->
            // Update idle state duration if timer not running
        }
    }
}
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Change focus to 45m, verify Timer shows 45
- [ ] Start timer, verify counts from 45:00
- [ ] Disable haptics, tap buttons, verify no vibration
- [ ] Start timer, change setting, verify current timer unaffected

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device
- [ ] Git commit: `feat(timer): connect settings to timer (closes #9)`
