# Story: story-005 - Create progress ring with gradient animation

**Epic:** epic-002 - Timer Core  
**Priority:** Must Have  
**Estimate:** L  
**Created:** 2026-02-27  
**Status:** planned

---

## User Story

**As a** user  
**I want** ver un anillo de progreso animado  
**So that** pueda ver visualmente el tiempo restante

---

## Acceptance Criteria

### AC-1: Progress ring renders
**Given** Timer screen  
**When** I view it  
**Then** I see circular ring with outer track and progress arc

### AC-2: Gradient colors
**Given** progress ring  
**When** I inspect it  
**Then** gradient goes from cyan (#00C6FF) to electric blue (#0072FF)

### AC-3: Progress animates smoothly
**Given** timer running  
**When** time passes  
**Then** ring fills/depletes smoothly (not jumpy)

### AC-4: Progress matches time
**Given** 25-min timer at 12:30 remaining  
**When** I look at ring  
**Then** it's approximately 50% complete

### AC-5: Glow effect
**Given** progress ring  
**When** I view it  
**Then** subtle glow on inner edge of progress arc

---

## Tasks

- [ ] **TASK-1:** Create `ProgressRing` composable → validates AC-1
- [ ] **TASK-2:** Implement gradient brush for progress arc → validates AC-2
- [ ] **TASK-3:** Add animation for smooth progress changes → validates AC-3
- [ ] **TASK-4:** Bind progress to timer state → validates AC-4
- [ ] **TASK-5:** Add subtle glow effect → validates AC-5
- [ ] **TASK-6:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- Use `Canvas` composable for custom drawing
- `SweepGradient` or `LinearGradient` for colors
- `animateFloatAsState` for smooth transitions

### Component API
```kotlin
@Composable
fun ProgressRing(
    progress: Float, // 0.0 to 1.0
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 12.dp,
    trackColor: Color = Color.White.copy(alpha = 0.1f),
    progressColors: List<Color> = listOf(Color(0xFF00C6FF), Color(0xFF0072FF))
)
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Ring visible with correct colors (screenshot)
- [ ] Animation smooth at 60fps
- [ ] Progress accurate at various time points
- [ ] Glow effect visible

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device with screenshots
- [ ] Animation smooth (no dropped frames)
- [ ] Git commit: `feat(ui): create animated progress ring (closes #5)`
