package ru.tinkoff.allure_android.utils

import android.util.Base64
import ru.tinkoff.allure_android.annotations.Description
import ru.tinkoff.allure_android.model.ExecutableItem
import java.lang.reflect.Method

/**
 * @author Badya on 29.05.2017.
 */

const val ALLURE_DESCRIPTIONS_PACKAGE = "allureDescriptions/"

fun processDescription(classLoader: ClassLoader, method: Method, item: ExecutableItem) {
    if (method.isAnnotationPresent(Description::class.java)) {
        if (method.getAnnotation(Description::class.java).useJavaDoc) {
            item.apply { descriptionHtml = classLoader.getResource(ALLURE_DESCRIPTIONS_PACKAGE + generateMethodSignatureHash(method)).readText() }
        } else {
            item.apply { descriptionHtml = method.getAnnotation(Description::class.java).value }
        }
    }
}

fun generateMethodSignatureHash(method: Method): String {
    val signature = method.name + method.parameterTypes.map { it.canonicalName }.joinToString(separator = " ")
    return Base64.encodeToString(md5(signature), Base64.URL_SAFE)
}


/* TODO: more annotations
public static Label createLabel(final Owner owner) {
    return new Label().withName("owner").withValue(owner.value());
}

public static Label createLabel(final Severity severity) {
    return new Label().withName("severity").withValue(severity.value().value());
}

public static Label createLabel(final Story story) {
    return new Label().withName("story").withValue(story.value());
}

public static Label createLabel(final Feature feature) {
    return new Label().withName("feature").withValue(feature.value());
}

public static Label createLabel(final Epic epic) {
    return new Label().withName("epic").withValue(epic.value());
}
*/