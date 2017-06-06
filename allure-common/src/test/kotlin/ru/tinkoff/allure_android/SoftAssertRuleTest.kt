package ru.tinkoff.allure_android

import org.hamcrest.core.IsEqual
import org.junit.Assert
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExternalResource
import org.junit.runner.RunWith
import ru.tinkoff.allure_android.SoftAssertRule.Companion.softly
import ru.tinkoff.allure_android.Step.Companion.step
import ru.tinkoff.allure_android.io.FileSystemResultsReader
import ru.tinkoff.allure_android.model.TestResult

/**
 * @author Badya on 01.06.2017.
 */
@RunWith(AllureRunner::class)
class SoftAssertRuleTest {
    companion object {
        @ClassRule
        @JvmField
        val externalResRule = object : ExternalResource() {
            override fun after() {
                fun verify(it: TestResult) {
                    if (it.fullName!!.contains("check_soft_asserted_after_test")
                            or it.fullName!!.contains("soft_asserts_with_hard_fail_test")) {
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

    @Rule
    @JvmField
    val softAssertRule = SoftAssertRule()

    @Test
    fun no_softAssert_usage() {
        step("The Only One") {
            Assert.assertTrue(true)
        }
    }

    @Test
    fun softAssert_in_test() {
        softAssertRule.checkThat("FirstAssert", true, IsEqual(false))
    }

    @Test
    fun check_soft_asserted_after_test() {
        step("The Only One") {
            softAssertRule.checkThat("FirstAssert", true, IsEqual(false))
            val assertedSoftly = true
            Assert.assertTrue(assertedSoftly)
        }
    }

    @Test(expected = AssertionError::class)
    fun soft_asserts_with_hard_fail_test() {
        step("The Only One") {
            softAssertRule.checkThat("FirstAssert", true, IsEqual(false))
            Assert.fail("Fail test")
        }
    }

    @Test
    fun each_step_has_own_softAsserts() {
        step("First") {
            softAssertRule.checkThat("FirstAssert", true, IsEqual(false))
        }
        step("Second") {
            softAssertRule.checkThat("SecondAssert", true, IsEqual(false))
        }
    }

    @Test
    fun nested_steps_has_own_softAsserts() {
        step("First") {
            step("Second") {
                softAssertRule.checkThat("SecondAssert", true, IsEqual(false))
            }
            softAssertRule.checkThat("FirstAssert", true, IsEqual(false))
        }
    }

    @Test
    fun softly_dsl_test() {
        softly {
            checkThat("FirstAssert", true, IsEqual(false))
        }
        val assertedSoftly = true
        Assert.assertTrue(assertedSoftly)
    }

}