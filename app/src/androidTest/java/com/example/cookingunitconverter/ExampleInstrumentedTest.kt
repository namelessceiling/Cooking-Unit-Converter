package com.example.cookingunitconverter

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.containsString

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule()
    val activity = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.cookingunitconverter", appContext.packageName)
    }

    @Test
    fun calculate_gallons_to_cups_with_rounding() {
        onView(withId(R.id.amount_edit_text_field))
            .perform(typeText("1.55"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.base_gallons))
            .perform(click())

        onView(withId(R.id.round_up_switch))
            .perform(click())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.result))
            .check(matches(withText(containsString("25"))))
    }

    @Test
    fun calculate_gallons_to_cups_without_rounding() {
        onView(withId(R.id.amount_edit_text_field))
            .perform(typeText("1.55"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.base_gallons))
            .perform(click())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.result))
            .check(matches(withText(containsString("24.8"))))
    }
}