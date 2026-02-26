# PRD — Focus Buddy

## 1. Overview

Focus Buddy es un temporizador Pomodoro con UI glassmorphism premium y feedback háptico. Prioriza la experiencia táctil y visual sobre features extensivas.

## 2. User Flows

### 2.1 First Launch
```
Splash → Onboarding rápido (2 slides) → Main Timer
```

### 2.2 Daily Usage
```
Abrir app → Timer listo en 25:00 → Tap play → Trabajar
→ Timer termina → Notificación + háptico → Tap para break
```

### 2.3 Session Complete
```
Timer 00:00 → Animación celebración → Auto-switch a break mode
→ Sonido + vibración
```

## 3. Screens

### 3.1 Main Timer (Pantalla Principal)

**Layout:**
```
┌─────────────────────────────┐
│  [Settings icon]    [Stats] │
│                             │
│         ╭─────────╮         │
│        ╱   25:00   ╲        │  ← Timer central
│       │   ───────   │       │     Glass card
│        ╲   FOCUS   ╱        │
│         ╰─────────╯         │
│                             │
│    [Session counter]        │
│                             │
│   [←]  [PLAY/PAUSE]  [→]   │  ← Controls
│                             │
│  [Short Break] [Long Break] │  ← Mode switchers
└─────────────────────────────┘
```

**Estados visuales:**
- **Focus:** Fondo oscuro degradado, acentos naranja/coral
- **Short Break:** Fondo verde azulado, acentos teal
- **Long Break:** Fondo azul profundo, acentos azul claro

### 3.2 Settings

- Duración Pomodoro (default: 25 min)
- Duración Short Break (default: 5 min)
- Duración Long Break (default: 15 min)
- Long break after X pomodoros (default: 4)
- Sonido on/off
- Haptics on/off
- Volumen alerta

### 3.3 Stats (Simple)

- Sesiones completadas hoy
- Sesiones completadas esta semana
- Total focus time hoy

## 4. Functional Requirements

| ID | Requirement | Priority |
|----|-------------|----------|
| F1 | Timer cuenta regresiva precisa (segundos) | P0 |
| F2 | Estados: Idle → Running → Paused → Completed | P0 |
| F3 | Auto-switch entre work/break modes | P0 |
| F4 | Persistencia de estado (survive rotation/bg) | P0 |
| F5 | Notificación local al completar sesión | P0 |
| F6 | Haptics en todos los botones | P0 |
| F7 | Settings persistidos en DataStore | P1 |
| F8 | Historial de sesiones en base local | P1 |

## 5. Non-Functional Requirements

- **Performance:** UI 60fps, respuesta táctil < 100ms
- **Accessibility:** Soportar TalkBack, tamaños de texto dinámicos
- **Battery:** Timer no debe drenar batería (usa AlarmManager/WorkManager)
- **Offline:** 100% funcional sin conexión

## 6. UI Specifications

### 6.1 Colors (Dark Theme por defecto)

```
Background:     #0D0D0F → #1A1A2E (gradiente)
Glass Card:     #FFFFFF 10% opacity + blur 20px
Text Primary:   #FFFFFF
Text Secondary: #FFFFFF 60% opacity
Accent Focus:   #FF6B6B (coral)
Accent Short:   #4ECDC4 (teal)
Accent Long:    #6B9BD1 (blue)
```

### 6.2 Typography

- Timer: SF Pro Display / Roboto 72sp bold
- Labels: 14sp medium
- Buttons: 16sp semibold

### 6.3 Animations

- **Botón play:** Scale 0.95 → 1.0 + ripple
- **Timer complete:** Pulse + celebración (partículas opcional)
- **Mode switch:** Cross-fade de colores 300ms
- **Glass card:** Sutil movimiento con gyro (parallax opcional)

## 7. Haptics Map

| Interaction | Haptic |
|-------------|--------|
| Tap button | Light impact |
| Start timer | Medium impact |
| Pause timer | Light impact |
| Timer complete | Success notification + vibración corta |
| Mode switch | Selection |
| Session count up | Light impact |

## 8. Edge Cases

- App en background: Timer continúa, notificación al terminar
- App killed: Timer se pierde (aceptable para v1)
- Device locked: Notificación + sonido funcionan
- Llamada entrante: Timer pausa automáticamente
- Batería baja: Sin efecto especial (v1)

## 9. Future Considerations (Post-v1)

- Widgets (home screen)
- Watch companion
- Sync cross-device
- Stats avanzados (gráficos)
- Sonidos ambientales (Lofi)
- Inter, SF Pro, o custom font

---

*PRD v1.0 — 2026-02-26*
