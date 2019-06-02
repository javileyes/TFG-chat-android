package es.javiergimenez.chat.service.retrofit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import es.javiergimenez.chat.service.deserializer.DateTimeDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.joda.time.DateTime
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ChatRetrofit {

//    private const val BASE_URL = "http://80.211.4.233"
    private const val BASE_URL = "http://javiergimenez.es"
    const val AUTHORIZATION = "Authorization"
    const val BEARER = "Bearer "

    var chatApi: ChatApi


    init {
        val builder = Retrofit.Builder().baseUrl(BASE_URL)
        builder.client(createHttpClient())
        val retrofit = builder
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(createGson()))
            .build()

        chatApi = retrofit.create<ChatApi>(
            ChatApi::class.java)
    }

    private fun createGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(
            DateTime::class.java,
            DateTimeDeserializer()
        )
//        return gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        return gsonBuilder.create()
    }

    private fun createHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(loggingInterceptor)
        return httpClient.build()
    }


}