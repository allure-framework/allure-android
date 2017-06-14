package ru.tinkoff.allure_android.model

import com.google.gson.annotations.SerializedName

/**
 * @author Badya on 14.04.2017.
 */
class Parameter(
        @SerializedName("name") var name: String? = null,
        @SerializedName("value") var value: String? = null)