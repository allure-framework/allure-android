package ru.tinkoff.allure.android

import android.support.annotation.Keep
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.uiautomator.UiDevice
import org.junit.runner.Description
import org.junit.runner.Result
import ru.tinkoff.allure.AllureLifecycle
import ru.tinkoff.allure.AllureRunListener

/**
 * @author Badya on 05.06.2017.
 */

@Keep
class AllureAndroidListener : AllureRunListener() {
    override val lifecycle: AllureLifecycle by lazy { AllureAndroidLifecycle }

    override fun testRunStarted(description: Description) {
        grantPermissions()
        super.testRunStarted()
    }

    override fun testRunFinished(result: Result) {
        super.testRunFinished()
    }

    private fun grantPermissions() {
        UiDevice.getInstance(getInstrumentation()).executeShellCommand("pm grant " + getInstrumentation().context.packageName + " android.permission.WRITE_EXTERNAL_STORAGE")
        UiDevice.getInstance(getInstrumentation()).executeShellCommand("pm grant " + getInstrumentation().context.packageName + " android.permission.READ_EXTERNAL_STORAGE")
    }
}