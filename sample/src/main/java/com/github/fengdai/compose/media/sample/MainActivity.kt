package com.github.fengdai.compose.media.sample

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.RememberObserver
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val state = remember { MyState() }

                val isLandscape =
                    LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE
                // The conditional flow is relevant
                if (!isLandscape) {
                    // This key fun usage is also relevant
                    key(state) {
                        // do something
                    }
                    Content("Normal Mode", state)
                } else {
                    Content("LandScape Mode", state)
                }
            }
        }
    }
}

@Composable
fun Content(
    name: String,
    state: MyState
) {
    Column {
        Text(name)
        val isAvailable = state.isAvailable
        Text(
            "isAvailable: $isAvailable",
            style = if (!isAvailable) TextStyle.Default.copy(color = Color.Red) else TextStyle.Default
        )
        Text("state hash code: ${state.hashCode()}")
    }
}

class MyState : RememberObserver {
    val isAvailable get() = remembered

    private var remembered = false

    override fun onRemembered() {
        remembered = true
    }

    override fun onAbandoned() {
        remembered = false
    }

    override fun onForgotten() {
        remembered = false
    }
}
