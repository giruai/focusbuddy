# Story: story-002 - Setup dependency injection with Hilt

**Epic:** epic-001 - Setup  
**Priority:** Must Have  
**Estimate:** S  
**Created:** 2026-02-27  
**Status:** ready-for-dev

---

## User Story

**As a** developer  
**I want** dependency injection configured  
**So that** I can inject dependencies cleanly across the app

---

## Acceptance Criteria

### AC-1: Hilt application class
**Given** the app module  
**When** I check `FocusBuddyApplication.kt`  
**Then** it's annotated with `@HiltAndroidApp`

### AC-2: Hilt modules created
**Given** `di/` package  
**When** I list files  
**Then** I see `AppModule.kt` with repository bindings

### AC-3: DI works in ViewModel
**Given** a ViewModel class  
**When** I annotate it with `@HiltViewModel`  
**Then** it compiles and runs without injection errors

---

## Tasks

- [ ] **TASK-1:** Create `@HiltAndroidApp` application class → validates AC-1
- [ ] **TASK-2:** Create AppModule with repository bindings → validates AC-2
- [ ] **TASK-3:** Create sample ViewModel to verify DI works → validates AC-3
- [ ] **TASK-4:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- Use `@Binds` for interface implementations
- SingletonComponent for app-level dependencies

### File Structure
```
di/
├── AppModule.kt
└── DataStoreModule.kt (if needed)
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Build succeeds
- [ ] App launches without DI crash
- [ ] Log shows Hilt initialization

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified
- [ ] No lint warnings
- [ ] Git commit: `chore(setup): configure hilt di (closes #2)`
