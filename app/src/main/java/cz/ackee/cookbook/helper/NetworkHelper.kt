package cz.ackee.cookbook.helper

import android.content.Context
import cz.ackee.cookbook.R
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkHelper {

    companion object {

        fun createErrorMessage(context: Context, e: Exception): String {
            return when (e) {
                is UnknownHostException -> {
                    context.getString(R.string.connection_error)
                }
                is SocketTimeoutException -> {
                    context.getString(R.string.connection_error)
                }
                is HttpException -> {
                    if (e.code() == 404) {
                        context.getString(R.string.recipe_not_found)
                    } else {
                        context.getString(R.string.connection_error)
                    }
                }
                is ConnectException -> {
                    context.getString(R.string.connection_error)
                }
                else -> {
                    context.getString(R.string.unknown_error)
                }
            }
        }

    }

}