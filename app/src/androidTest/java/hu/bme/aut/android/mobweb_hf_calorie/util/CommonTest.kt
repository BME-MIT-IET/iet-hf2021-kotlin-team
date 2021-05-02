package hu.bme.aut.android.mobweb_hf_calorie.util

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import hu.bme.aut.android.mobweb_hf_calorie.R
import org.hamcrest.core.AllOf

object CommonTest {


    fun clearList(){
        onView(withContentDescription("More options"))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText("Delete All"))
            .perform(click())

        onView(withText("OK"))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    fun addItem(name: String, calories: Int, description: String) {
        onView(withId(R.id.fab))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.svDialogNewEvent))
            .check(matches(isDisplayed()))

        onView(AllOf.allOf(withId(R.id.nameET), isDisplayed()))
            .perform(ViewActions.replaceText(name))

        onView(AllOf.allOf(withId(R.id.calorieCountET), isDisplayed()))
            .perform(ViewActions.replaceText(calories.toString()))

        onView(AllOf.allOf(withId(R.id.descriptionET), isDisplayed()))
            .perform(ViewActions.replaceText(description))

        onView(withText("OK"))
            .check(matches(isDisplayed()))
            .perform(click())
    }

}