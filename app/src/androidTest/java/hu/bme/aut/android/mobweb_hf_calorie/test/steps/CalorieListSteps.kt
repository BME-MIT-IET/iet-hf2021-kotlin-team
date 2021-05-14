package hu.bme.aut.android.mobweb_hf_calorie.test.steps

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
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
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.runner.RunWith
import java.time.LocalDateTime

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

    @When("adding {int} {word} item to the list")
    fun adding_num_items_to_the_list(int: Int, type: String){
        for(i in 0 until int)
            addItem("testName", 50, type = type)
    }

    @When("adding {int} item to the list with number")
    fun adding_num_items_to_the_list_with_number(int: Int){
        for(i in 0 until int)
            addItem("testName$i", 50)
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

    @When("tapping on first item")
    fun tapping_on_first_item(){
        onView(withId(R.id.rv_calorie))
                .perform(
                        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
                                ViewActions.click()))
    }

    @Then("the description shown on the screen is {word}")
    fun the_description_shown_on_the_screen_is(string: String){
        onView(withId(R.id.frag_desc))
                .check(matches(withText(string)))
    }

    @Given("{int} item with {word} type")
    fun num_item_with_string_type(int: Int, string: String){
        for(i in 0 until int)
            addItem("testName", 50, type = string)
    }

    @When("swiping left {int} time")
    fun swiping_left_num_time(int: Int){
        val viewPager = onView(withId(R.id.mainViewPager))
        for(i in 0 until int){
            viewPager.perform(ViewActions.swipeLeft())
        }

    }

    @Then("user is at the second page")
    fun user_is_at_the_second_page(){
        onView(AllOf.allOf(withId(R.id.linLayoutDiagram)))
                .check(matches(isCompletelyDisplayed()))
    }

    @Given("an item with date: {word} and time: {word}")
    fun an_item_with_date(date: String, time: String){
        val dateData = date.split(".")
        val timeData = time.split(":")
        val date = LocalDateTime.of(dateData[0].toInt(), dateData[1].toInt(), dateData[2].toInt(), timeData[0].toInt(), timeData[1].toInt())
        addItem("testEvent1", 50, dateTime = date)
    }

    @When("ordering by date and time")
    fun order_by_date_and_time(){
        CommonTest.orderByDateAndTime()
    }

    @Then("{int} th element has date: {word} and time: {word}")
    fun nt_element_has_date(int: Int, date: String, time: String){
        onView(withId(R.id.rv_calorie))
                .check(matches(MyMatcher.atPosition(int, hasDescendant(withText(date)))))
                .check(matches(MyMatcher.atPosition(int, hasDescendant(withText(time)))))
    }

    @When("delete all is selected")
    fun delete_all_is_selected(){
        onView(withContentDescription("More options"))
                .check(matches(isDisplayed()))
                .perform(ViewActions.click())

        onView(withText("Delete All"))
                .perform(ViewActions.click())
    }

    @Then("are you sure dialog is shown")
    fun are_your_sure_dialog_is_shown(){
        onView(withText(R.string.areyousure))
                .check(matches(isCompletelyDisplayed()))

        onView(withText(R.string.cancel))
                .check(matches(isCompletelyDisplayed()))

        onView(withText(R.string.ok))
                .check(matches(isCompletelyDisplayed()))
    }
    @Then("item has web page")
    fun item_has_web_page(){
        onView(withText("Web")).perform(ViewActions.click()).check(matches(ViewMatchers.isDisplayed()))
    }

    @When("order items by name")
    fun order_items_by_name(){
        onView(withContentDescription("More options"))
            .check(matches(isDisplayed()))
            .perform(ViewActions.click())

        onView(withText("Order By"))
            .perform(ViewActions.click())
        onView(withText("Name"))
            .perform(ViewActions.click())
    }

    @Then("order is correct after ordering by name")
    fun order_is_correct_after_ordering_by_name(){
        onView(withId(R.id.rv_calorie))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("testName0")))))
    }

@When("delete second item")
fun delete_second_item(){
    onView(withId(R.id.rv_calorie))
        .perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                1,
                ViewActions.longClick()
            )
        )
    onView(withText("Delete")).perform(ViewActions.click())
}

@Then("one item in list")
fun one_item_in_list(){
    onView(withId(R.id.rv_calorie))
        .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        .check(matches(MyMatcher.atPosition(0, hasDescendant(withText("testName0")))))
}

@When("edit first item name to {word}")
fun one_item_in_list(nameAfterEdit : String){
    onView(withId(R.id.rv_calorie))
        .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0,
            ViewActions.longClick()
        ))
    onView(withText("Edit")).perform(ViewActions.click())
    onView(AllOf.allOf(withId(R.id.nameET), ViewMatchers.isDisplayed()))
        .perform(ViewActions.replaceText(nameAfterEdit))
    onView(withText("OK")).perform(ViewActions.click())
}

@Then("check name changed to {word}")
fun check_name_changed(nameAfterEdit :String){
    onView(withId(R.id.rv_calorie))
        .check(matches(MyMatcher.atPosition(0, hasDescendant(withText(nameAfterEdit)))))
}

@And("navigate to diagram")
fun navigate_to_diagram(){
    onView(withText("Diagram")).perform(ViewActions.click())
}

    @Then("diagram is visible")
    fun diagram_is_visible(){
        onView(AllOf.allOf(withId(R.id.barChart), ViewMatchers.isDisplayed()))
    }

}