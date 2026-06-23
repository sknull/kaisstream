package de.visualdigits.kaisstream

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import de.visualdigits.common.domain.model.platform.PlatformType
import de.visualdigits.kaisstream.presentation.model.KAisStreamViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: KAisStreamViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = window.decorView
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->

                    // for newer device starting with android 14/15
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(
                            lightScrim = Color.TRANSPARENT,
                            darkScrim = Color.TRANSPARENT,
                            detectDarkMode = { false }
                        )
                    )

                    // for older devices down to android 6
                    val olderWindow = this@MainActivity.window
                    WindowCompat.getInsetsController(olderWindow, view).apply {
                        isAppearanceLightStatusBars = true
                    }
                }
            }
        }

        setContent {
            App(PlatformType.android)
        }
    }
}
