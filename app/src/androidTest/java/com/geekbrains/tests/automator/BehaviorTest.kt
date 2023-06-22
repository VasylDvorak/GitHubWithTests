package com.geekbrains.tests.automator


import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import com.geekbrains.tests.R
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val packageName = context.packageName

    @Before
    fun setup() {

        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun test_MainActivityIsStarted() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {
        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"

        val searchButton: UiObject =
            uiDevice.findObject(UiSelector().description(context.getString(R.string.Search)))
        searchButton.click()

//        editText.click()
//        uiDevice.pressKeyCode(KeyEvent.KEYCODE_SEARCH)

        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextViewMainActivity")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text.toString(), "Number of results: 812")
    }

    @Test
    fun test_OpenDetailsScreen() {
        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(
                packageName,
                "toDetailsActivityButton"
            )
        )
        toDetails.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text, "Number of results: 0")
    }

    @Test
    fun test_functionOfButtonsOnDetailsScreen() {
        test_OpenDetailsScreen()
        val initialTotalCountFromDetailsActivity = getTotalCount("totalCountTextView")
        val incrementButton: UiObject2 = uiDevice.findObject(By.res(packageName,
            "incrementButton"))
        incrementButton.click()
        Assert.assertEquals(
            initialTotalCountFromDetailsActivity + 1,
            getTotalCount("totalCountTextView")
        )
        val decrementButton: UiObject2 = uiDevice.findObject(
            By.res(packageName, "decrementButton")
        )
        decrementButton.click()
        decrementButton.click()
        Assert.assertEquals(
            initialTotalCountFromDetailsActivity - 1,
            getTotalCount("totalCountTextView")
        )

    }

    @Test
    fun test_TotalCountTextView() {
        test_SearchIsPositive()
        val totalCountFromMainActivity = getTotalCount("totalCountTextViewMainActivity")
        //Находим кнопку
        val toDetails: UiObject2 = uiDevice.findObject(
            By.res(packageName, "toDetailsActivityButton")
        )
        toDetails.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )

        val totalCountFromDetailsActivity = changedText.text
            .substringAfterLast(": ").toInt()
        Assert.assertEquals(totalCountFromDetailsActivity, totalCountFromMainActivity)
    }

    private fun getTotalCount(stringId: String): Int {
        val totalCountText = uiDevice.findObject(By.res(packageName, stringId))
        val totalCountString = totalCountText.text
        return totalCountString.substringAfterLast(": ").toInt()
    }

    companion object {
        private const val TIMEOUT = 20000L
    }

}

