package cz.ackee.cookbook.helper

import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkHelper {

    companion object {

        fun createErrorMessage(e: Exception): String {
            return when (e) {
                is UnknownHostException -> {
                    "Connection error."
                }
                is SocketTimeoutException -> {
                    "Connection error."
                }
                is HttpException -> {
                    if (e.code() == 404) {
                        "Recipe not found"
                    } else {
                        "Connection error."
                    }
                }
                is ConnectException -> {
                    "Connection error."
                }
                else -> {
                    "Unknown error occurred."
                }
            }
        }

    }

}