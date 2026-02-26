# AGENTS.md — Focus Buddy

## Cada sesión

1. Leer `SOUL.md` (quién soy, qué hago)
2. Leer `USER.md` (quién es Franco)
3. Leer `memory/working.md` (tarea actual)
4. Leer `memory/YYYY-MM-DD.md` (hoy + ayer)
5. Si estoy en desarrollo, leer `planning/sprint-status.yaml` (qué story sigue)

## Compaction Survival

1. **`memory/working.md`** — checkpoint cada 3-5 intercambios durante dev activo
2. **Post-compaction:** leer working.md + daily memory + memory_search del topic
3. **Subagents:** para tareas pesadas (bulk refactor, análisis de dependencias)

## Tópicos del grupo Telegram

| Tópico | Thread ID | Uso |
|--------|-----------|-----|
| **General** | 1 | **SESIÓN PRINCIPAL** — toda la conversación con Franco va aquí |
| **Planning** | 4 | Solo outputs: briefs, PRDs, architecture, stories |
| **Development** | 6 | Solo outputs: PRs, builds, device testing results |
| **Review** | 8 | Solo outputs: code reviews, QA issues |

### Regla de tópicos
- **Franco siempre habla en General** — única sesión con contexto completo
- **Otros tópicos son "canales de reporte"** — postear updates vía `message` tool, NO esperar respuestas

## GitHub

- **Repo:** `giruai/focusbuddy`
- **Branch principal:** `main`
- **Workflow:** feature branches + PRs
- **Commits:** conventional commits en inglés

## Memoria

- **`MEMORY.md`**: estado actual del proyecto
- **`memory/YYYY-MM-DD.md`**: log diario de trabajo
- **`memory/working.md`**: tarea activa

## Dev Workflow

**Ver `planning/templates/dev-workflow.md` para ciclo completo.**

1. Pick next `ready-for-dev` story from GitHub milestone
2. Create feature branch
3. Implement + tests
4. **Adversarial code review** (checklist)
5. Fix critical review issues
6. Build APK: `ANDROID_HOME=~/android-sdk ./gradlew assembleDebug`
7. Deploy to device → QA on device
8. Create PR with device testing evidence
9. Merge if approved
10. Close GitHub issue referencing PR

**Testing en device es NO NEGOCIABLE.** Sin screenshot + ACs verificados = no PR.
