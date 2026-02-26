# Story: story-001 - Create Android project structure

**Epic:** epic-001 - Setup  
**Priority:** Must Have  
**Estimate:** M  
**Created:** 2026-02-27  
**Status:** ready-for-dev

---

## User Story

**As a** developer  
**I want** a clean Android project structure  
**So that** I can build features on a solid foundation

---

## Acceptance Criteria

### AC-1: Project compiles
**Given** a fresh checkout  
**When** I run `./gradlew assembleDebug`  
**Then** build succeeds without errors

### AC-2: Correct package structure
**Given** the project root  
**When** I check `app/src/main/java/com/giruai/focusbuddy/`  
**Then** I see packages: `di/`, `data/`, `domain/`, `ui/`, `util/`

### AC-3: Dependencies configured
**Given** `build.gradle.kts`  
**When** I check dependencies  
**Then** I see: Compose BOM, Hilt, Navigation, DataStore, Coroutines

### AC-4: Theme colors defined
**Given** `ui/theme/Color.kt`  
**When** I review the file  
**Then** I see colors matching UI spec: cobalt blue (#1A1B2F), midnight violet (#2D2E4D), cyan (#00C6FF), electric blue (#0072FF), coral (#FF7A6A)

---

## Tasks

- [ ] **TASK-1:** Create project with Android Studio template or manual setup → validates AC-1
- [ ] **TASK-2:** Configure build.gradle.kts with all dependencies → validates AC-3
- [ ] **TASK-3:** Create package structure → validates AC-2
- [ ] **TASK-4:** Define theme colors and typography → validates AC-4
- [ ] **TASK-5:** Write unit tests for build configuration → validates AC-1
- [ ] **TASK-6:** Test on device (build + install) → validates all ACs

---

## Dev Notes

### Architecture Hints
- Use `Empty Compose Activity` template
- Min SDK 26, Target SDK 34
- Java 17 compatibility

### File Structure
```
app/src/main/java/com/giruai/focusbuddy/
├── FocusBuddyApplication.kt
├── di/
├── data/
│   ├── local/
│   ├── model/
│   └── repository/
├── domain/
│   └── model/
├── ui/
│   ├── timer/
│   ├── settings/
│   ├── components/
│   ├── theme/
│   └── navigation/
└── util/
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Build APK: `ANDROID_HOME=~/android-sdk ./gradlew assembleDebug`
- [ ] APK size < 15MB
- [ ] Deploy to Moto G60s
- [ ] App launches without crash
- [ ] Take screenshot of blank theme

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

### Implementation Summary

### Files Modified

### Tests Added

### Device Test Results

### Challenges Encountered

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device
- [ ] Unit tests written and passing
- [ ] No lint warnings
- [ ] Git commit: `chore(setup): create project structure (closes #1)`
