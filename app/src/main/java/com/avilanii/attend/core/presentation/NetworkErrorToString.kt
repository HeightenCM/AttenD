package com.avilanii.attend.core.presentation

import android.content.Context
import com.avilanii.attend.R
import com.avilanii.attend.core.domain.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when(this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_unknown
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.UNKNOWN -> R.string.error_unknown
        NetworkError.NOT_FOUND -> R.string.error_not_found
        NetworkError.CONFLICT -> R.string.error_conflict
        NetworkError.BAD_REQUEST -> R.string.error_bad_request
    }
    return context.getString(resId)
}