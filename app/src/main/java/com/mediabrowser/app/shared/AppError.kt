package com.mediabrowser.app.shared

import androidx.annotation.StringRes
import com.mediabrowser.app.R

sealed class AppError(@get:StringRes val messageRes: Int) {
    data object Network : AppError(R.string.error_network)
    data object Server : AppError(R.string.error_server)
    data object Serialization : AppError(R.string.error_serialization)
    data class Unknown(val throwable: Throwable) : AppError(R.string.error_unknown)
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