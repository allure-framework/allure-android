package ru.tinkoff.allure.android

import android.os.Bundle
import android.support.annotation.Keep
import android.support.multidex.MultiDex

/**
 * @author Badya on 13.04.2017.
 */
@Keep
class AllureMultiDexRunner : AllureAndroidRunner() {
    override fun onCreate(arguments: Bundle) {
        MultiDex.install(targetContext)
        super.onCreate(arguments)
    }
}
