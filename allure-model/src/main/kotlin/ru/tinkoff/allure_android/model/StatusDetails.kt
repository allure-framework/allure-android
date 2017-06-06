package ru.tinkoff.allure_android.model

import com.google.gson.annotations.SerializedName
import java.io.PrintWriter
import java.io.StringWriter

/**
 * @author Badya on 14.04.2017.
 */
data class StatusDetails(
        @SerializedName("known") var known: Boolean? = null,
        @SerializedName("muted") var muted: Boolean? = null,
        @SerializedName("flaky") var flaky: Boolean? = null,
        @SerializedName("message") var message: String? = null,
        @SerializedName("trace") var trace: String? = null) {

    companion object {
        @JvmStatic
        fun fromThrowable(e: Throwable?): StatusDetails? {
            if (e == null) return null
            return StatusDetails(
                    message = e.message,
                    trace = e.run {
                        val stringWriter = StringWriter()
                        printStackTrace(PrintWriter(stringWriter))
                        stringWriter.toString()
                    })
        }
    }
}