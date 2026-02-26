# Stories — Sprint 1: Foundation

## Epic: Setup

### Story 1: Project Bootstrap
**Como** desarrollador  
**Quiero** un proyecto Android base configurado  
**Para** empezar a desarrollar features

**ACs:**
- [ ] Repo inicializado con .gitignore apropiado
- [ ] Estructura de módulos según architecture.md
- [ ] Dependencias configuradas (Compose, Hilt, Room, etc.)
- [ ] Build exitoso: `./gradlew assembleDebug`

**Estimado:** 1 sesión

---

### Story 2: Theme System
**Como** usuario  
**Quiero** ver la app con el diseño glassmorphism  
**Para** tener la experiencia visual premium

**ACs:**
- [ ] Colores definidos (dark theme, acentos por modo)
- [ ] Typography configurada
- [ ] Glass card component reusable
- [ ] Preview en Compose Preview

**Estimado:** 1 sesión

---

### Story 3: Navigation
**Como** usuario  
**Quiero** navegar entre pantallas  
**Para** acceder a settings y stats

**ACs:**
- [ ] NavHost configurado
- [ ] BottomBar o navegación lateral
- [ ] Transiciones entre screens
- [ ] Back button funciona correctamente

**Estimado:** 1 sesión

---

## Epic: Timer Core

### Story 4: Timer UI
**Como** usuario  
**Quiero** ver el timer principal  
**Para** saber cuánto tiempo queda

**ACs:**
- [ ] Timer display grande y centrado
- [ ] Indicador de modo (Focus/Break)
- [ ] Botones play/pause/skip visibles
- [ ] Diseño responsive

**Estimado:** 1 sesión

---

### Story 5: Timer Logic
**Como** usuario  
**Quiero** que el timer cuente regresivamente  
**Para** saber cuándo termina mi sesión

**ACs:**
- [ ] Timer cuenta segundos precisos
- [ ] Estados: Idle, Running, Paused, Completed
- [ ] Persistencia de estado básica
- [ ] Test unitario del ViewModel

**Estimado:** 1 sesión

---

## Notas

- Orden sugerido: 1 → 2 → 3 → 4 → 5
- Cada story = 1 PR
- Testing en device obligatorio antes de merge

---

*Sprint 1 — Foundation — 2026-02-26*
