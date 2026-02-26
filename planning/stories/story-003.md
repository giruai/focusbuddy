# Story: story-003 - Implement navigation and theme

**Epic:** epic-001 - Setup  
**Priority:** Must Have  
**Estimate:** M  
**Created:** 2026-02-27  
**Status:** ready-for-dev

---

## User Story

**As a** user  
**I want** navegar entre Timer y Settings  
**So that** puedo configurar la app

---

## Acceptance Criteria

### AC-1: Navigation routes defined
**Given** the navigation module  
**When** I check `Screen` sealed class  
**Then** I see routes: `timer`, `settings`

### AC-2: NavHost configured
**Given** the app  
**When** it launches  
**Then** NavHost shows Timer as start destination

### AC-3: Navigate to Settings
**Given** I'm on Timer screen  
**When** I tap Settings icon  
**Then** Settings screen appears with slide animation

### AC-4: Navigate back
**Given** I'm on Settings screen  
**When** I tap back arrow  
**Then** I return to Timer screen

### AC-5: Theme applied
**Given** either screen  
**When** I view it  
**Then** background shows gradient cobalt → violet

---

## Tasks

- [ ] **TASK-1:** Create `Screen` sealed class with routes → validates AC-1
- [ ] **TASK-2:** Implement `AppNavigation` with NavHost → validates AC-2
- [ ] **TASK-3:** Create placeholder TimerScreen → validates AC-2
- [ ] **TASK-4:** Create placeholder SettingsScreen → validates AC-3
- [ ] **TASK-5:** Implement navigation actions → validates AC-3, AC-4
- [ ] **TASK-6:** Apply gradient background theme → validates AC-5
- [ ] **TASK-7:** Test on device → validates all ACs

---

## Dev Notes

### Architecture Hints
- Use `rememberNavController()`
- `NavHost` with `startDestination = Screen.Timer.route`
- Pass navigation lambdas to screens

### File Structure
```
ui/
├── navigation/
│   └── AppNavigation.kt
├── timer/
│   └── TimerScreen.kt (placeholder)
├── settings/
│   └── SettingsScreen.kt (placeholder)
└── theme/
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

---

## Testing Notes

### Device Testing Checklist
- [ ] Build and install
- [ ] App launches on Timer screen
- [ ] Navigate to Settings (screenshot)
- [ ] Navigate back to Timer (screenshot)
- [ ] Gradient background visible on both screens

---

## Dev Agent Record

**Started:** —  
**Ended:** —  
**Developer:** —

---

## Definition of Done

- [ ] All tasks completed
- [ ] All ACs verified on device with screenshots
- [ ] Navigation animations smooth (60fps)
- [ ] Git commit: `feat(navigation): implement navigation and theme (closes #3)`
