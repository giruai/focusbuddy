# Story: story-007 - Setup DataStore for preferences

**Epic:** epic-003 - Settings  
**Priority:** Must Have  
**Estimate:** M  
**Created:** 2026-02-27  
**Status:** planned

---

## User Story

**As a** user  
**I want** que mis preferencias persistan  
**So that** no tenga que reconfigurar cada vez

---

## Acceptance Criteria

### AC-1: DataStore configured
**Given** the data layer  
**When** I check `SettingsDataStore`  
**Then** it uses `preferencesDataStore` with name "focusbuddy_settings"

### AC-2: Settings model defined
**Given** `data/model/TimerSettings.kt`  
**When** I review it  
**Then** it has: focusDuration, breakDuration, hapticsEnabled, soundEnabled

### AC-3: Repository implemented
**Given** `SettingsRepository`  
**When** I call `getSettings()`  
**Then** it returns `Flow<TimerSettings>` with stored values

### AC-4: Defaults work
**Given** fresh install  
**When** app first launches  
**Then** defaults are: focus=25, break=5, haptics=true, sound=false

### AC-5: Updates persist
**Given** I change focus duration to 45  
**When** I restart app  
**Then** it shows 45 as focus duration

---

## Tasks

- [ ] **TASK-1:** Create `SettingsDataStore` with DataStore → validates AC-1
- [ ] **TASK-2:** Define `TimerSettings` data class → validates AC-2
- [ ] **TASK-3:** Create `SettingsRepository` interface and impl → validates AC-3
- [ ] **TASK-4:** Add Hilt module for DataStore → validates AC-1
- [ ] **TASK-5:** Write unit tests for repository → validates AC-4, AC-5
- [ ] **TASK-6:** Test on device (change setting, kill app, reopen) → validates AC-5

---

## Dev Notes

### Architecture Hints
- `preferencesDataStore(name = "focusbuddy_settings")`
- Keys as `intPreferencesKey`, `booleanPreferencesKey`
- Repository exposes `Flow<TimerSettings>` for reactive UI

### Data Models
```kotlin
data class TimerSettings(
    val focusDuration: Int = 25,
    val breakDuration: Int = 5,
    val hapticsEnabled: Boolean = true,
    val soundEnabled: Boolean = false
)
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Install fresh app
- [ ] Change focus duration to 45
- [ ] Kill app (swipe away)
- [ ] Reopen, verify still 45

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device
- [ ] Unit tests for repository
- [ ] Git commit: `feat(data): setup DataStore for preferences (closes #7)`
