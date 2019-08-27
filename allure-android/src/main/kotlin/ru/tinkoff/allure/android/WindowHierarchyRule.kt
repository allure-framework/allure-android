package ru.tinkoff.allure.android

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import ru.tinkoff.allure.io.TEXT_XML
import ru.tinkoff.allure.io.XML_EXTENSION
import ru.tinkoff.allure.utils.createAttachmentFile

class WindowHierarchyRule : TestRule {
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                try {
                    base.evaluate()
                } catch (t: Throwable) {
                    after()
                    throw t
                }
            }
        }
    }

    private fun after() {
        val file = createAttachmentFile()
        with(UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())) {
            dumpWindowHierarchy(file)
        }
        AllureAndroidLifecycle.addAttachment(name = "window_hierarchy", type = TEXT_XML,
                fileExtension = XML_EXTENSION, file = file)
    }
}
