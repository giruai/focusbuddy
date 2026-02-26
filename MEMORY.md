# MEMORY.md — Focus Buddy

## Estado del proyecto

- **Fase actual:** Setup (listo para comenzar desarrollo)
- **Sprint actual:** Sprint 1 — Foundation

## App Details

- **Nombre:** Focus Buddy - Minimal Timer
- **Package:** com.giruai.focusbuddy
- **Descripción:** Pomodoro timer con UI glassmorphism premium, hápticos y diseño minimalista
- **Target users:** Personas que buscan productividad con una experiencia visual premium

## Stack Técnico

- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM + Repository pattern
- **DI:** Hilt
- **Storage:** DataStore (preferencias)
- **minSdk:** 26
- **targetSdk:** 34

## Repo

- **GitHub:** github.com/giruai/focusbuddy
- **Branch:** main

## Planning

### Documentos
- ✅ Product Brief: `planning/product-brief.md`
- ✅ PRD: `planning/prd.md`
- ✅ Architecture: `planning/architecture.md`
- ✅ **UI Spec:** `planning/ui-spec.md` (actualizado con referencias exactas)
- ✅ **UI Reference Skill:** `skills/ui-reference/SKILL.md`
- ✅ Sprint Status: `planning/sprint-status.yaml`

### Imágenes de Referencia
- Timer Screen: `media/inbound/file_0---37847ee1-2a06-4499-9d83-a1aa3d723423.jpg`
- Settings Screen: `media/inbound/file_1---dbb0225b-33c6-4436-a4e8-c2be10d7e442.jpg`

### Epics (4)
- epic-001: Setup
- epic-002: Timer Core
- epic-003: Settings
- epic-004: Haptics & Polish

### Stories (12)
| ID | Título | Epic | Status |
|----|--------|------|--------|
| story-001 | Create Android project structure | Setup | ready-for-dev |
| story-002 | Setup dependency injection with Hilt | Setup | ready-for-dev |
| story-003 | Implement navigation and theme | Setup | ready-for-dev |
| story-004 | Implement timer logic and state management | Timer | planned |
| story-005 | Create progress ring with gradient animation | Timer | planned |
| story-006 | Build timer screen UI | Timer | planned |
| story-007 | Setup DataStore for preferences | Settings | planned |
| story-008 | Implement settings screen UI | Settings | planned |
| story-009 | Connect settings to timer | Settings | planned |
| story-010 | Implement haptic feedback system | Polish | planned |
| story-011 | Add completion sound | Polish | planned |
| story-012 | Polish animations and transitions | Polish | planned |

## GitHub

- **Milestone:** Sprint 1 — Foundation (#1)
- **Labels:** epic:setup, epic:timer, epic:settings, epic:polish, must-have, should-have
- **Issues:** 12 creados (#1-#12)

## Device Testing

- **Device:** Moto G60s (OpenClaw node)
- **Android version:** 12 (API 31)
- **Screen resolution:** 1080x2460

---

**Última actualización:** 2026-02-27
