package com.geekbrains.tests.automator

import android.widget.Button
import android.widget.TextView
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiCollection
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class OpenOtherAppsTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    @Test
    fun test_OpenSettings() {
        uiDevice.pressHome()
        val settingsApp: UiObject = uiDevice.findObject(UiSelector().description("Настройки"))
        settingsApp.clickAndWaitForNewWindow()
        val settingsValidation =
            uiDevice.findObject(UiSelector().packageName(settingsApp.packageName))
        Assert.assertTrue(settingsValidation.exists())
    }

    @Test
    fun test_OpenViber() {
        uiDevice.pressHome()
        uiDevice.swipe(500, 1500, 500, 0, 5)
        val appViews = UiScrollable(UiSelector().scrollable(true))
        val viberApp = appViews.getChildByText(
            UiSelector()
                .className(TextView::class.java.name), "Viber"
        )
        viberApp.clickAndWaitForNewWindow()
        val viberValidation =
            uiDevice.findObject(UiSelector().packageName(viberApp.packageName))
        Assert.assertTrue(viberValidation.exists())
        val Button: UiObject = uiDevice.findObject(
            UiSelector().className(Button::class.java)
        )
        Button.clickAndWaitForNewWindow()
    }
}
