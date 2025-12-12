package cat.pigeon.luxaforcompanion2

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.pigeon.luxaforcompanion2.ui.theme.LuxaforCompanion2Theme

class MainActivity : ComponentActivity() {

    private val utils = LuxaforUtils()

    private val SHOW_BLUE = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LuxaforCompanion2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LuxaforButtons()
                }
            }
        }
    }

    @Composable
    @Preview
    fun LuxaforButtons() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        ) {

            LuxaforButton(LuxaforUtils.Color.RED, Color.Red)

            if (SHOW_BLUE)
                LuxaforButton(LuxaforUtils.Color.BLUE, Color.Blue)

            LuxaforButton(LuxaforUtils.Color.GREEN, Color.Green)
        }
    }

    @Composable
    fun LuxaforButton(color: LuxaforUtils.Color, buttonColor: Color) {
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        val buttonHeight = if (!SHOW_BLUE) screenHeight / 2f else screenHeight / 3f

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(buttonHeight)
                .padding(10.dp, 5.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            border = BorderStroke(2.dp, Color.White),
            onClick = {
                //vibrate()
                triggerLightningCrackle(this)
                utils.changeToColor(color)
            }) {
        }
    }

    private fun vibrate() {
        val vibrationEffect = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                // For Android 12 (S) and above
                val vibratorManager =
                    getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator = vibratorManager.defaultVibrator
                vibrator.vibrate(vibrationEffect)
            }

            Build.VERSION.SDK_INT <= Build.VERSION_CODES.R -> {
                // For Android 11 (R) and below
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(vibrationEffect)
            }
        }
    }

    private fun triggerLightningCrackle(context: Context) {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (!vibrator.hasVibrator()) {
            return // No vibrator on this device
        }


        // ## 1. Define the variables for the pattern ##
        val stepDurationMs = 1L
        val totalDurationMs = 1000L
        val amplitude1 = 50
        val amplitude2 = 200
        val waitDurationMs = 0L

        // Calculate how many 1ms steps are needed for the total duration
        val numberOfSteps = (totalDurationMs / stepDurationMs).toInt()

        val timingsList = mutableListOf<Long>()
        val amplitudesList = mutableListOf<Int>()

        // ## 2. Build the arrays using a loop ##
        for (i in 0 until numberOfSteps) {
            // For each step, add a [wait, vibrate] duration pair
            timingsList.add(waitDurationMs)
            timingsList.add(stepDurationMs)

            // Determine which amplitude to use for the current step
            val currentAmplitude = if (i % 2 == 0) amplitude1 else amplitude2

            // Add a corresponding [0, amplitude] pair
            amplitudesList.add(0) // Amplitude for the wait time is 0
            amplitudesList.add(currentAmplitude)
        }

        // Convert the lists to the required array types
        val timings = timingsList.toLongArray()
        val amplitudes = amplitudesList.toIntArray()

        // Create the vibration effect from the waveform
        val vibrationEffect = VibrationEffect.createWaveform(timings, amplitudes, -1)
        // ^ -1 means do not repeat

        vibrator.vibrate(vibrationEffect)
    }
}