package ru.wladislavshcherbakov.practice_1

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LevelsFragmentTest {

    // Запускаем MainActivity перед каждым тестом
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testLevelsFragmentIsDisplayed() {
        // Нажимаем кнопку "Список уровней", чтобы открыть LevelsFragment
        onView(withId(R.id.levelsButton)).perform(click())

        // Проверяем, что RecyclerView появился на экране
        onView(withId(R.id.levelsRecyclerView))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testLevelItemDisplayed() {
        // Открываем LevelsFragment
        onView(withId(R.id.levelsButton)).perform(click())

        // Проверяем, что первый уровень (например, "Уровень 1") отображается
        onView(withText("Уровень 1"))
            .check(matches(isDisplayed()))
    }
}