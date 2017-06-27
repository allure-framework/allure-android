package ru.tinkoff.allure.android

import android.support.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.tinkoff.allure.Step.Companion.step

/**
 * @author Badya on 06.06.2017.
 */
@RunWith(AndroidJUnit4::class)
class AllureTest {
    @Rule
    @JvmField
    val failshot = FailshotRule()

    @Test
    fun test1() = step("Step1") {
        Assert.assertTrue(true)
    }

    @Test(expected = AssertionError::class)
    fun test2() {
        step("Step1") {
            Assert.fail()
        }
    }
}