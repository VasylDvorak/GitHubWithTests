package com.geekbrains.tests

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.geekbrains.tests.view.search.MainActivity
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun activitySearch_IsWorking() {
        onView(withId(R.id.searchEditText)).perform(click())
        onView(withId(R.id.searchEditText)).perform(replaceText("algol"), closeSoftKeyboard())
        onView(withId(R.id.searchEditText)).perform(pressImeActionButton())

        onView(isRoot()).perform(delay())
        onView(withId(R.id.totalCountTextViewMainActivity))
            .check(matches(withText(R.string.number_of_results)))

        onView(withId(R.id.totalCountTextViewMainActivity))
            .check(matches(isCompletelyDisplayed()))
    }
    @Test
    fun activityProgressBar_Check() {
        onView(withId(R.id.progressBar)).check(matches(withEffectiveVisibility(Visibility.GONE)))
    }
    @Test
    fun activityEditText_Check() {
        onView(withId(R.id.searchEditText)).check(matches(withHint(R.string.search_hint)))
        onView(withId(R.id.searchEditText)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun activityButton_Check() {
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isClickable()))
        onView(withId(R.id.toDetailsActivityButton)).check(matches(withText(R.string.to_details)))
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isCompletelyDisplayed()))

    }



    @Test
    fun activityEditText_HasHint() {
        onView(withId(R.id.searchEditText)).check(matches(withHint(R.string.search_hint)))
        onView(withId(R.id.searchEditText)).check(matches(isCompletelyDisplayed()))
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

    @After
    fun close() {
        scenario.close()
    }
}
