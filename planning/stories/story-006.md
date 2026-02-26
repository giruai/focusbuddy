# Story: story-006 - Build timer screen UI

**Epic:** epic-002 - Timer Core  
**Priority:** Must Have  
**Estimate:** L  
**Created:** 2026-02-27  
**Status:** planned

---

## User Story

**As a** user  
**I want** una pantalla de timer hermosa y funcional  
**So that** disfrute usar la app

---

## Acceptance Criteria

### AC-1: Timer display large
**Given** Timer screen  
**When** I view it  
**Then** time shows in large bold text (minutes "25" massive, "mins" smaller)

### AC-2: Header with back arrow
**Given** Timer screen  
**When** I view top-left  
**Then** I see back arrow icon (blanco)

### AC-3: Title "Focus Buddy"
**Given** Timer screen  
**When** I view top  
**Then** I see "Focus Buddy" title, descentrado derecha, blanco

### AC-4: Session label
**Given** Timer screen  
**When** timer is idle or running  
**Then** I see "Deep Work Session" centered below timer

### AC-5: Control buttons
**Given** Timer screen  
**When** I view bottom  
**Then** I see: Play/Pause (left), Start/Stop center pill, Settings (right)

### AC-6: Glassmorphism panel
**Given** Timer screen  
**When** I view control panel  
**Then** it has backdrop blur, rounded capsule shape, dark semi-transparent bg

---

## Tasks

- [ ] **TASK-1:** Create header with back arrow and title → validates AC-2, AC-3
- [ ] **TASK-2:** Integrate ProgressRing with timer display → validates AC-1
- [ ] **TASK-3:** Add session label → validates AC-4
- [ ] **TASK-4:** Create control panel with glassmorphism → validates AC-6
- [ ] **TASK-5:** Add Play/Pause/Settings buttons → validates AC-5
- [ ] **TASK-6:** Add Start/Stop pill button with gradient → validates AC-5
- [ ] **TASK-7:** Wire buttons to ViewModel actions → validates all ACs
- [ ] **TASK-8:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- Use `Scaffold` or `Box` with `Column`
- Montserrat/Inter fonts from Google Fonts
- Glassmorphism: `Modifier.blur()` + semi-transparent background

### File Structure
```
ui/timer/
├── TimerScreen.kt
├── TimerViewModel.kt
└── components/
    └── TimerControls.kt
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Screenshot of complete timer screen
- [ ] All buttons visible and tappable (48dp min)
- [ ] Typography matches spec
- [ ] Colors match UI spec exactly
- [ ] Glassmorphism effect visible

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device with screenshots
- [ ] UI pixel-perfect to spec
- [ ] Git commit: `feat(timer): build timer screen UI (closes #6)`
