package ru.tinkoff.allure_android

import org.hamcrest.Matcher
import org.junit.Assert.assertThat
import org.junit.runners.model.MultipleFailureException
import ru.tinkoff.allure_android.model.ExecutableItem
import ru.tinkoff.allure_android.model.Status
import ru.tinkoff.allure_android.model.StatusDetails
import ru.tinkoff.allure_android.model.TestResult

/**
 * @author Badya on 01.06.2017.
 */

// todo: use lifecycle instead of direct storage access
class SoftAssert {
    companion object {
        @JvmStatic
        inline fun <T> softly(block: SoftAssert.() -> T): T {
            with(SoftAssert()) {
                val result = run { block() }
                verify()
                return result
            }
        }
    }

    val errorsMap = mutableMapOf<String, MutableList<Throwable>>()

    fun verify() {
        errorsMap.forEach { (uuid, errList) ->
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
            AllureStorage.get(AllureStorage.getTest(), TestResult::class.java).apply { status = Status.FAILED }
        }
    }

    fun <T> checkSucceeds(callable: () -> T): T? {
        try {
            return run(callable)
        } catch (t: Throwable) {
            addError(t)
            return null
        }
    }

    @JvmOverloads
    fun <T> checkThat(reason: String = "", value: T, matcher: Matcher<T>) {
        checkSucceeds({
            assertThat(reason, value, matcher)
            value
        })
    }

    fun addError(error: Throwable) {
        errorsMap.getOrPut(AllureStorage.getCurrentStep(), defaultValue = { mutableListOf<Throwable>() }).add(error)
    }
}