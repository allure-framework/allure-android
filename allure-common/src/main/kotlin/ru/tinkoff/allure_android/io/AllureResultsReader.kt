package ru.tinkoff.allure_android.io

import java.io.File

/**
 * @author Badya on 18.04.2017.
 */
interface AllureResultsReader {
    fun getAttachmentFile(src: String): File
}