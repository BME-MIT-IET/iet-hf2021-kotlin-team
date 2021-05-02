package hu.bme.aut.android.mobweb_hf_calorie

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import hu.bme.aut.android.mobweb_hf_calorie.activity.ListActivity
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.addItem
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.clearList
import hu.bme.aut.android.mobweb_hf_calorie.util.MyMatcher.atPosition
import org.hamcrest.CoreMatchers.*
import org.hamcrest.core.AllOf
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class ApplicationUITests {


    @Before
    fun setup() {
        val activityScenario = ActivityScenario.launch(ListActivity::class.java)
        clearList()
    }

    @Test
    fun givenTwoItem_WhenDelete_ThenSelectedDeleted() {
        //given
        addItem("b", 30, "description")
        addItem("a", 40, "desc")
        //when
        testDeleteFirstElement()
        //then
        onView(withId(R.id.rv_calorie))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(atPosition(0, ViewMatchers.hasDescendant(ViewMatchers.withText("a")))))
    }

    private fun testDeleteFirstElement() {
        onView(withId(R.id.rv_calorie))
            .perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                            0,
                            longClick()
                    )
            )
        onView(withText("Delete")).perform(click())
    }

    @Test
    fun givenOneItem_WhenModify_ThenAttributeChanged() {
        //given
        addItem("b", 30, "description")
        //when
        onView(withId(R.id.rv_calorie))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                    longClick()))
        onView(withText("Edit")).perform(click())
        onView(AllOf.allOf(withId(R.id.nameET), ViewMatchers.isDisplayed()))
            .perform(ViewActions.replaceText("Replaced"))
        onView(withText("OK")).perform(click())
        //then
        onView(withId(R.id.rv_calorie))
            .check(matches(atPosition(0, ViewMatchers.hasDescendant(ViewMatchers.withText("Replaced")))))
    }

    @Test
    fun givenItem_WhenClicked_ThenDiagramVisible() {
        //given
        addItem("b", 30, "description")
        //when
        onView(withId(R.id.rv_calorie))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                        click()))
        onView(withText("Diagram")).perform(click())
        //then
        onView(AllOf.allOf(withId(R.id.barChart), ViewMatchers.isDisplayed()))
    }

    @Test
    fun givenLunchItem_WhenClicked_ThenDetailsHasThreePages(){
        //given
        onView(withId(R.id.fab))
                .check(matches(ViewMatchers.isDisplayed()))
                .perform(click())

        onView(AllOf.allOf(withId(R.id.nameET), ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("Event"))

        onView(AllOf.allOf(withId(R.id.calorieCountET), ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText(30.toString()))

        onView(AllOf.allOf(withId(R.id.descriptionET), ViewMatchers.isDisplayed()))
                .perform(ViewActions.replaceText("description"))

        onView(withId(R.id.typeSpinner)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)),
                `is`("Lunch")))
                .inRoot(isPlatformPopup())
                .perform(click())

        onView(withText("OK"))
                .check(matches(ViewMatchers.isDisplayed()))
                .perform(click())

        //when
        onView(withId(R.id.rv_calorie))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                        click()))

        onView(withText("Diagram")).perform(click())
        //then
        onView(withText("Web")).perform(click()).check(matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun givenTwoItem_WhenOrderedByName_ThenOrderIsCorrect(){
        //given
        addItem("b", 30, "description")
        addItem("a", 40, "desc")
        //when
        onView(withContentDescription("More options"))
                .check(matches(isDisplayed()))
                .perform(click())

        onView(withText("Order By"))
                .perform(click())
        onView(withText("Name"))
                .perform(click())
        //then
        onView(withId(R.id.rv_calorie))
                .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
                .check(matches(atPosition(0, ViewMatchers.hasDescendant(ViewMatchers.withText("a")))))
    }

}