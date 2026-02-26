# Sprint 2 — UI Fixes

**Start:** 2026-02-27  
**Goal:** Fix UI discrepancies from reference images

---

## Stories

### story-013: Fix progress ring direction
**Issue:** Ring currently depletes (full → empty)  
**Fix:** Ring should fill up (empty → full) as time progresses  
**AC:**
- At 25:00, ring shows minimal/no progress arc
- At 0:00, ring shows full arc
- Progress increases as timer counts down

### story-014: Fix control buttons layout
**Issue:** Current layout has wrong button states  
**Fix:** Match reference exactly:
- Left: Play button (▶) — only visible when paused
- Center: Start/Stop — coral gradient when idle, green when running
- Right: Settings gear
**AC:**
- Button states match reference images
- Start/Stop has correct icon (▶ when idle, ⏹ or text when running)

### story-015: Convert Settings to bottom sheet
**Issue:** Settings is currently a full screen  
**Fix:** Settings should be a bottom sheet modal with:
- Rounded top corners (24dp)
- Cover ~70% of screen from bottom
- Slide up animation
**AC:**
- Settings appears as bottom sheet
- Proper rounded top corners
- Dismissible by swiping down or tapping close

### story-016: Polish settings UI
**Issue:** Settings UI doesn't match reference  
**Fix:**
- Proper stepper styling (- / value / +)
- Session pills layout matching reference
- Toggles for haptics/sound
**AC:**
- Visual match to reference images
