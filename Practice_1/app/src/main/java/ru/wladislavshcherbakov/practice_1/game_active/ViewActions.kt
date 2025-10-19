package ru.wladislavshcherbakov.practice_1

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.GeneralClickAction
import androidx.test.espresso.action.Press
import androidx.test.espresso.action.Tap
import androidx.test.espresso.action.CoordinatesProvider
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

fun clickAt(x: Float, y: Float): ViewAction {
    val coords = CoordinatesProvider { view ->
        val location = IntArray(2); view.getLocationOnScreen(location)
        floatArrayOf(location[0] + x, location[1] + y)
    }
    return GeneralClickAction(Tap.SINGLE, coords, Press.FINGER)
}

fun swipeTo(x: Float, y: Float): ViewAction {
    // Простой клик имитирует касание
    return clickAt(x, y)
}
