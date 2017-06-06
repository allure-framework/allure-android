package ru.tinkoff.allure_android.model

import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * @author Badya on 14.04.2017.
 */
class TestResult(
        @SerializedName("uuid") val uuid: String = UUID.randomUUID().toString(),
        name: String? = null,
        @SerializedName("historyId") var historyId: String? = null,
        @SerializedName("testCaseId") var testCaseId: String? = null,
        @SerializedName("rerunOf") var rerunOf: String? = null,
        @SerializedName("fullName") var fullName: String? = null,
        @SerializedName("labels") var labels: List<Label> = ArrayList(),
        @SerializedName("links") var links: List<Link> = ArrayList()
) : ExecutableItem(name = name) {

    override fun withName(name: String?): TestResult {
        return super.withName(name) as TestResult
    }

    override fun withStatus(status: Status?): TestResult {
        return super.withStatus(status) as TestResult
    }

    override fun withStatusDetails(statusDetails: StatusDetails?): TestResult {
        return super.withStatusDetails(statusDetails) as TestResult
    }

}