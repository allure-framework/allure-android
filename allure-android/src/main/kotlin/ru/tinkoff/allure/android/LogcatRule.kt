package ru.tinkoff.allure.android

import android.os.Environment
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class LogcatRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    before()
                    base.evaluate()
                } catch (t: Throwable) {
                    after()
                    throw t
                }
            }
        }
    }

    private fun before() {
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            executeShellCommand("logcat -c")
        }
    }

    private fun after() {
        val file = createTempFile("attachment", null, Environment.getExternalStorageDirectory())
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            val logcatResult = executeShellCommand("logcat -d")
            file.writeText(logcatResult)
        }
        AllureAndroidLifecycle.addAttachment(name = "logcat", type = "text/html",
                fileExtension = ".txt", file = file)
    }
}