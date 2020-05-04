package com.mctech.stocktradetracking.di.data

import com.mctech.library.core.networking.RetrofitBuilder
import com.mctech.stocktradetracking.data.stock_share.api.StockSharePriceAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val stockShareNetworkingModule = module {
    single {
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // Create the OkHttp Client
        OkHttpClient.Builder()
            .addInterceptor(logger)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        RetrofitBuilder(
            apiURL = "http://192.168.0.14:3000/api/",
            httpClient = get()
        )
    }

    // Provide API
    single<StockSharePriceAPI> {
        val retrofit: Retrofit = get()
        retrofit.create(StockSharePriceAPI::class.java)
    }
}