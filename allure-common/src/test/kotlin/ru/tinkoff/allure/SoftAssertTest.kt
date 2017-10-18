package ru.tinkoff.allure

import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.allure.model.Status
import ru.tinkoff.allure.model.StepResult
import ru.tinkoff.allure.model.TestResult
import kotlin.test.assertTrue
import kotlin.test.fail

/**
 * @author Badya on 01.06.2017.
 */
@RunWith(AllureRunner::class)
class SoftAssertTest {
    @After
    fun after() {
        fun verify(it: TestResult) {
            fun checkStepResult(stepResult: StepResult, text: String) {
                assertTrue(stepResult.statusDetails?.message!!.contains(text))
                assertTrue(stepResult.status!! == Status.FAILED)
            }

            if (it.fullName!!.contains("check_soft_asserted_after_test")) {
                checkStepResult(it.steps[0], "FirstAssert")
            } else if (it.fullName!!.contains("each_step_has_own_softAsserts")) {
                checkStepResult(it.steps[0], "FirstAssert")
                checkStepResult(it.steps[1], "SecondAssert")
            } else if (it.fullName!!.contains("nested_steps_has_own_softAsserts")) {
                checkStepResult(it.steps[0], "FirstAssert")
                checkStepResult(it.steps[0].steps[0], "SecondAssert")
            }
        }

        verify(AllureStorage.getTestResult(AllureStorage.getTest()))
    }

    @Test
    fun no_softAssert_usage() {
        step("The Only One") {
            assertTrue(true)
        }
    }

    @Test
    fun softAssert_in_test() {
        softly { checkThat("FirstAssert", true, `is`(false)) }
    }

    @Test
    fun check_soft_asserted_after_test() {
        step("The Only One") {
            softly { checkThat("FirstAssert", true, `is`(false)) }
            val assertedSoftly = true
            assertTrue(assertedSoftly)
        }
    }

    @Test(expected = AssertionError::class)
    fun soft_asserts_with_hard_fail_test() {
        step("The Only One") {
            softly { checkThat("FirstAssert", true, `is`(false)) }
            fail("Fail test")
        }
    }

    @Test
    fun each_step_has_own_softAsserts() {
        step("First") {
            softly { checkThat("FirstAssert", true, `is`(false)) }
        }
        step("Second") {
            softly { checkThat("SecondAssert", true, `is`(false)) }
        }
    }

    @Test
    fun nested_steps_has_own_softAsserts() {
        step("First") {
            step("Second") {
                softly { checkThat("SecondAssert", true, `is`(false)) }
            }
            softly { checkThat("FirstAssert", true, `is`(false)) }
        }
    }

    @Test
    fun actual_can_be_null() {
        softly {
            checkThat("Null check", null, Matchers.isEmptyOrNullString())
        }
    }
}