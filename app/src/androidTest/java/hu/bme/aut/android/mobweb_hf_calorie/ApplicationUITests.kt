package hu.bme.aut.android.mobweb_hf_calorie

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import hu.bme.aut.android.mobweb_hf_calorie.activity.ListActivity
import hu.bme.aut.android.mobweb_hf_calorie.activity.MainActivity
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.addItem
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.clearList
import hu.bme.aut.android.mobweb_hf_calorie.util.MyMatcher.atPosition
import org.hamcrest.CoreMatchers.anything
import org.junit.After
import org.junit.Before
import org.junit.Rule
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
    fun testDeleteWithMultipleElements() {
        testCreateTwoElement()
        testDeleteFirstElement()
        onView(withId(R.id.rv_calorie))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(atPosition(0, ViewMatchers.hasDescendant(ViewMatchers.withText("a")))))
    }

    private fun testCreateTwoElement() {
        addItem("b", 30, "description")
        addItem("a", 40, "desc")
    }

    private fun testDeleteFirstElement() {
        onView(withId(R.id.rv_calorie))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    longClick()
                )
            );
        onView(withText("Delete")).perform(click())

    }

}