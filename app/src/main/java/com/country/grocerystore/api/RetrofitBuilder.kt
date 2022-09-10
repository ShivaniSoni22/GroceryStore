package com.country.grocerystore.api

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import com.country.grocerystore.Constants
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetrofitBuilder {

    companion object {

        fun getRetrofitWithTimeout(context: Context): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val httpClientBuilder = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(Interceptor { chain -> onOnIntercept(chain, context) })
                .build()

            val retrofit = Retrofit.Builder()
                .client(httpClientBuilder)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit
        }

        @Throws(IOException::class)
        private fun onOnIntercept(chain: Interceptor.Chain, context: Context): Response {
            try {
                if (!isConnected(context)) {
                    throw IOException()
                }
                val response: Response = chain.proceed(chain.request())
                val bodyString = response.body.string()
                return response.newBuilder()
                    .body(bodyString.toResponseBody(response.body.contentType()))
                    .build()
            } catch (exception: Exception) {
                when (exception) {
                    is IOException -> Log.e(TAG, "No internet connection")
                    is HttpException -> Log.e(TAG, "Something went wrong!!")
                    else -> {}
                }
                exception.printStackTrace()
            }
            return chain.proceed(chain.request())
        }

        private fun isConnected(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }

    }
}