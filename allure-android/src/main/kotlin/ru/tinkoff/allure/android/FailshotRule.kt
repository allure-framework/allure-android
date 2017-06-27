package ru.tinkoff.allure.android

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.tinkoff.allure.android.Screenshot.deviceScreenshot

/**
 * @author Badya on 25.05.2017.
 */
class FailshotRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    base.evaluate()
                } catch (t: Throwable) {
                    failshot()
                    throw t
                }
            }
        }
    }

    private fun failshot() {
        deviceScreenshot("failshot")
    }

}