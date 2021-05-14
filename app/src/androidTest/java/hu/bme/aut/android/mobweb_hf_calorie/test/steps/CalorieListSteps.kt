package hu.bme.aut.android.mobweb_hf_calorie.test.steps

import android.app.Activity
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule

import cucumber.api.java.Before
import cucumber.api.java.en.And
import cucumber.api.java.en.Given
import cucumber.api.java.en.Then
import cucumber.api.java.en.When
import hu.bme.aut.android.mobweb_hf_calorie.R
import hu.bme.aut.android.mobweb_hf_calorie.activity.ListActivity
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.addItem
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.clearList
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.getCurrentDateInDisplayFormat
import hu.bme.aut.android.mobweb_hf_calorie.util.CommonTest.getCurrentTimeInDisplayFormat
import hu.bme.aut.android.mobweb_hf_calorie.util.MyMatcher
import hu.bme.aut.android.mobweb_hf_calorie.util.RecyclerViewItemCountAssertion
import org.junit.Rule
import org.junit.runner.RunWith

@LargeTest
class CalorieListSteps{

    @Before("@setup-feature")
    fun setup() {
        val activityScenario = ActivityScenario.launch(ListActivity::class.java)

        clearList()
    }

    @When("adding {int} item to the list")
    fun adding_num_items_to_the_list(int: Int){
        for(i in 0 until int)
            addItem("testName", 50)
    }


    @Then("the list has {int} item")
    fun the_list_has_x_item(int: Int){
        onView(withId(R.id.rv_calorie)).check(RecyclerViewItemCountAssertion(int))
    }

    @And("the displayed data is correct")
    fun the_displayed_data_is_correct(){
        onView(withId(R.id.rv_calorie))
                .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("testName")))))
                .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("50")))))
                .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("Workout")))))
                .check(matches(MyMatcher.atPosition(0, hasDescendant(withText(getCurrentDateInDisplayFormat())))))
                .check(matches(MyMatcher.atPosition(0, hasDescendant(withText(getCurrentTimeInDisplayFormat())))))
    }

    @When("the list is cleared")
    fun the_list_is_cleared(){
        clearList()
    }

    @Given("one item with a description: {word}")
    fun one_item_with_a_description(string: String){
        addItem("testName1", 50, string)
    }

    @When("item is tapped")
    fun item_is_tapped(){
        onView(withText("testName1"))
                .perform(ViewActions.click())
    }

    @Then("the description shown on the screen is {word}")
    fun the_description_shown_on_the_screen_is(string: String){
        onView(withId(R.id.frag_desc))
                .check(matches(withText(string)))
    }

    @Given("{int} item with {word} type")
    fun adding_num_items_to_the_list_with_type(int: Int, string: String){
        for(i in 0 until int)
            addItem("testName", 50)
    }




}