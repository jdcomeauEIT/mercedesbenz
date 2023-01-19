package com.example.mercedesbenz.di

import com.example.mercedesbenz.model.rest.API_KEY
import com.example.mercedesbenz.model.rest.BASE_URL
import com.example.mercedesbenz.model.rest.ServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideServiceApi(retrofitClient: Retrofit): ServiceApi{
        return retrofitClient.create(ServiceApi::class.java)
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient, converter: Converter.Factory) =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converter)
            .client(client)
            .build()


    @Provides
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    setLevel(HttpLoggingInterceptor.Level.HEADERS)
                }
            )
            .build()
    }

    @Provides
    fun providesInterceptor(): Interceptor {
        return Interceptor {chain ->
            var original = chain.request()
            val token = API_KEY
            val url = original.url.newBuilder().addQueryParameter("apikey", token).build()
            original = original.newBuilder().url(url).build()
            chain.proceed(original)
        }
    }

    @Provides
    fun providesConverter(): Converter.Factory = MoshiConverterFactory.create()
}