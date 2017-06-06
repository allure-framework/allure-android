package ru.tinkoff.allure_android.utils

import ru.tinkoff.allure_android.annotations.DisplayName
import ru.tinkoff.allure_android.annotations.Issue
import ru.tinkoff.allure_android.model.Link
import java.math.BigInteger
import java.security.MessageDigest

/**
 * @author Badya on 01.06.2017.
 */

const val ISSUE_LINK_TYPE = "issue"

fun getMethodDisplayName(description: org.junit.runner.Description): String {
    return description.getAnnotation(DisplayName::class.java)?.value ?: description.methodName
}

fun getClassDisplayName(description: org.junit.runner.Description): String? {
    return (description.testClass.getAnnotation(DisplayName::class.java) as? DisplayName)?.value ?: description.className
}

fun createLink(link: ru.tinkoff.allure_android.annotations.Link): Link {
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

fun getLinks(description: org.junit.runner.Description): List<Link> {
    return getAnnotationsOnClass(description, ru.tinkoff.allure_android.annotations.Link::class.java).map { createLink(it) } +
            getAnnotationsOnMethod(description, ru.tinkoff.allure_android.annotations.Link::class.java).map { createLink(it) } +
            getAnnotationsOnClass(description, Issue::class.java).map { createLink(it) } +
            getAnnotationsOnMethod(description, Issue::class.java).map { createLink(it) }

}

fun <T : Annotation> getAnnotationsOnMethod(description: org.junit.runner.Description, clazz: Class<T>): List<T> {
    return listOfNotNull(description.getAnnotation(clazz)) + extractRepeatable(clazz)
}

fun <T : Annotation> getAnnotationsOnClass(description: org.junit.runner.Description, clazz: Class<T>): List<T> {
    return listOfNotNull(description.testClass.getAnnotation(clazz))
}

@Suppress("UNUSED_PARAMETER")
fun <T : Annotation> extractRepeatable(clazz: Class<T>): List<T> {
    // TODO: support Repeatable
    return emptyList()
}

fun getPackage(testClass: Class<*>): String {
    return testClass.`package`.name
}

fun getHistoryId(description: org.junit.runner.Description): String {
    return BigInteger(1, md5(description.className + description.methodName)).toString(16)
}

fun md5(source: String): ByteArray {
    return MessageDigest.getInstance("md5").digest(source.toByteArray())
}