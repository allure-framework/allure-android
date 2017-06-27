package ru.tinkoff.allure

import org.hamcrest.Matcher
import org.junit.Assert.assertThat
import org.junit.runners.model.MultipleFailureException
import ru.tinkoff.allure.model.ExecutableItem
import ru.tinkoff.allure.model.Status
import ru.tinkoff.allure.model.StatusDetails
import ru.tinkoff.allure.model.TestResult

/**
 * @author Badya on 01.06.2017.
 */

// todo: use lifecycle instead of direct storage access
class SoftAssert {
    companion object {
        @JvmStatic
        inline fun softly(block: SoftAssert.() -> Unit): Unit {
            with(SoftAssert()) {
                block()
                verify()
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

    private inline fun checkSucceeds(callable: () -> Unit): Unit {
        try {
            callable()
        } catch (t: Throwable) {
            addError(t)
        }
    }

    @JvmOverloads
    fun <T> checkThat(reason: String = "", value: T, matcher: Matcher<T>) {
        checkSucceeds {
            assertThat(reason, value, matcher)
        }
    }

    private fun addError(error: Throwable) {
        errorsMap.getOrPut(AllureStorage.getCurrentStep(), defaultValue = { mutableListOf<Throwable>() }).add(error)
    }
}