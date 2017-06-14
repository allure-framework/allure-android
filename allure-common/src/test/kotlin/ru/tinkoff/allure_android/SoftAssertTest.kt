package ru.tinkoff.allure_android

import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.junit.Assert
import org.junit.ClassRule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.runner.RunWith
import ru.tinkoff.allure_android.SoftAssert.Companion.softly
import ru.tinkoff.allure_android.Step.Companion.step
import ru.tinkoff.allure_android.io.FileSystemResultsReader
import ru.tinkoff.allure_android.model.TestResult

/**
 * @author Badya on 01.06.2017.
 */
@RunWith(AllureRunner::class)
class SoftAssertTest {
    companion object {
        @ClassRule
        @JvmField
        val externalResRule = object : ExternalResource() {
            override fun after() {
                fun verify(it: TestResult) {
                    if (it.fullName!!.contains("check_soft_asserted_after_test")) {
                        Assert.assertTrue(it.steps[0].statusDetails?.message!!.contains("FirstAssert"))
                    } else if (it.fullName!!.contains("each_step_has_own_softAsserts")) {
                        Assert.assertTrue(it.steps[0].statusDetails?.message!!.contains("FirstAssert"))
                        Assert.assertTrue(it.steps[1].statusDetails?.message!!.contains("SecondAssert"))
                    } else if (it.fullName!!.contains("nested_steps_has_own_softAsserts")) {
                        Assert.assertTrue(it.steps[0].statusDetails?.message!!.contains("FirstAssert"))
                        Assert.assertTrue(it.steps[0].steps[0].statusDetails?.message!!.contains("SecondAssert"))
                    }
                }

                with(FileSystemResultsReader()) {
                    listResults()?.forEach {
                        if (it.nameWithoutExtension.contains("result")) {
                            read(it, TestResult::class.java).let { verify(it) }
                        }
                    }
                }
                FileSystemResultsReader.getDefaultResultsDir().deleteOnExit()
            }
        }
    }

    @Test
    fun no_softAssert_usage() {
        step("The Only One") {
            Assert.assertTrue(true)
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
            Assert.assertTrue(assertedSoftly)
        }
    }

    @Test(expected = AssertionError::class)
    fun soft_asserts_with_hard_fail_test() {
        step("The Only One") {
            softly { checkThat("FirstAssert", true, `is`(false)) }
            Assert.fail("Fail test")
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