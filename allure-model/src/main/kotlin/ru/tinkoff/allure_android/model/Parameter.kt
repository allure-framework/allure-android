package ru.tinkoff.allure_android.model

import com.google.gson.annotations.SerializedName

/**
 * @author Badya on 14.04.2017.
 */
class Parameter(
        @SerializedName("name") var name: String? = null,
        @SerializedName("value") var value: String? = null) {

    fun withName(name: String?): Parameter {
        this.name = name
        return this
    }

    fun withValue(value: String?): Parameter {
        this.value = value
        return this
    }
}