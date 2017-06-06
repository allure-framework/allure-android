package ru.tinkoff.allure_android.model

import com.google.gson.annotations.SerializedName

/**
 * @author Badya on 14.04.2017.
 */
abstract class ExecutableItem(
        @SerializedName("name") open var name: String? = null,
        @SerializedName("start") open var start: Long? = null,
        @SerializedName("stop") open var stop: Long? = null,
        @SerializedName("description") open var description: String? = null,
        @SerializedName("descriptionHtml") open var descriptionHtml: String? = null,
        @SerializedName("stage") open var stage: Stage? = null,
        @SerializedName("status") override var status: Status? = null,
        @SerializedName("statusDetails") override var statusDetails: StatusDetails? = null,
        @SerializedName("steps") override var steps: MutableList<StepResult> = ArrayList(),
        @SerializedName("attachments") override var attachments: MutableList<Attachment> = ArrayList(),
        @SerializedName("parameters") override var parameters: MutableList<Parameter> = ArrayList()
) : WithSteps, WithAttachments, WithParameters, WithStatusDetails {


    open fun withName(name: String?): ExecutableItem {
        this.name = name
        return this
    }

    open fun withStatus(status: Status?): ExecutableItem {
        this.status = status
        return this
    }

    open fun withStatusDetails(statusDetails: StatusDetails?): ExecutableItem {
        this.statusDetails = statusDetails
        return this
    }

    open fun withParameters(params: Array<Parameter>): ExecutableItem {
        parameters.addAll(params)
        return this
    }
}

