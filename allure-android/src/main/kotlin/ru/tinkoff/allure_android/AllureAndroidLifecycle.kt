package ru.tinkoff.allure_android

import android.content.Context
import android.os.Build
import android.os.Environment
import android.support.test.InstrumentationRegistry.getInstrumentation
import ru.tinkoff.allure_android.io.FileSystemResultsReader
import ru.tinkoff.allure_android.io.FileSystemResultsWriter
import java.io.File

/**
 * @author Badya on 06.06.2017.
 */
object AllureAndroidLifecycle : AllureLifecycle(FileSystemResultsReader(obtainDirectory("allure-results")), FileSystemResultsWriter(obtainDirectory("fork/allure-results")))

fun obtainDirectory(path: String): File =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) File(Environment.getExternalStorageDirectory(), path)
        else getInstrumentation().context.getDir(path, Context.MODE_WORLD_READABLE)