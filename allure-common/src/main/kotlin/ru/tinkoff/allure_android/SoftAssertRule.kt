package ru.tinkoff.allure_android

import org.hamcrest.Matcher
import org.junit.Assert.assertThat
import org.junit.rules.Verifier
import org.junit.runners.model.MultipleFailureException
import ru.tinkoff.allure_android.model.ExecutableItem
import ru.tinkoff.allure_android.model.Status
import ru.tinkoff.allure_android.model.StatusDetails

/**
 * @author Badya on 01.06.2017.
 */

class SoftAssertRule : Verifier() {
    companion object {
        @JvmStatic
        fun <T : Any?> softly(block: SoftAssertRule.() -> T): T {
            with(SoftAssertRule()) {
                val result = run { block() }
                verify()
                return result
            }
        }
    }

    val lifecycle: AllureLifecycle = AllureCommonLifecycle

    val errorsMap = mutableMapOf<String, MutableList<Throwable>>()

    override fun verify() {
        errorsMap.forEach { uuid, errList ->
            AllureStorage.get(uuid, ExecutableItem::class.java).apply {
                run {
                    try {
                        MultipleFailureException.assertEmpty(errList)
                    } catch (t: Throwable) {
                        status = Status.FAILED
                        statusDetails = StatusDetails.fromThrowable(t)
                    }
                }
            }
            lifecycle.updateTestCase { status = Status.FAILED }
        }
    }

    fun <T : Any?> checkSucceeds(callable: () -> T): T? {
        try {
            return run(callable)
        } catch (t: Throwable) {
            addError(t)
            return null
        }
    }

    @JvmOverloads
    fun <T : Any?> checkThat(reason: String = "", value: T, matcher: Matcher<T>) {
        checkSucceeds({
            assertThat(reason, value, matcher)
            value
        })
    }

    fun addError(error: Throwable) {
        errorsMap.getOrPut(AllureStorage.getCurrentStep(), defaultValue = { mutableListOf<Throwable>() }).add(error)
    }
}