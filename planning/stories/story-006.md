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

### AC-1: Header correcto
**Given** Timer screen  
**When** I view it  
**Then** I see: `<` back arrow left, "Focus Buddy" centered, white text

### AC-2: Progress ring
**Given** Timer screen  
**When** I view center  
**Then** I see circular ring with:
- Track: dark gray `#2A2B45`
- Progress arc: gradient `#4A9EFF` → `#00C6FF`
- Glow effect on progress end
- Time markers every 5 minutes

### AC-3: Time display
**Given** Timer screen  
**When** I view center  
**Then** I see: large `24:09` (72sp, Montserrat Black, white), "mins" below (14sp, gray)

### AC-4: Session label
**Given** Timer screen  
**When** timer active  
**Then** I see "Deep Work Session" (16sp, `#9CA3AF`, centered)

### AC-5: Control panel (glassmorphism)
**Given** Timer screen  
**When** I view bottom  
**Then** I see pill-shaped panel with:
- Pause button `||` (left, outlined circle)
- Stop button "Stop" (center, gradient green pill when running)
- Settings button `⚙️` (right, outlined circle)

### AC-6: Stop button states
**Given** timer running  
**When** I view Stop button  
**Then** it's green gradient (`#4ADE80` → `#22C55E`)

**Given** timer idle  
**When** I view Start button  
**Then** it's coral gradient (`#FF7A6A` → `#FFC0A0`)

---

## Tasks

- [ ] **TASK-1:** Create header with back arrow and title → validates AC-1
- [ ] **TASK-2:** Implement ProgressRing with gradient and glow → validates AC-2
- [ ] **TASK-3:** Create TimeDisplay component (large numbers + mins label) → validates AC-3
- [ ] **TASK-4:** Add session label → validates AC-4
- [ ] **TASK-5:** Create glassmorphism control panel → validates AC-5
- [ ] **TASK-6:** Implement Pause/Stop/Settings buttons → validates AC-5
- [ ] **TASK-7:** Add button state logic (green vs coral) → validates AC-6
- [ ] **TASK-8:** Wire buttons to ViewModel → validates AC-5, AC-6
- [ ] **TASK-9:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- Use `Box` with `Column` for layout
- Montserrat font family (add to theme)
- Glassmorphism: `Modifier.background(Color(0xFF252641).copy(alpha=0.9f))` + `graphicsLayer { renderEffect = BlurEffect }`

### UI Reference
- **CRITICAL:** Consultar `skills/ui-reference/SKILL.md` antes de implementar
- Imágenes de referencia: `media/inbound/file_0---*.jpg`

### Componentes a crear
```kotlin
// ui/timer/components/
- ProgressRing.kt
- TimeDisplay.kt
- ControlPanel.kt
- GlassmorphismPanel.kt
```

### Colores exactos
```kotlin
val Background = Color(0xFF1A1B2F)
val Surface = Color(0xFF252641)
val SurfaceVariant = Color(0xFF2A2B45)
val Primary = Color(0xFF4A9EFF)
val PrimaryGradientEnd = Color(0xFF00C6FF)
val Success = Color(0xFF4ADE80)
val SuccessDark = Color(0xFF22C55E)
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Screenshot of timer screen
- [ ] Compare with reference image side-by-side
- [ ] Verify colors with color picker
- [ ] All buttons tappable (48dp min)
- [ ] Typography matches spec exactly
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
- [ ] UI matches reference image pixel-perfectly
- [ ] No lint warnings
- [ ] Git commit: `feat(timer): build timer screen UI (closes #6)`
