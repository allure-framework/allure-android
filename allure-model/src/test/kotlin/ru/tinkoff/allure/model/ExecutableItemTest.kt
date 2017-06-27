package ru.tinkoff.allure.model

import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Badya on 23.06.2017.
 */
class ExecutableItemTest {
    @Test
    fun `when no subSteps return status`() {
        val mainStep = StepResult().apply { status = Status.PASSED }
        assertEquals(Status.PASSED, mainStep.determineStatus())
    }

    @Test
    fun `when subStep FAILED should be FAILED`() {
        val failedSubStep = StepResult().apply { status = Status.FAILED }
        val mainStep = StepResult().apply {
            status = Status.PASSED
            steps.add(failedSubStep)
        }
        assertEquals(Status.FAILED, mainStep.determineStatus())
    }

    @Test
    fun `when subStep BROKEN should be BROKEN`() {
        val brokenSubStep = StepResult().apply { status = Status.BROKEN }
        val mainStep = StepResult().apply {
            status = Status.PASSED
            steps.add(brokenSubStep)
        }
        assertEquals(Status.BROKEN, mainStep.determineStatus())
    }

    @Test
    fun `when subSteps FAILED and BROKEN prefer FAILED`() {
        val failedSubStep = StepResult().apply { status = Status.FAILED }
        val brokenSubStep = StepResult().apply { status = Status.BROKEN }
        val mainStep = StepResult().apply {
            status = Status.PASSED
            steps.addAll(arrayOf(failedSubStep, brokenSubStep))
        }

        assertEquals(Status.FAILED, mainStep.determineStatus())
    }

    @Test
    fun `when subStep FAILED in subStep should be FAILED`() {
        val failedSubStep = StepResult().apply { status = Status.FAILED }
        val passedSubStep = StepResult().apply {
            status = Status.PASSED
            steps.add(failedSubStep)
        }
        val mainStep = StepResult().apply {
            status = Status.PASSED
            steps.add(passedSubStep)
        }

        assertEquals(Status.FAILED, mainStep.determineStatus())
    }
}