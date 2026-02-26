# UI Reference — Focus Buddy

> **CRITICAL:** Esta skill contiene la UI de referencia EXACTA que debe implementarse.
> Siempre consultar este archivo antes de implementar componentes visuales.

## Imágenes de Referencia

Las imágenes oficiales de diseño están en:
- `~/.openclaw/media/inbound/file_0---37847ee1-2a06-4499-9d83-a1aa3d723423.jpg` — Timer Screen
- `~/.openclaw/media/inbound/file_1---dbb0225b-33c6-4436-a4e8-c2be10d7e442.jpg` — Settings Screen

## Timer Screen

```
┌─────────────────────────────┐
│  <      Focus Buddy         │  Header: back arrow + title centered
│                             │
│      ╭─────────────╮        │
│     ╱               ╲       │  Progress ring with markers
│    │     24:09       │      │  Large time (72sp, bold, white)
│    │      mins       │      │  Small label below
│     ╲               ╱       │
│      ╰─────────────╯        │  Gradient progress arc (blue → cyan)
│                             │  Glow effect on progress end
│   Deep Work Session         │  Session label (gray)
│                             │
│  ┌─────────────────────┐    │
│  │  ||    [Stop]   ⚙️  │    │  Glassmorphism panel (pill shape)
│  │       (green)       │    │  Pause | Stop | Settings
│  └─────────────────────┘    │  Stop = gradient green when running
└─────────────────────────────┘      gradient coral when idle
```

### Timer Specs
- Background: `#1A1B2F`
- Time: `24:09` format, Montserrat Black 72sp, white
- Ring track: `#2A2B45`, 8dp stroke
- Ring progress: gradient `#4A9EFF` → `#00C6FF`, 12dp stroke
- Glow: blue blur at progress end
- Panel: `#252641` 90% opacity + blur, rounded pill

## Settings Screen (Bottom Sheet)

```
┌─────────────────────────────┐
│                             │
│  ┌─────────────────────┐    │  Bottom sheet covering ~70%
│  │ Settings        [X] │    │  Header with close
│  │                     │    │
│  │ FOCUS DURATION      │    │
│  │  ⊖    25 min    ⊕   │    │  Stepper: - / value / +
│  │                     │    │  Circles 40dp, outlined
│  │ BREAK DURATION      │    │
│  │  ⊖     5 min    ⊕   │    │
│  │                     │    │
│  │ SESSION LABEL       │    │
│  │ [Deep Work Session] │    │  Active pill: filled blue
│  │ [Study Session]     │    │  Inactive: outlined gray
│  │ [Creative Flow]     │    │
│  │ [Meeting Prep]      │    │
│  └─────────────────────┘    │
└─────────────────────────────┘
```

### Settings Specs
- Sheet background: `#252641` with blur
- Corner radius: 24dp top
- Stepper circles: 40dp, border `#3A3B55`
- Active pill: `#4A9EFF` background, white text
- Inactive pill: `#2A2B45` background, `#9CA3AF` text

## Colores Oficiales

```kotlin
object AppColors {
    val Background = Color(0xFF1A1B2F)
    val Surface = Color(0xFF252641)
    val SurfaceVariant = Color(0xFF2A2B45)
    val Border = Color(0xFF3A3B55)
    
    val Primary = Color(0xFF4A9EFF)
    val PrimaryGradientEnd = Color(0xFF00C6FF)
    val Success = Color(0xFF4ADE80)
    val SuccessDark = Color(0xFF22C55E)
    val Coral = Color(0xFF7A6A)
    val CoralLight = Color(0xFFFFC0A0)
    
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFF9CA3AF)
    val TextMuted = Color(0xFF6B7280)
}
```

## Checklist de Implementación

Antes de marcar una story como completa, verificar:

- [ ] Colores hex EXACTOS (no aproximaciones)
- [ ] Tipografía Montserrat (bold para tiempo, medium para labels)
- [ ] Proporciones de elementos (72sp tiempo, 16sp labels)
- [ ] Glassmorphism con blur real (no solo transparencia)
- [ ] Anillo con gradiente y glow
- [ ] Panel de controles con shape pill (no rectángulo)
- [ ] Settings como bottom sheet (no pantalla completa)
- [ ] Stepper con círculos -/+ (no pills)

## Testing Visual

En cada PR de UI:
1. Screenshot del timer corriendo
2. Screenshot de settings abierto
3. Comparar lado a lado con imágenes de referencia
4. Verificar colores con color picker si es necesario
