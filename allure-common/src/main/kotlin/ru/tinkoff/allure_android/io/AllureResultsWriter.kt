package ru.tinkoff.allure_android.io

import ru.tinkoff.allure_android.model.TestResult
import ru.tinkoff.allure_android.model.TestResultContainer
import java.io.File
import java.io.InputStream

/**
 * @author Badya on 18.04.2017.
 */
interface AllureResultsWriter {
    fun write(testResult: TestResult)
    fun write(testResultContainer: TestResultContainer)
    fun write(dest: String, attachment: InputStream)
    fun copy(src: File, dest: File) // TODO: move+copy interface
}