package ru.tinkoff.allure_android.model

import java.util.*

/**
 * @author Badya on 14.04.2017.
 */
class StepResult(@Transient val uuid: String = UUID.randomUUID().toString()) : ExecutableItem() {
    override fun withName(name: String?): StepResult {
        return super.withName(name) as StepResult
    }

    override fun withParameters(params: Array<Parameter>): StepResult {
        return super.withParameters(params) as StepResult
    }
}