# Story: story-012 - Polish animations and transitions

**Epic:** epic-004 - Haptics & Polish  
**Priority:** Should Have  
**Estimate:** M  
**Created:** 2026-02-27  
**Status:** planned

---

## User Story

**As a** user  
**I want** animaciones suaves y fluidas  
**So that** la app se sienta premium

---

## Acceptance Criteria

### AC-1: Navigation transitions
**Given** I navigate to Settings  
**When** screen changes  
**Then** smooth slide/fade transition (not instant)

### AC-2: Button press feedback
**Given** I tap a button  
**When** finger presses  
**Then** subtle scale animation (press down/up)

### AC-3: Timer number animation
**Given** timer is running  
**When** seconds change  
**Then** numbers update smoothly (no flicker)

### AC-4: Progress ring smoothness
**Given** timer running  
**When** I observe the ring  
**Then** animation at consistent 60fps

### AC-5: Screen enter animations
**Given** app launches or navigates  
**When** content appears  
**Then** staggered fade-in of elements

---

## Tasks

- [ ] **TASK-1:** Add navigation enter/exit transitions → validates AC-1
- [ ] **TASK-2:** Add button press scale animation → validates AC-2
- [ ] **TASK-3:** Optimize timer number rendering → validates AC-3
- [ ] **TASK-4:** Profile progress ring performance → validates AC-4
- [ ] **TASK-5:** Add screen enter animations → validates AC-5
- [ ] **TASK-6:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- Navigation: `NavHost` with `enterTransition`, `exitTransition`
- Buttons: `Modifier.pointerInput` with `detectTapGestures` or `indication`
- Use `AnimatedContent`, `animateFloatAsState`, `Crossfade`

### Implementation Examples
```kotlin
// Navigation transitions
composable(
    route = ...,
    enterTransition = { slideInHorizontally { it } },
    exitTransition = { slideOutHorizontally { -it } }
)

// Button scale
val scale by animateFloatAsState(if (pressed) 0.95f else 1f)
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Navigate between screens, verify smooth transitions
- [ ] Tap buttons rapidly, verify no jank
- [ ] Run 5-min timer, verify consistent 60fps
- [ ] Profile GPU if available

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device
- [ ] No dropped frames during animations
- [ ] Git commit: `feat(polish): add smooth animations and transitions (closes #12)`
