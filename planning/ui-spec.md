# UI/UX Specification — Focus Buddy

## Design Language
Glassmorphism moderno + tipografía audaz + retroalimentación háptica y visual sutil.

## Pantalla 1: Temporizador Principal (Focus / Break Mode)

### Fondo
- Gradiente radial profundo: centro azul cobalto (#1A1B2F) → bordes violeta medianoche (#2D2E4D)

### Cabecera
- Flecha retroceso (<): blanco puro, izquierda → Settings
- Título "Focus Buddy": blanco, Inter Bold/Montserrat Bold, descentrado derecha

### Cronómetro Central (Elemento Héroe)
- **Anillo Exterior:** #FFFFFF 10% opacidad (100% sesión)
- **Anillo de Progreso:** gradiente cian (#00C6FF) → azul eléctrico (#0072FF), animación fluida
  - Brillo neón sutil en borde interior
- **Texto:** minutos en blanco, Montserrat Black, tamaño masivo, kerning ajustado
  - "mins" debajo: blanco 60%, regular, menor
- **Micro-detalles:** marcas/puntos blancos baja opacidad emanando del centro

### Etiqueta: "Deep Work Session" centrada debajo

### Panel de Control Flotante
- Cápsula redondeada, backdrop blur, #33355A 80% opacidad, sombra suave
- **Play/Pause** (izq): icono en círculo blur
- **Start/Stop** (centro): píldora gradiente coral (#FF7A6A→#FFC0A0), cambia a verde en stop
- **Settings** (der): engranaje en círculo blur

## Pantalla 2: Settings

### Fondo: mismo gradiente
### Controles
- "Focus Duration" / "Break Duration": píldoras horizontales (25m, 30m, 45m)
  - Inactivo: transparente + borde blanco fino
  - Activo: relleno cian/azul eléctrico
- "Haptic Feedback": toggle switch, color activo = cian/azul
- Separadores: blanco 5% opacidad

## Implementación
- Fuentes: Inter/Montserrat (Google Fonts)
- Haptics: vibración breve en botones y al finalizar
- Animaciones: anillo fluido, micro-scale en botones, slide/fade entre pantallas
