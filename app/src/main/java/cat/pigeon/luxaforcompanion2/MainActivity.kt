package cat.pigeon.luxaforcompanion2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.unit.dp
import cat.pigeon.luxaforcompanion2.ui.theme.LuxaforCompanion2Theme

class MainActivity : ComponentActivity() {

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
}

@Composable
fun LuxaforButtons() {
    val utils = LuxaforUtils()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LuxaforButton(utils, LuxaforUtils.Color.RED, Color.Red)
        LuxaforButton(utils, LuxaforUtils.Color.BLUE, Color.Blue)
        LuxaforButton(utils, LuxaforUtils.Color.GREEN, Color.Green)
    }
}

@Composable
fun LuxaforButton(utils: LuxaforUtils, color: LuxaforUtils.Color, buttonColor: Color) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 3f)
            .padding(10.dp, 5.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
        onClick = {
        utils.changeToColor(color)
    }) {

    }
}

