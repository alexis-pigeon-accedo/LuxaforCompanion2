package cat.pigeon.luxaforcompanion2

import android.os.Handler
import android.os.Looper
import io.github.rybalkinsd.kohttp.dsl.async.httpPostAsync
import io.github.rybalkinsd.kohttp.ext.url

class LuxaforUtils {
    enum class Color(val rgb: String) {
        RED("red"),
        GREEN("green"),
        BLUE("blue"),
        WHITE("white"),
        YELLOW("yellow"),
        CYAN("cyan"),
        MAGENTA("magenta")
    }

    enum class Pattern(val pattern: String) {
        POLICE("police"),
        TRAFFIC_LIGHTS("traffic lights"),
        RAINBOW("rainbow"),
        SEA("sea"),
        WHITE_WAVE("white wave"),
        SYNTHETIC("synthetic"),
        RANDOM1("random 1"),
        RANDOM2("random 2"),
        RANDOM3("random 3"),
        RANDOM4("random 4"),
        RANDOM5("random 5")
    }

    private val BASE_URL = "https://api.luxafor.com"
    private val ENDPOINT_SOLID_COLOR = "/webhook/v1/actions/solid_color"
    private val ENDPOINT_BLINK = "/webhook/v1/actions/blink"
    private val ENDPOINT_PATTERN = "/webhook/v1/actions/pattern"
    private val USER_ID = BuildConfig.luxaforuserid

    private var currentColor: Color = Color.GREEN
    private val ANIMATIONS_ENABLED:Boolean = true

    private val BLINK_TIMER: Long = 1500

    fun changeToColor(color: Color) {
        println("Change to $color")

        if (ANIMATIONS_ENABLED) {
            blinkColor(currentColor)
            Handler(Looper.getMainLooper()).postDelayed({
                blinkColor(color)
                Handler(Looper.getMainLooper()).postDelayed({
                    solidColor(color)
                }, BLINK_TIMER)
            }, BLINK_TIMER)
        }
        else {
            solidColor(color)
        }
        currentColor = color
    }

    private fun blinkColor(color: Color) {
        println("Blink to $color")
        luxaforApiCall(ENDPOINT_BLINK, color = color)
    }

    private fun solidColor(color: Color) {
        println("Solid to $color")
        luxaforApiCall(ENDPOINT_SOLID_COLOR, color = color)
    }

    private fun pattern(pattern: Pattern): Boolean {
        println("Pattern to $pattern")
        luxaforApiCall(ENDPOINT_PATTERN, pattern = pattern)
        return true
    }

    private fun luxaforApiCall(endpoint : String, color: Color ?= null, pattern: Pattern ?= null) {
        httpPostAsync {
            url(BASE_URL + endpoint)
            body {
                json {
                    "userId" to USER_ID
                    "actionFields" to json {
                        if (color != null) {
                            "color" to color.rgb
                        }
                        if (pattern != null)
                            "pattern" to pattern.pattern
                    }
                }
            }
        }
    }
}