# Product Requirements Document: Focus Buddy

**Version:** 1.0  
**Created:** 2026-02-27  
**Status:** Ready for Architecture  
**Author:** Focus Buddy Agent

---

## Problem Statement

**Current situation:**  
Los temporizadores Pomodoro existentes son funcionales pero carecen de diseño premium y experiencia táctil satisfactoria. La mayoría tiene anuncios, UI desactualizada, o carecen de retroalimentación háptica.

**Impact:**  
Los usuarios no disfrutan usar la app, lo que reduce la adherencia a la técnica Pomodoro y su efectividad para mejorar productividad.

**Proposed solution:**  
Un temporizador Pomodoro minimalista con diseño glassmorphism premium, animaciones fluidas y hápticos que hagan cada interacción satisfactoria.

---

## User Journeys

### Journey 1: Iniciar Sesión de Focus

**Scenario:** Usuario abre la app para comenzar un Pomodoro de 25 minutos

**Steps:**
1. Usuario abre Focus Buddy
2. Ve el timer en 25:00 con anillo de progreso completo
3. Toca el botón Start (grande, gradiente coral)
4. Timer comienza a decrementar, anillo de progreso se reduce suavemente
5. Si necesita pausar, toca Pause
6. Al terminar 25 minutos, vibración larga + sonido (si está activado)

**Success metric:** Usuario completa sesión sin abandonar la app

### Journey 2: Cambiar Configuración

**Scenario:** Usuario quiere cambiar duración a 45 minutos

**Steps:**
1. Usuario toca Settings (icono engranaje)
2. Pantalla Settings aparece con slide/fade
3. Toca píldora "45m" en "Focus Duration"
4. Regresa atrás (o toca área fuera del panel)
5. Timer principal ahora muestra 45:00

**Success metric:** Preferencia persiste después de cerrar y abrir app

### Journey 3: Resetear Timer

**Scenario:** Usuario necesita abortar sesión actual

**Steps:**
1. Usuario toca Stop (o Reset según diseño)
2. Confirmación háptica (vibración corta)
3. Timer regresa a valor inicial
4. Anillo de progreso vuelve a 100%

---

## MVP Scope

### Functional Requirements

**FR-001: Timer Functionality**  
**Priority:** Must Have  
**Description:** Timer cuenta hacia atrás desde duración configurada hasta 0  
**User value:** Core functionality del Pomodoro  

**FR-002: Play/Pause Control**  
**Priority:** Must Have  
**Description:** Usuario puede pausar y reanudar timer  
**User value:** Flexibilidad para interrupciones  

**FR-003: Reset/Stop Control**  
**Priority:** Must Have  
**Description:** Usuario puede resetear timer a valor inicial en cualquier momento  
**User value:** Control completo del timer  

**FR-004: Visual Progress Ring**  
**Priority:** Must Have  
**Description:** Anillo animado muestra progreso de timer con gradiente cian→azul  
**User value:** Feedback visual inmediato de tiempo restante  

**FR-005: Configurable Durations**  
**Priority:** Must Have  
**Description:** Usuario puede seleccionar 25m/30m/45m para focus, 5m/10m/15m para break  
**User value:** Personalización a preferencias individuales  

**FR-006: Haptic Feedback**  
**Priority:** Must Have  
**Description:** Vibración en botones (corta) y al completar sesión (larga)  
**User value:** Retroalimentación táctil satisfactoria  

**FR-007: Haptic Toggle**  
**Priority:** Must Have  
**Description:** Usuario puede desactivar/activar hápticos  
**User value:** Accesibilidad y preferencia personal  

**FR-008: Persist Settings**  
**Priority:** Must Have  
**Description:** Duraciones y preferencias de hápticos persisten entre sesiones  
**User value:** No necesita reconfigurar cada vez  

**FR-009: Completion Sound**  
**Priority:** Should Have  
**Description:** Sonido suave al completar timer (con toggle)  
**User value:** Notificación audible cuando no está mirando pantalla  

**FR-010: Session Label**  
**Priority:** Should Have  
**Description:** Texto "Deep Work Session" / "Break Time" según modo  
**User value:** Contexto visual del estado actual  

---

## Non-Functional Requirements

**NFR-001: Performance**  
- App launch: < 1.5 segundos cold start
- Animaciones: 60fps consistente
- Transiciones entre pantallas: < 300ms

**NFR-002: Offline Support**  
- 100% funcional sin conectividad
- No backend requerido

**NFR-003: Data Privacy**  
- Solo datos locales en DataStore
- Sin tracking, sin analytics
- Sin permisos de red

**NFR-004: Accessibility**  
- TalkBack support en todos los controles
- Minimum touch target: 48dp
- Contrast ratio: 4.5:1 para texto
- Controles accesibles sin ver (screen reader)

**NFR-005: Battery & Resources**  
- Timer funciona con pantalla apagada (foreground service si aplica)
- Sin wake locks innecesarios
- APK size objetivo: < 10MB

---

## Android-Specific Requirements

### Permissions
- [ ] `android.permission.VIBRATE` — Reason: Haptic feedback
- [ ] `android.permission.FOREGROUND_SERVICE` — Reason: Timer en background (if implementing)
- [ ] `android.permission.POST_NOTIFICATIONS` — Reason: Notificación al completar (if implementing)

### SDK Versions
- **minSdk:** 26 (Android 8.0)
- **targetSdk:** 34 (Android 14)
- **compileSdk:** 34

### Architecture Pattern
**MVVM with Repository Pattern**
- ViewModels para UI state
- Repository para abstraer DataStore
- Hilt para dependency injection

### Build Configuration
- **Package:** com.giruai.focusbuddy
- **Version code:** 1
- **Version name:** 1.0.0
- **Build variants:** debug, release

---

## Dependencies

### Core Libraries
- Jetpack Compose (UI)
- Navigation Compose
- Hilt (DI)
- DataStore (Preferences)
- Coroutines + Flow
- ViewModel + StateFlow

### Audio & Haptics
- Vibrator service (system)
- MediaPlayer (para sonidos simples)

### Testing
- JUnit 4
- Mockk
- Compose UI Test
- Truth

---

## Edge Cases & Error Handling

### Timer Completa en Background
**Scenario:** Usuario cambia a otra app, timer completa  
**Expected behavior:** Notificación local (if implementado) o vibración continúa

### Cambio de Duración Mientras Timer Corre
**Scenario:** Usuario abre Settings y cambia duración durante sesión activa  
**Expected behavior:** Nuevo valor aplica solo al siguiente timer (no al actual)

### App Kill Durante Timer
**Scenario:** Sistema mata app por memoria  
**Expected behavior:** Timer se pierde (aceptable para MVP sin foreground service)

### Low Battery
**Scenario:** Batería baja, sistema limita background  
**Expected behavior:** Timer continúa si es foreground service, sino se pierde

---

## Open Questions

- [ ] ¿Implementar foreground service para timer confiable en background? (MVP: no)
- [ ] ¿Incluir sonido por defecto o silencioso? (MVP: opcional, default silencio)
- [ ] ¿Soporte para landscape o solo portrait? (MVP: portrait only)

---

## Sign-Off

**Ready for Architecture phase:** ✅ Yes

**Blocker issues:** None
