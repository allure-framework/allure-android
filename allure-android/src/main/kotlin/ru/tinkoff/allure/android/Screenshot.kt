package ru.tinkoff.allure.android

import android.os.Environment.getExternalStorageDirectory
import android.support.test.InstrumentationRegistry.getInstrumentation
import android.support.test.uiautomator.UiDevice
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * @author Badya on 31.05.2017.
 */
object Screenshot {
    @JvmStatic
    fun deviceScreenshot(tag: String) {
        val file = File.createTempFile("attachment", null, getExternalStorageDirectory())
        file.deleteOnExit()
        with(UiDevice.getInstance(getInstrumentation())) {
            waitForIdle(TimeUnit.SECONDS.toMillis(5))
            takeScreenshot(file)
        }
        AllureAndroidLifecycle.addAttachment(name = tag, type = "image/png", fileExtension = ".png", file = file)
    }
}
