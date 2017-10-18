package ru.tinkoff.allure.utils

import ru.tinkoff.allure.annotations.DisplayName
import ru.tinkoff.allure.annotations.Issue
import ru.tinkoff.allure.model.Link
import java.math.BigInteger
import java.security.MessageDigest

/**
 * @author Badya on 01.06.2017.
 */

const val ISSUE_LINK_TYPE = "issue"

fun org.junit.runner.Description.getMethodDisplayName(): String {
    return getAnnotation(DisplayName::class.java)?.value ?: methodName
}

fun org.junit.runner.Description.getClassDisplayName(): String {
    return testClass.getAnnotation(DisplayName::class.java)?.value ?: className
}

fun org.junit.runner.Description.getLinks(): List<Link> {
    return getAnnotationsOnClass(ru.tinkoff.allure.annotations.Link::class.java).map { createLink(it) } +
            getAnnotationsOnMethod(ru.tinkoff.allure.annotations.Link::class.java).map { createLink(it) } +
            getAnnotationsOnClass(Issue::class.java).map { createLink(it) } +
            getAnnotationsOnMethod(Issue::class.java).map { createLink(it) }

}

fun <T : Annotation> org.junit.runner.Description.getAnnotationsOnMethod(clazz: Class<T>): List<T> {
    return listOfNotNull(getAnnotation(clazz)) + extractRepeatable(clazz)
}

fun <T : Annotation> org.junit.runner.Description.getAnnotationsOnClass(clazz: Class<T>): List<T> {
    return listOfNotNull(testClass.getAnnotation(clazz))
}

fun createLink(link: ru.tinkoff.allure.annotations.Link): Link {
    return createLink(link.value, link.name, link.url, link.type)
}

fun createLink(link: Issue): Link {
    return createIssueLink(link.value)
}

fun createIssueLink(value: String): Link {
    return createLink(value, "", null, ISSUE_LINK_TYPE)
}

fun createLink(value: String?, name: String?, url: String?, type: String): Link {
    return Link(name = value ?: name, url = url ?: getLinkUrl(name, type), type = type)
}

fun getLinkUrl(name: String?, type: String): String? {
    val pattern = System.getProperty("allure.link.$type.pattern") ?: return null
    return pattern.replace("\\{}", name ?: "")
}

@Suppress("UNUSED_PARAMETER")
fun <T : Annotation> extractRepeatable(clazz: Class<T>): List<T> {
    // TODO: support Repeatable
    return emptyList()
}

fun getPackage(testClass: Class<*>): String {
    return testClass.`package`.name
}

fun org.junit.runner.Description.getHistoryId(): String {
    return BigInteger(1, md5(className + methodName)).toString(16)
}

fun md5(source: String): ByteArray {
    return MessageDigest.getInstance("md5").digest(source.toByteArray())
}