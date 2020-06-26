package io.qameta.allure.kotlin

import java.lang.annotation.Inherited

/**
 * Wrapper annotation for [LabelAnnotation].
 */
@MustBeDocumented
@Inherited
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS)
annotation class LabelAnnotations(vararg val value: LabelAnnotation)