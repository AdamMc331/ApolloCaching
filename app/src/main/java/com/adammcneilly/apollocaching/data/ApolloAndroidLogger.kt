package com.adammcneilly.apollocaching.data

import android.util.Log
import com.apollographql.apollo.Logger

/**
 * This is a custom implementation of a [Logger] from Apollo that will take any
 * messages and log them in the Android logcat for debugging purposes.
 */
class ApolloAndroidLogger : Logger {
    override fun log(priority: Int, message: String, t: Throwable?, vararg args: Any) {
        val formattedMessage = message.format(*args)
        val tag = ApolloAndroidLogger::class.java.simpleName

        when (priority) {
            Logger.DEBUG -> Log.d(tag, formattedMessage)
            Logger.WARN -> Log.w(tag, formattedMessage)
            Logger.ERROR -> Log.e(tag, formattedMessage, t)
            else -> Log.d(tag, formattedMessage)
        }
    }
}
