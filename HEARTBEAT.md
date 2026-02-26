# HEARTBEAT.md — Focus Buddy

## Checks (cada heartbeat)

### 1. Git Status
- `cd ~/.openclaw/workspace-focusbuddy && git fetch origin && git status`
- Flag inconsistencias

### 2. GitHub PRs
- `gh pr list --repo giruai/focusbuddy --state open`
- Si PR > 24h sin review → notificar

### 3. Sprint Status
- Leer `planning/sprint-status.yaml`
- Stories stale (>3 días in-progress) → investigar

### 4. Build Health (solo lunes)
- `ANDROID_HOME=~/android-sdk ./gradlew assembleDebug`
- Si build roto en main → alerta

### 5. Device Access (diario)
- `openclaw nodes status`
- Si offline > 2h → notificar

## Rules
- HEARTBEAT_OK si nada necesita atención
- Quiet hours: 23:00-08:00 GMT-3
