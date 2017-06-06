package ru.tinkoff.allure_android.io

import ru.tinkoff.allure_android.serialization.SerializationProcessor
import ru.tinkoff.allure_android.serialization.gson.GsonSerializationProcessor
import java.io.File

/**
 * @author Badya on 18.04.2017.
 */
open class FileSystemResultsReader(val resultsDir: File = FileSystemResultsReader.getDefaultResultsDir(),
                                   val serializationProcessor: SerializationProcessor = GsonSerializationProcessor) : AllureResultsReader {

    companion object {
        @JvmStatic
        fun getDefaultResultsDir() = File(System.getProperty("allure.results.directory", "build/allure-results"))
    }

    override fun getAttachmentFile(src: String): File = File(resultsDir, src)

    fun <T> read(file: File, type: Class<T>): T = serializationProcessor.deserialize(file, type)

    fun listResults(): Array<out File>? = resultsDir.listFiles()
}