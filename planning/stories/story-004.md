# Story: story-004 - Implement timer logic and state management

**Epic:** epic-002 - Timer Core  
**Priority:** Must Have  
**Estimate:** L  
**Created:** 2026-02-27  
**Status:** planned

---

## User Story

**As a** user  
**I want** un timer que cuente regresivamente  
**So that** pueda hacer Pomodoro sessions

---

## Acceptance Criteria

### AC-1: Timer countdown works
**Given** timer set to 25 minutes  
**When** I start it  
**Then** it decrements every second (25:00 → 24:59 → 24:58...)

### AC-2: Timer completes
**Given** timer running  
**When** it reaches 0  
**Then** state changes to Completed

### AC-3: Pause works
**Given** timer running  
**When** I tap Pause  
**Then** timer stops, state is Paused

### AC-4: Resume works
**Given** timer paused  
**When** I tap Start  
**Then** timer resumes from where it stopped

### AC-5: Reset works
**Given** timer running or paused  
**When** I tap Reset  
**Then** timer returns to initial duration, state is Idle

---

## Tasks

- [ ] **TASK-1:** Define `TimerState` sealed class → validates AC-1
- [ ] **TASK-2:** Implement countdown logic in ViewModel → validates AC-1, AC-2
- [ ] **TASK-3:** Implement pause functionality → validates AC-3
- [ ] **TASK-4:** Implement resume functionality → validates AC-4
- [ ] **TASK-5:** Implement reset functionality → validates AC-5
- [ ] **TASK-6:** Write unit tests for ViewModel → validates all ACs
- [ ] **TASK-7:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- `TimerState` sealed class: Idle, Running, Paused, Completed
- Use `delay(1000)` in coroutine for countdown
- Manage coroutine job lifecycle (cancel on pause/reset)

### Data Models
```kotlin
sealed class TimerState {
    data class Idle(val durationMinutes: Int) : TimerState()
    data class Running(val totalSeconds: Int, val remainingSeconds: Int, val progress: Float) : TimerState()
    data class Paused(val totalSeconds: Int, val remainingSeconds: Int, val progress: Float) : TimerState()
    object Completed : TimerState()
}
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Start 1-minute timer, verify it counts down
- [ ] Pause at 0:45, verify it stops
- [ ] Resume, verify continues
- [ ] Reset at 0:30, verify returns to 1:00
- [ ] Let complete to 0, verify Completed state

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device
- [ ] Unit tests for all state transitions
- [ ] Git commit: `feat(timer): implement countdown logic (closes #4)`
