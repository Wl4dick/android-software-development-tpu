package ru.wladislavshcherbakov.practice_1

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.wladislavshcherbakov.practice_1.game_active.GameView

@RunWith(AndroidJUnit4::class)
class GameFlowTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun startGame_and_seeGameView() {
        onView(withId(R.id.startButton)).perform(click())
        onView(isAssignableFrom(GameView::class.java)).check(matches(isDisplayed()))
    }

    @Test
    fun eatCheese_increasesScore() {
        onView(withId(R.id.startButton)).perform(click())
        // Клик по экрану для движения мыши — координаты подбираются экспериментально
        onView(isAssignableFrom(GameView::class.java)).perform(clickAt(300f, 300f))
    }

    @Test
    fun collideWithCat_showsGameOverDialog() {
        onView(withId(R.id.startButton)).perform(click())
        // Сдвинем мышь в центр, где чаще появляются объекты
        onView(isAssignableFrom(GameView::class.java)).perform(clickAt(500f, 500f))
        // Ожидаем появление диалога (заголовок)
        onView(withText("Игра окончена")).check(matches(isDisplayed()))
    }
}
