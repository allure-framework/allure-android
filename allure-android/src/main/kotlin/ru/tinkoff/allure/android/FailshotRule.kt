package ru.tinkoff.allure.android

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/**
 * @author Badya on 25.05.2017.
 */
class FailshotRule : TestRule {
    override fun apply(base: Statement, description: Description) = object : Statement() {
        override fun evaluate() {
            try {
                base.evaluate()
            } catch (t: Throwable) {
                deviceScreenshot("failshot")
                throw t
            }
        }
    }
}