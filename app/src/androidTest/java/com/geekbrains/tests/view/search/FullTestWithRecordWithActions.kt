package com.geekbrains.tests.view.search


import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.geekbrains.tests.R
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


const val countResult = 16341
const val titleCount = "Number of results: "
const val hint = "Enter keyword e.g. android"
const val search = "pascal"


@LargeTest
@RunWith(AndroidJUnit4::class)
class FullTestWithRecordWithActions {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTestWithRecordWithActions() {

        val editText = onView(withId(R.id.searchEditText))
        editText.check(matches(withHint(hint)))

        val button = onView(withId(R.id.toDetailsActivityButton))
        button.check(matches(isDisplayed()))
            .check(matches(withText(R.string.to_details)))

        val button2 = onView(withId(R.id.searchButton))
        button2.check(matches(isDisplayed()))
            .check(matches(withText(R.string.Search)))

        editText.perform(click())
            .perform(replaceText(search), closeSoftKeyboard())

        button2.perform(click())
        onView(isRoot()).perform(delay())
        button.perform(click())

        val buttonMinus = onView(withText("-"))
        buttonMinus.check(matches(isDisplayed()))

        val textView = onView(withId(R.id.totalCountTextView))
        textView.check(matches(withText(titleCount + countResult.toString())))

        val buttonPlus = onView(withText("+"))
        buttonPlus.check(matches(isDisplayed()))

        buttonMinus.perform(click())

        textView.check(matches(withText(titleCount + (countResult - 1).toString())))
        buttonPlus.perform(click())
            .perform(click())

        textView.check(matches(withText(titleCount + (countResult + 1).toString())))
    }

    private fun delay(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $20 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(20000)
            }
        }
    }
}
