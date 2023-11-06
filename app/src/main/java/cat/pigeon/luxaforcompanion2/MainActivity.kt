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
            LuxaforButton(LuxaforUtils.Color.BLUE, Color.Blue)
            LuxaforButton(LuxaforUtils.Color.GREEN, Color.Green)
        }
    }

    @Composable
    fun LuxaforButton(color: LuxaforUtils.Color, buttonColor: Color) {
        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 3f)
                .padding(10.dp, 5.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
            border = BorderStroke(2.dp, Color.White),
            onClick = {
                vibrate()
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
}