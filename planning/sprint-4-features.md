# Sprint 4 — Break & Sound

**Start:** 2026-02-27  
**Goal:** Implement break functionality and completion sound

---

## Stories

### story-021: Implement break functionality
**Description:** After focus session completes, offer a break timer  
**AC:**
- When focus timer reaches 0, show break screen with countdown
- Break duration from settings (default 5 min)
- Skip break option
- After break, return to idle state for next focus session
- Visual distinction between focus and break modes

### story-022: Implement completion sound
**Description:** Play sound when timer completes if soundEnabled  
**AC:**
- Add sound file to res/raw/
- Play sound on focus completion
- Play sound on break completion  
- Respect soundEnabled setting
- Handle AudioManager properly
