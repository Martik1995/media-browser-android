package com.mediabrowser.app.shared

import androidx.annotation.StringRes
import com.mediabrowser.app.R

sealed interface AppError {
    data object Network : AppError
    data object Server : AppError
    data object Serialization : AppError
    data class Unknown(val throwable: Throwable) : AppError
}

fun Throwable.toAppError(): AppError {
    return when (this) {
        is java.net.UnknownHostException,
        is java.net.SocketTimeoutException,
        is java.io.IOException -> AppError.Network
        is retrofit2.HttpException -> AppError.Server
        is kotlinx.serialization.SerializationException -> AppError.Serialization
        else -> AppError.Unknown(this)
    }
}

@StringRes
fun AppError.toMessageRes(): Int {
    return when (this) {
        AppError.Network -> R.string.error_network
        is AppError.Server -> R.string.error_server
        AppError.Serialization -> R.string.error_serialization
        is AppError.Unknown -> R.string.error_unknown
    }
}
