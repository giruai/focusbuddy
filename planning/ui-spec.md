# UI/UX Specification — Focus Buddy

## Referencias Visuales

MEDIA:./media/inbound/file_0---37847ee1-2a06-4499-9d83-a1aa3d723423.jpg
MEDIA:./media/inbound/file_1---dbb0225b-33c6-4436-a4e8-c2be10d7e442.jpg

---

## Design Language

Glassmorphism moderno + tipografía audaz + fondo oscuro profundo.

### Paleta de Colores

**Background:**
- Fondo principal: `#1A1B2F` (azul noche profundo)
- Superficies glass: `#252641` con 80% opacidad + blur

**Acentos:**
- Primario (activo/selección): `#4A9EFF` (azul brillante)
- Éxito/Stop: `#4ADE80` (verde neón)
- Progreso anillo: gradiente `#4A9EFF` → `#00C6FF`
- Glow del progreso: `#4A9EFF` con blur

**Texto:**
- Primario: `#FFFFFF`
- Secundario: `#9CA3AF` (gris claro)
- Placeholder: `#6B7280`

---

## Pantalla 1: Timer Principal (Focus/Break Mode)

### Header
- Flecha retroceso `<`: blanco, izquierda
- Título "Focus Buddy": blanco, centrado, Inter/Montserrat Bold

### Anillo de Progreso Central
- **Track (fondo):** `#2A2B45` (gris azulado oscuro), stroke 8dp
- **Marcadores:** líneas radiales sutiles cada 5 minutos
- **Progress arc:** gradiente `#4A9EFF` → `#00C6FF`, stroke 12dp
- **Glow effect:** blur azul en el borde del progress
- **Indicador de tiempo:** punto azul brillante en el extremo del progress

### Display de Tiempo
- **Formato:** `24:09` (minutos:segundos)
- **Fuente:** Montserrat Black/ExtraBold, tamaño 72sp
- **Color:** blanco puro `#FFFFFF`
- **Label:** "mins" debajo, 14sp, `#9CA3AF`

### Session Label
- Texto: "Deep Work Session" (o el label seleccionado)
- Fuente: 16sp, medium, `#9CA3AF`
- Centrado debajo del tiempo

### Panel de Control (Glassmorphism)
- **Contenedor:** pill/cápsula redondeada (corner radius 32dp)
- **Background:** `#252641` 90% opacidad + backdrop blur
- **Padding:** 16dp vertical, 24dp horizontal
- **Sombra:** suave elevación

**Botones (dentro del panel):**
1. **Pause** (izquierda):
   - Icono: `||` (pausa) en círculo
   - Background: transparente con borde sutil `#3A3B55`
   - Tamaño: 48dp círculo

2. **Stop** (centro):
   - Texto: "Stop" (cambia a "Start" cuando está idle)
   - Background: gradiente verde `#4ADE80` → `#22C55E` (cuando running)
   - Background: gradiente coral `#FF7A6A` → `#FFC0A0` (cuando idle)
   - Shape: píldora redondeada (corner radius 24dp)
   - Padding: 16dp vertical, 48dp horizontal

3. **Settings** (derecha):
   - Icono: `⚙️` (engranaje)
   - Background: transparente con borde sutil `#3A3B55`
   - Tamaño: 48dp círculo

---

## Pantalla 2: Settings (Bottom Sheet)

### Contenedor
- **Tipo:** Bottom sheet modal que cubre ~70% de pantalla desde abajo
- **Background:** `#252641` con backdrop blur
- **Corner radius:** 24dp arriba
- **Sombra:** elevación alta

### Header
- Título "Settings": 24sp, bold, blanco, izquierda
- Botón cerrar `X`: 24sp, `#9CA3AF`, derecha

### Secciones

#### Focus Duration
- Label: "Focus Duration", 14sp, `#9CA3AF`, mayúsculas o small caps
- Controles: `-` [círculo] / `25 min` / `+` [círculo]
- Número: 32sp, bold, blanco
- Botones +/-: círculos 40dp, borde `#3A3B55`, icono blanco

#### Break Duration
- Mismo layout que Focus Duration
- Default: 5 min

#### Session Label
- Label: "Session Label", 14sp, `#9CA3AF`
- Pills horizontales (wrap):
  - **Activo:** background `#4A9EFF`, texto blanco, corner radius 20dp
  - **Inactivo:** background `#2A2B45`, texto `#9CA3AF`, border `#3A3B55`
- Opciones: "Deep Work Session", "Study Session", "Creative Flow", "Meeting Prep"

---

## Componentes Reutilizables

### GlassmorphismPanel
```kotlin
@Composable
fun GlassmorphismPanel(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    // Background: Color(0xFF252641).copy(alpha = 0.9f)
    // blur behind (10-20dp)
    // corner radius 24dp o 32dp según uso
}
```

### CircularProgressRing
```kotlin
@Composable
fun CircularProgressRing(
    progress: Float, // 0.0 to 1.0
    timeText: String, // "24:09"
    label: String, // "mins"
    modifier: Modifier = Modifier
) {
    // Track: #2A2B45
    // Progress: gradient #4A9EFF → #00C6FF
    // Glow effect on progress end
    // Time markers every 5 min
}
```

### ControlButton
```kotlin
@Composable
fun ControlButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = false
) {
    // if isPrimary: gradient pill
    // else: outlined circle
}
```

### DurationStepper
```kotlin
@Composable
fun DurationStepper(
    value: Int,
    unit: String,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    // - [circle] 25 min + [circle]
}
```

### PillSelector
```kotlin
@Composable
fun PillSelector(
    options: List<String>,
    selected: String,
    onSelect: (String) -> Unit
) {
    // Horizontal wrap layout
    // Active: filled primary
    // Inactive: outlined
}
```

---

## Animaciones

- **Progreso del timer:** animación fluida del arco cada segundo
- **Transición Settings:** slide up desde bottom (300ms, easeOut)
- **Botones:** scale 0.95 on press, con animation
- **Glow del progress:** pulso sutil cuando timer corre

---

## Implementación

- Fuentes: Montserrat (Google Fonts)
- Haptics: tick en botones, long vibrate al completar
- Status bar: transparente o color fondo
- Navigation bar: oscuro, ocultar en timer
