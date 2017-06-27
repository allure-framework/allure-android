package ru.tinkoff.allure.android

import android.os.Bundle
import android.support.annotation.Keep
import android.support.test.runner.AndroidJUnitRunner

/**
 * @author Badya on 30.03.2017.
 */

@Keep
open class AllureAndroidRunner : AndroidJUnitRunner() {
    override fun onCreate(arguments: Bundle) {
        arguments.putCharSequence("listener", AllureAndroidListener::class.java.name)
        super.onCreate(arguments)
    }
}
