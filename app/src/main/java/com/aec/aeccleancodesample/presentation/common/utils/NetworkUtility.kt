package com.aec.aeccleancodesample.presentation.common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.viewbinding.BuildConfig
import com.aec.aeccleancodesample.R
import com.aec.aeccleancodesample.data.local.prefs.PreferencesUtility
import com.aec.aeccleancodesample.data.model.ApiError
import com.aec.aeccleancodesample.presentation.common.Result
import com.aec.aeccleancodesample.presentation.common.constants.PrefsConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import java.security.SecureRandom
import java.util.concurrent.TimeUnit.SECONDS
import javax.inject.Inject
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class NetworkUtility @Inject constructor(
    private val applicationContext: Context,
    private val preferencesUtility: PreferencesUtility
) {

    fun isOnline(): Boolean {

        val connectivityManager: ConnectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork) != null
    }

    fun getUnsafeOkHttpClient(): OkHttpClient {

        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }

            override fun checkClientTrusted(
                certs: Array<java.security.cert.X509Certificate>, authType: String
            ) {
                // no implementation needed
            }

            override fun checkServerTrusted(
                certs: Array<java.security.cert.X509Certificate>, authType: String
            ) {
                // no implementation needed
            }
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("TLSv1.2")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory


        val builder = OkHttpClient.Builder()
        builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        builder.hostnameVerifier { _, _ -> true }

        builder.addInterceptor { chain ->
            try {
                chain.proceed(getNewBuilder(chain))
            } catch (ex: Exception) {
                throw ex
            }
        }

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.connectTimeout(55, SECONDS).readTimeout(55, SECONDS)
            .writeTimeout(55, SECONDS).build()
    }

    private fun getNewBuilder(chain: Interceptor.Chain): Request {

        val newBuilder = chain.request().newBuilder()

        newBuilder.header(
            "Authorization",
            "Bearer " + preferencesUtility.getString(PrefsConstants.ACCESS_TOKEN)
        )

        return newBuilder.build()
    }

    private fun handleUnauthorized() {
        Toast.makeText(
            applicationContext,
            applicationContext.resources.getString(R.string.msg_unauthorized_user),
            Toast.LENGTH_LONG
        ).show()
        //applicationContext.startActivity(LoginActivity.getStartIntent(applicationContext, null))
    }

    fun <T : Any> safeApiCall(isHandleUnAuthorized: Boolean = true, apiToBeCalled: suspend () -> T): Flow<Result<T>> = flow {
        try {
            if (!isOnline()) {
                emit(
                    Result.Error(
                        ApiError(
                            NetworkError.NO_INTERNET.code,
                            applicationContext.resources.getString(R.string.msg_no_network)
                        )
                    )
                )
            } else {
                emit(Result.Loading())
                val response = apiToBeCalled.invoke()
                emit(Result.Success(response))
            }
        } catch (ex: Exception) {
            emit(Result.Error(handleNetworkException(ex, isHandleUnAuthorized)))
        }
    }

    private fun handleNetworkException(ex: Exception, isHandleUnAuthorized: Boolean): ApiError {
        if (ex is HttpException) {
            return when (ex.code()) {
                NetworkError.UNAUTHORIZED.code -> {
                    if (isHandleUnAuthorized) handleUnauthorized()
                    ApiError(
                        NetworkError.UNAUTHORIZED.code,
                        applicationContext.resources.getString(R.string.msg_unauthorized_user)
                    )
                }
                else -> {
                    ApiError(ex.code(), getErrorMessage(ex.code()))
                }
            }
        } else {
            return ApiError(
                NetworkError.UNKNOWN_ERROR.code,
                applicationContext.resources.getString(R.string.msg_can_not_get_data)
            )
        }
    }

    private fun getErrorMessage(responseCode: Int): String {
        when (responseCode) {

            NetworkError.UNAUTHORIZED.code -> return applicationContext.resources.getString(R.string.msg_unauthorized_user)

            NetworkError.FORBIDDEN.code -> return applicationContext.resources.getString(R.string.msg_forbidden_user)

            NetworkError.NOT_FOUND.code -> return applicationContext.resources.getString(R.string.msg_no_data)

            NetworkError.TOO_MANY_REQUESTS.code -> return applicationContext.resources.getString(R.string.msg_too_many_requests)

            NetworkError.TIMEOUT.code -> return applicationContext.resources.getString(R.string.msg_timeout)

            NetworkError.INTERNAL_SERVER_ERROR.code -> return applicationContext.resources.getString(R.string.msg_internal_service_error)

            else -> return applicationContext.resources.getString(R.string.msg_can_not_get_data)

        }
    }
}

enum class NetworkError(val code: Int) {
    NO_INTERNET(1000),
    UNKNOWN_ERROR(3000),

    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    TIMEOUT(408),
    TOO_MANY_REQUESTS(429),
    INTERNAL_SERVER_ERROR(500),
}
