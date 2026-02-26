# Story: story-008 - Implement settings screen UI

**Epic:** epic-003 - Settings  
**Priority:** Must Have  
**Estimate:** M  
**Created:** 2026-02-27  
**Status:** planned

---

## User Story

**As a** user  
**I want** cambiar configuraciones facilmente  
**So that** personalize la app a mi gusto

---

## Acceptance Criteria

### AC-1: Bottom sheet container
**Given** Settings opened  
**When** I view it  
**Then** it's a bottom sheet covering ~70% from bottom with rounded top corners (24dp)

### AC-2: Header
**Given** Settings sheet  
**When** I view top  
**Then** I see "Settings" (24sp, bold, left) and close `X` button (right)

### AC-3: Focus Duration stepper
**Given** Settings sheet  
**When** I view  
**Then** I see:
- Label "Focus Duration" (14sp, gray)
- `-` circle / `25 min` / `+` circle
- Circles: 40dp, outlined `#3A3B55`

### AC-4: Break Duration stepper
**Given** Settings sheet  
**When** I view  
**Then** same layout as Focus, default 5 min

### AC-5: Session Label pills
**Given** Settings sheet  
**When** I view  
**Then** I see:
- Label "Session Label"
- Pills: "Deep Work Session", "Study Session", "Creative Flow", "Meeting Prep"
- Active pill: filled `#4A9EFF`, white text
- Inactive: `#2A2B45` bg, `#9CA3AF` text, outlined

### AC-6: Interactions work
**Given** Settings open  
**When** I tap `+` / `-` / pills  
**Then** values update and persist

---

## Tasks

- [ ] **TASK-1:** Create bottom sheet container with rounded top → validates AC-1
- [ ] **TASK-2:** Add header with title and close button → validates AC-2
- [ ] **TASK-3:** Create DurationStepper component (- / value / +) → validates AC-3, AC-4
- [ ] **TASK-4:** Create PillSelector component → validates AC-5
- [ ] **TASK-5:** Add Focus Duration section → validates AC-3
- [ ] **TASK-6:** Add Break Duration section → validates AC-4
- [ ] **TASK-7:** Add Session Label section → validates AC-5
- [ ] **TASK-8:** Wire to ViewModel for persistence → validates AC-6
- [ ] **TASK-9:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- Use `ModalBottomSheet` from Material3
- Or custom `BottomSheetScaffold`
- Stepper: circular buttons with icon

### UI Reference
- **CRITICAL:** Consultar `skills/ui-reference/SKILL.md` antes de implementar
- Imagen de referencia: `media/inbound/file_1---*.jpg`

### Componentes a crear
```kotlin
// ui/settings/components/
- DurationStepper.kt
- PillSelector.kt
- SettingsBottomSheet.kt
```

### Colores exactos
```kotlin
val Surface = Color(0xFF252641)
val SurfaceVariant = Color(0xFF2A2B45)
val Border = Color(0xFF3A3B55)
val Primary = Color(0xFF4A9EFF)
val TextSecondary = Color(0xFF9CA3AF)
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Screenshot of settings bottom sheet
- [ ] Compare with reference image
- [ ] Tap + increase duration
- [ ] Tap - decrease duration
- [ ] Tap pills change selection
- [ ] Close button dismisses sheet

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device with screenshots
- [ ] UI matches reference image
- [ ] Git commit: `feat(settings): implement settings bottom sheet (closes #8)`
