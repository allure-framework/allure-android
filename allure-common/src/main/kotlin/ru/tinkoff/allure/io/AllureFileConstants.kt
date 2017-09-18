package ru.tinkoff.allure.io

import java.util.*

/**
 * @author Badya on 18.04.2017.
 */
object AllureFileConstants {
    @JvmField
    val TEST_RESULT_FILE_SUFFIX = "-result.json"
    @JvmField
    val TEST_RESULT_FILE_PATTERN = ".+-result.json".toRegex()
    @JvmField
    val TEST_RESULT_FILE_GLOB = "*-result.json"
    @JvmField
    val TEST_RESULT_CONTAINER_FILE_SUFFIX = "-container.json"
    @JvmField
    val TEST_RESULT_CONTAINER_FILE_PATTERN = ".+-container.json".toRegex()
    @JvmField
    val TEST_RESULT_CONTAINER_FILE_GLOB = "*-container.json"
    @JvmField
    val TEST_RUN_FILE_SUFFIX = "-testrun.json"
    @JvmField
    val TEST_RUN_FILE_GLOB = "*-testrun.json"
    @JvmField
    val ATTACHMENT_FILE_SUFFIX = "-attachment"
    @JvmField
    val ATTACHMENT_FILE_PATTERN = ".+-attachment.*".toRegex()
    @JvmField
    val TXT_EXTENSION = ".txt"
    @JvmField
    val TEXT_PLAIN = "text/plain"
    @JvmField
    val PNG_EXTENSION = ".png"
    @JvmField
    val IMAGE_PNG = "image/png"

    @JvmStatic
    fun generateTestResultName(uuid: String): String {
        return uuid + TEST_RESULT_FILE_SUFFIX
    }

    @JvmStatic
    fun generateTestResultName(): String {
        return generateTestResultName(UUID.randomUUID().toString())
    }

    @JvmStatic
    fun generateTestResultContainerName(): String {
        return generateTestResultContainerName(UUID.randomUUID().toString())
    }

    @JvmStatic
    fun generateTestResultContainerName(uuid: String): String {
        return uuid + TEST_RESULT_CONTAINER_FILE_SUFFIX
    }

    @JvmStatic
    fun generateAttachmentFileName(uuid: String, fileExtension: String?): String {
        return uuid + ATTACHMENT_FILE_SUFFIX + getExtension(fileExtension)
    }

    fun getExtension(fileExtension: String?): String {
        if (fileExtension.isNullOrBlank()) {
            return ""
        }
        return if (fileExtension?.get(0) == '.') fileExtension else ".$fileExtension"
    }
}