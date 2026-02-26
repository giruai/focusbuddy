# Story: story-010 - Implement haptic feedback system

**Epic:** epic-004 - Haptics & Polish  
**Priority:** Should Have  
**Estimate:** S  
**Created:** 2026-02-27  
**Status:** planned

---

## User Story

**As a** user  
**I want** sentir retroalimentación al tocar botones  
**So that** la app se sienta táctil y responsive

---

## Acceptance Criteria

### AC-1: Button press haptic
**Given** haptics enabled  
**When** I tap any button  
**Then** I feel short vibration (EFFECT_TICK)

### AC-2: Timer complete haptic
**Given** haptics enabled and timer running  
**When** timer reaches 0  
**Then** I feel long vibration (custom pattern)

### AC-3: Haptic helper exists
**Given** the codebase  
**When** I check `util/HapticsHelper`  
**Then** I see methods: `tick()`, `complete()`

### AC-4: Respects settings
**Given** haptics disabled in settings  
**When** I tap buttons  
**Then** no vibration occurs

---

## Tasks

- [ ] **TASK-1:** Create `HapticsHelper` class → validates AC-3
- [ ] **TASK-2:** Implement tick() for button presses → validates AC-1
- [ ] **TASK-3:** Implement complete() for timer end → validates AC-2
- [ ] **TASK-4:** Check settings before vibrating → validates AC-4
- [ ] **TASK-5:** Wire into all button actions → validates AC-1
- [ ] **TASK-6:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- `@Singleton` HapticsHelper injected via Hilt
- Use `Vibrator` system service
- Check `Build.VERSION` for `VibrationEffect` vs legacy `vibrate()`

### Implementation
```kotlin
@Singleton
class HapticsHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    
    fun tick() { /* EFFECT_TICK */ }
    fun complete() { /* Custom pattern */ }
}
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Tap each button, feel tick
- [ ] Set 5s timer, let complete, feel long vibration
- [ ] Disable haptics, verify no vibration
- [ ] Re-enable, verify vibration returns

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device
- [ ] Git commit: `feat(haptics): implement haptic feedback system (closes #10)`
