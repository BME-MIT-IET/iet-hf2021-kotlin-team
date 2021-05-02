package hu.bme.aut.android.mobweb_hf_calorie

import android.view.InputDevice
import android.view.MotionEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import hu.bme.aut.android.mobweb_hf_calorie.activity.ListActivity
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.addItem
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.clearList
import hu.bme.aut.android.mobweb_hf_calorie.util.MyMatcher
import hu.bme.aut.android.mobweb_hf_calorie.util.RecyclerViewItemCountAssertion
import org.hamcrest.core.AllOf
import org.hamcrest.core.AllOf.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4ClassRunner::class)
class CalorieListTest {

    @Before
    fun setup(){
        val activityScenario = ActivityScenario.launch(ListActivity::class.java)

        clearList()
    }

    @Test
    fun whenItemAdded_ThenDataOnUIMatches(){
        addItem("testName", 50, "")

        onView(withId(R.id.rv_calorie)).check(RecyclerViewItemCountAssertion(1))

        onView(withId(R.id.rv_calorie))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("testName")))))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("50")))))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("Workout")))))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText(getCurrentDateInDisplayFormat())))))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText(getCurrentTimeInDisplayFormat())))))
    }

    @Test
    fun whenDeleteAllCalled_ThenItemsAreDeleted(){
        addItem("testName1", 50, "")
        addItem("testName2", 150, "")

        onView(withId(R.id.rv_calorie)).check(RecyclerViewItemCountAssertion(2))

        clearList()

        onView(withId(R.id.rv_calorie)).check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun givenItemWithDescription_WhenItemIsClicked_ThenDescriptionIsShown(){
        val myDesc = "My description for this calorie event."
        addItem("testName1", 50, myDesc)

        onView(withText("testName1"))
            .perform(click())

        onView(withId(R.id.frag_desc))
            .check(matches(withText(myDesc)))
    }

    @Test
    fun givenWorkoutTypeItem_ThenViewPagerHasTwoPages(){
        addItem("testName1", 50, "")

        onView(withText("testName1"))
            .perform(click())

        onView(withId(R.id.mainViewPager))
            .perform(swipeLeft())
            .perform(swipeLeft())

        onView(allOf(withId(R.id.linLayoutDiagram)))
            .check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun givenTwoItems_WhenOrderedByDate_ThenOrderIsCorrect(){
        onView(withId(R.id.fab))
            .perform(click())

        onView(allOf(withId(R.id.nameET), isDisplayed()))
            .perform(replaceText("testEvent1"))

        onView(allOf(withId(R.id.calorieCountET), isDisplayed()))
            .perform(replaceText("50"))

        val now = LocalDateTime.now()
        //onView(withText())



        onView(withText("OK"))
            .check(matches(isDisplayed()))
            .perform(click())
    }

    private fun getCurrentDateInDisplayFormat(): String{
        val now = LocalDateTime.now()
        return String.format("%04d", now.year) + "." + String.format("%02d", now.month.value) + "." + String.format("%02d", now.dayOfMonth)
    }

    private fun getCurrentTimeInDisplayFormat(): String{
        val now = LocalDateTime.now()
        return String.format("%02d", now.hour) + ":" + String.format("%02d", now.minute)
    }
}