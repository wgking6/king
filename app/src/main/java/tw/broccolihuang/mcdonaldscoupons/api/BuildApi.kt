package tw.broccolihuang.mcdonaldscoupons.api

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import tw.broccolihuang.mcdonaldscoupons.api.common.SourceInfo
import tw.broccolihuang.mcdonaldscoupons.firestore.Config
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class BuildApi {
    companion object {
        fun getSourceInfo(config: Config): SourceInfo {
            return SourceInfo(config.app_version, SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Date()), config.device_uuid, config.model_id, config.os_version, config.platform)
        }
    }

    private lateinit var retrofit: Retrofit

    fun <S> create(serviceClass: Class<S>, baseUrl: String): S {
        val gson = GsonBuilder()
                .serializeNulls()
                .create()

        // create retrofit
        retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .client(httpBuilder.build())
                .build()

        return retrofit.create(serviceClass)
    }

    private val httpBuilder: OkHttpClient.Builder get() {
        // create http client
        val httpClient = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val original = chain.request()

                    //header
                    val request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .method(original.method(), original.body())
                            .build()

                    return@Interceptor chain.proceed(request)
                })
                .readTimeout(30, TimeUnit.SECONDS)

        // log interceptor
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)

        return httpClient
    }
}