package hu.bme.aut.android.mobweb_hf_calorie

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import hu.bme.aut.android.mobweb_hf_calorie.activity.ListActivity
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.addItem
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.clearList
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.orderByDateAndTime
import hu.bme.aut.android.mobweb_hf_calorie.util.MyMatcher
import hu.bme.aut.android.mobweb_hf_calorie.util.RecyclerViewItemCountAssertion
import org.hamcrest.core.AllOf.allOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime


@RunWith(AndroidJUnit4ClassRunner::class)
class CalorieListTest {

    @Before
    fun setup() {
        val activityScenario = ActivityScenario.launch(ListActivity::class.java)

        clearList()
    }

    @Test
    fun whenItemAdded_ThenDataOnUIMatches() {
        //when
        addItem("testName", 50)

        //then
        onView(withId(R.id.rv_calorie)).check(RecyclerViewItemCountAssertion(1))

        onView(withId(R.id.rv_calorie))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("testName")))))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("50")))))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("Workout")))))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText(getCurrentDateInDisplayFormat())))))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText(getCurrentTimeInDisplayFormat())))))

    }

    @Test
    fun givenMultipleItems_whenDeleteAllCalled_ThenItemsAreDeleted() {
        //given
        addItem("testName1", 50)
        addItem("testName2", 150)

        onView(withId(R.id.rv_calorie)).check(RecyclerViewItemCountAssertion(2))

        //when
        clearList()

        //then
        onView(withId(R.id.rv_calorie)).check(RecyclerViewItemCountAssertion(0))
    }

    @Test
    fun givenItemWithDescription_WhenItemIsClicked_ThenDescriptionIsShown() {
        //given
        val myDesc = "My description for this calorie event."
        addItem("testName1", 50, myDesc)

        //when
        onView(withText("testName1"))
            .perform(click())

        //then
        onView(withId(R.id.frag_desc))
            .check(matches(withText(myDesc)))
    }

    @Test
    fun givenWorkoutTypeItem_ThenViewPagerHasTwoPages() {
        //given
        addItem("testName1", 50)

        //when - swipe left 2x to get to third page
        onView(withText("testName1"))
            .perform(click())

        onView(withId(R.id.mainViewPager))
            .perform(swipeLeft())
            .perform(swipeLeft())

        //then - assert that we are only at the 2nd page
        onView(allOf(withId(R.id.linLayoutDiagram)))
            .check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun givenTwoItems_WhenOrderedByDate_ThenOrderIsCorrect() {
        //given
        val dateTimePast = LocalDateTime.of(2010, 2, 14, 10, 10)
        val dateTimeFuture = LocalDateTime.of(2030, 11, 10, 10, 10)

        addItem("testEvent1", 50, dateTime = dateTimeFuture)
        addItem("testEvent2", 50, dateTime = dateTimePast)

        //when
        orderByDateAndTime()

        //then
        onView(withId(R.id.rv_calorie))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("2010.02.14")))))
            .check(matches(MyMatcher.atPosition(1, hasDescendant(withText("2030.11.10")))))

    }

    @Test
    fun whenDeleteAllSelected_ThenDialogIsDisplayed(){
        //when
        onView(withContentDescription("More options"))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withText("Delete All"))
            .perform(click())

        //then
        onView(withText(R.string.areyousure))
            .check(matches(isCompletelyDisplayed()))

        onView(withText(R.string.cancel))
            .check(matches(isCompletelyDisplayed()))

        onView(withText(R.string.ok))
            .check(matches(isCompletelyDisplayed()))
    }

    private fun getCurrentDateInDisplayFormat(): String {
        val now = LocalDateTime.now()
        return String.format("%04d", now.year) + "." + String.format("%02d", now.month.value) + "." + String.format(
            "%02d",
            now.dayOfMonth
        )
    }

    private fun getCurrentTimeInDisplayFormat(): String {
        val now = LocalDateTime.now()
        return String.format("%02d", now.hour) + ":" + String.format("%02d", now.minute)
    }
}