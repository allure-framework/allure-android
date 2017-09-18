package ru.tinkoff.allure.listener

import android.util.Log
import ru.tinkoff.allure.model.StepResult
import ru.tinkoff.allure.model.TestResult
import ru.tinkoff.allure.model.TestResultContainer

/**
 * @author Badya on 22.05.2017.
 */
class LogCatListener : ContainerLifecycleListener, TestLifecycleListener, StepLifecycleListener {
    companion object {
        const val TAG = "AllureAndroidLifecycle"
    }

    override fun beforeContainerStart(container: TestResultContainer) {
        debug(Event.START, container)
    }

    override fun beforeContainerUpdate(container: TestResultContainer) {
        debug(Event.UPDATE, container)
    }

    override fun beforeContainerStop(container: TestResultContainer) {
        debug(Event.STOP, container)
    }

    override fun beforeContainerWrite(container: TestResultContainer) {
        debug(Event.WRITE, container)
    }

    override fun beforeTestSchedule(result: TestResult) {
        debug(Event.SCHEDULE, result)
    }

    override fun beforeTestStart(result: TestResult) {
        debug(Event.START, result)
    }

    override fun beforeTestUpdate(result: TestResult) {
        debug(Event.UPDATE, result)
    }

    override fun beforeTestStop(result: TestResult) {
        debug(Event.STOP, result)
    }

    override fun beforeTestWrite(result: TestResult) {
        debug(Event.WRITE, result)
    }

    override fun beforeStepStart(result: StepResult) {
        debug(Event.START, result)
    }

    override fun beforeStepUpdate(result: StepResult) {
        debug(Event.UPDATE, result)
    }

    override fun beforeStepStop(result: StepResult) {
        debug(Event.STOP, result)
    }

    private fun debug(event: Event, entity: Any) {
        Log.d(TAG, when (entity) {
            is TestResult          -> "${event.name} test: ${entity.uuid} (${entity.fullName})"
            is TestResultContainer -> "${event.name} container: ${entity.uuid}"
            is StepResult          -> "${event.name} step: ${entity.uuid}"
            else                   -> ""
        })
    }

    enum class Event {
        SCHEDULE,
        START,
        UPDATE,
        STOP,
        WRITE
    }
}