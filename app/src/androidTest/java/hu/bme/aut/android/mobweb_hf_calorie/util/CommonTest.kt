package hu.bme.aut.android.mobweb_hf_calorie.util

import android.widget.DatePicker
import android.widget.TimePicker
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.*
import hu.bme.aut.android.mobweb_hf_calorie.R
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matchers
import org.hamcrest.core.AllOf
import org.hamcrest.core.AllOf.allOf
import java.time.LocalDateTime


object CommonTest {

    fun orderByDateAndTime(){
        onView(withContentDescription("More options"))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText("Order By"))
            .perform(click())

        onView(withText("Date and Time"))
            .perform(click())
    }

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

    fun addItem(name: String, calories: Int, description: String = "", dateTime: LocalDateTime = LocalDateTime.now(), type: String = "Workout") {
        onView(withId(R.id.fab))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.svDialogNewEvent))
            .check(matches(isDisplayed()))

        onView(AllOf.allOf(withId(R.id.nameET), isDisplayed()))
            .perform(ViewActions.replaceText(name))

        onView(AllOf.allOf(withId(R.id.calorieCountET), isDisplayed()))
            .perform(ViewActions.replaceText(calories.toString()))

        onView(withClassName(Matchers.equalTo(DatePicker::class.java.name)))
            .perform(PickerActions.setDate(dateTime.year, dateTime.monthValue, dateTime.dayOfMonth))

        onView(withClassName(Matchers.equalTo(TimePicker::class.java.name)))
            .perform(PickerActions.setTime(dateTime.hour, dateTime.minute))

        onView(withId(R.id.typeSpinner)).perform(click())
        onView(withText(type)).inRoot(isPlatformPopup()).perform(click());

        onView(AllOf.allOf(withId(R.id.descriptionET), isDisplayed()))
            .perform(ViewActions.replaceText(description))

        onView(withText("OK"))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    fun getCurrentDateInDisplayFormat(): String {
        val now = LocalDateTime.now()
        return String.format("%04d", now.year) + "." + String.format("%02d", now.month.value) + "." + String.format(
                "%02d",
                now.dayOfMonth
        )
    }

    fun getCurrentTimeInDisplayFormat(): String {
        val now = LocalDateTime.now()
        return String.format("%02d", now.hour) + ":" + String.format("%02d", now.minute)
    }

}