# Story: story-011 - Add completion sound

**Epic:** epic-004 - Haptics & Polish  
**Priority:** Should Have  
**Estimate:** S  
**Created:** 2026-02-27  
**Status:** planned

---

## User Story

**As a** user  
**I want** escuchar un sonido al completar  
**So that** me notifique si no estoy mirando la pantalla

---

## Acceptance Criteria

### AC-1: Sound toggle exists
**Given** Settings screen  
**When** I scroll  
**Then** I see "Completion Sound" with toggle

### AC-2: Sound plays on complete
**Given** sound enabled and timer running  
**When** timer reaches 0  
**Then** I hear completion sound

### AC-3: No sound when disabled
**Given** sound disabled  
**When** timer completes  
**Then** no sound plays (only haptics if enabled)

### AC-4: Sound setting persists
**Given** I enable sound  
**When** I restart app  
**Then** toggle remains on

---

## Tasks

- [ ] **TASK-1:** Add sound toggle to settings → validates AC-1
- [ ] **TASK-2:** Add sound file to res/raw/ → validates AC-2
- [ ] **TASK-3:** Play sound on timer complete → validates AC-2
- [ ] **TASK-4:** Check setting before playing → validates AC-3
- [ ] **TASK-5:** Persist sound setting → validates AC-4
- [ ] **TASK-6:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- Simple `MediaPlayer` for short sound
- Sound file: soft chime/bell (1-2 seconds)
- Play in ViewModel when state changes to Completed

### Implementation
```kotlin
// In TimerViewModel
if (settings.soundEnabled) {
    mediaPlayer?.start()
}
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Enable sound, complete timer, hear chime
- [ ] Disable sound, complete timer, hear nothing
- [ ] Kill app, reopen, verify setting persists

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device
- [ ] Git commit: `feat(audio): add completion sound (closes #11)`
