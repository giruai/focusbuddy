# Product Brief: Focus Buddy

**Created:** 2026-02-27  
**Status:** Ready for PRD

---

## Problem & Solution

### The Problem
Las personas que quieren mejorar su productividad usando la técnica Pomodoro se enfrentan a temporizadores feos, con anuncios invasivos, o que simplemente no brindan una experiencia satisfactoria. La mayoría de apps Pomodoro son funcionales pero carecen de diseño premium y retroalimentación háptica que haga la experiencia "deliciosa".

### The Solution
Focus Buddy es un temporizador Pomodoro minimalista con UI glassmorphism premium, animaciones fluidas y retroalimentación háptica sutil. Diseñado para ser bello, simple y efectivo.

---

## Target Users

### Primary Audience
Personas de 20-40 años que trabajan en conocimiento (desarrolladores, diseñadores, escritores, estudiantes) y valoran la estética tanto como la funcionalidad.

### User Context
- Usan Pomodoro durante sesiones de trabajo profundo
- Quieren minimizar distracciones
- Aprecian experiencias móviles pulidas
- Usan el timer varias veces al día

---

## MVP Features

### Must Have (MVP blocker)
- [ ] Timer Pomodoro de 25 minutos (focus) / 5 minutos (break)
- [ ] UI glassmorphism con anillo de progreso animado
- [ ] Controles Play/Pause/Reset
- [ ] Pantalla de configuración (durations, haptics)
- [ ] Haptic feedback en interacciones y al terminar
- [ ] Persistencia de preferencias

### Should Have (important but not MVP blocker)
- [ ] Sonido al completar sesión
- [ ] Modo oscuro automático (ya es el default)
- [ ] Animaciones de transición entre pantallas

### Could Have (nice to have)
- [ ] Estadísticas básicas de sesiones completadas
- [ ] Widget para home screen
- [ ] Notificaciones locales

### Won't Have (explicitly out of scope for MVP)
- [ ] Cuentas de usuario / sync en cloud
- [ ] Integración con calendarios
- [ ] Modo equipo/collaborativo
- [ ] Subscripción premium

---

## Technical Constraints

### Platform
- **Min SDK:** 26 (Android 8.0)
- **Target SDK:** 34 (Android 14)
- **Supported devices:** Phone

### Stack
- **Language:** Kotlin
- **UI:** Jetpack Compose
- **Architecture:** MVVM with Repository pattern
- **DI:** Hilt
- **Storage:** DataStore (preferencias)
- **Audio:** MediaPlayer para sonidos
- **Haptics:** Vibrator service

### Limitations
- Sin conectividad requerida (100% offline)
- Sin backend
- Sin permisos sensibles (solo vibración)

---

## Success Criteria (MVP)

- [ ] Usuario puede iniciar, pausar y resetear timer sin errores
- [ ] UI visualmente atractiva, coincide con especificación de diseño
- [ ] Haptics funcionan en todos los botones y al finalizar sesión
- [ ] Preferencias de duración persisten entre sesiones
- [ ] App se siente fluida (60fps en Moto G60s)
- [ ] Build < 10MB APK

---

## Out of Scope

- Play Store launch (MVP es para testing interno)
- Marketing/ASO
- Monetización
- Analytics
- Wear OS companion
