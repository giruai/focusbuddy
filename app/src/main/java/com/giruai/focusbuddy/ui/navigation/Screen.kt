package com.giruai.focusbuddy.ui.navigation

sealed class Screen(val route: String) {
    data object Timer : Screen("timer")
    data object Settings : Screen("settings")
}
