package ru.tinkoff.allure

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.tinkoff.allure.model.Stage
import ru.tinkoff.allure.model.StepResult
import ru.tinkoff.allure.model.TestResult
import ru.tinkoff.allure.model.TestResultContainer

/**
 * @author Badya on 02.06.2017.
 */
class AllureLifecycleTest {
    lateinit var lifecycle: AllureLifecycle
    lateinit var testContainer: TestResultContainer
    lateinit var testResult: TestResult
    lateinit var stepResult: StepResult

    @Before
    fun setUp() {
        lifecycle = AllureCommonLifecycle
        testContainer = TestResultContainer()
        testResult = TestResult()
        stepResult = StepResult()
    }

    @After
    fun tearDown() {
        AllureStorage.clearStepContext()
    }

    @Test
    fun test_startTestContainer_inserted() {
        lifecycle.startTestContainer(testContainer)
        Assert.assertSame("Container wasn't added to lifecycle", testContainer, AllureStorage.getContainer(testContainer.uuid))
    }

    @Test
    fun test_startTestContainer_insideAnotherContainer_hasChild() {
        val child = TestResultContainer()
        test_startTestContainer_inserted()
        lifecycle.startTestContainer(testContainer.uuid, child)
        Assert.assertTrue("TestContainer wasn't added to TestContainer", child.uuid in testContainer.children)
    }

    @Test
    fun test_scheduleTestCase_scheduled() {
        lifecycle.scheduleTestCase(testResult)
        Assert.assertEquals("TestResult wasn't SCHEDULED", testResult.stage, Stage.SCHEDULED)
    }

    @Test
    fun test_sheduleTestCase_container_hasChild() {
        lifecycle.startTestContainer(testContainer)
        lifecycle.scheduleTestCase(testContainer.uuid, testResult)

        Assert.assertTrue("TestResult wasn't added to TestContainer", testResult.uuid in testContainer.children)
    }

    @Test(expected = IllegalArgumentException::class)
    fun when_startStep_withoutTest_exception() {
        lifecycle.startStep(stepResult)
    }

    @Test
    fun when_startStep_push_to_stepContext() {
        lifecycle.scheduleTestCase(testResult)
        lifecycle.startTestCase(testResult.uuid)
        lifecycle.startStep(stepResult)
        Assert.assertEquals("Step not on top of stepContext", stepResult.uuid, AllureStorage.getCurrentStep())
    }

    @Test
    fun stopStep_removes_from_stepContext() {
        when_startStep_push_to_stepContext()
        lifecycle.stopStep()
        Assert.assertNotEquals("Step wasnt removed", stepResult.uuid, AllureStorage.getCurrentStep())
    }

    @Test
    fun severalTest_inContainer() {
        val testResult2 = TestResult()
        with(lifecycle) {
            startTestContainer(testContainer)
            scheduleTestCase(testContainer.uuid, testResult)
            startTestCase(testResult.uuid)
            stopTestCase()
            scheduleTestCase(testContainer.uuid, testResult2)
            startTestCase(testResult2.uuid)
            stopTestCase()
        }

        Assert.assertTrue("First TestResult not in container", testResult.uuid in testContainer.children)
        Assert.assertTrue("Second TestResult not in container", testResult2.uuid in testContainer.children)
    }
}