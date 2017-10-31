package ru.tinkoff.allure.android

import android.content.Context
import android.os.Build
import android.os.Environment
import android.support.test.InstrumentationRegistry.getInstrumentation
import ru.tinkoff.allure.AllureLifecycle
import ru.tinkoff.allure.io.FileSystemResultsReader
import ru.tinkoff.allure.io.FileSystemResultsWriter
import java.io.File

/**
 * @author Badya on 06.06.2017.
 */
object AllureAndroidLifecycle : AllureLifecycle(
        FileSystemResultsReader(obtainDirectory("allure-results")),
        FileSystemResultsWriter(obtainDirectory("allure-results")))

fun obtainDirectory(path: String): File {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        return File(Environment.getExternalStorageDirectory(), path)
    } else {
        return getInstrumentation().context.getDir(path, Context.MODE_PRIVATE)
    }
}