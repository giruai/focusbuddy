package com.giruai.focusbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.giruai.focusbuddy.ui.navigation.AppNavigation
import com.giruai.focusbuddy.ui.theme.FocusBuddyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocusBuddyTheme {
                AppNavigation()
            }
        }
    }
}
