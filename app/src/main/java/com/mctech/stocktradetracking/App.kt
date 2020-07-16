package com.mctech.stocktradetracking

import android.app.Application
import com.instacart.library.truetime.TrueTime
import com.mctech.stocksharetracking.feature.stock_share_filter.stockShareFilterViewModelModule
import com.mctech.stocktradetracking.di.*
import com.mctech.stocktradetracking.di.data.*
import com.mctech.stocktradetracking.feature.stock_share.stockShareViewModelModule
import com.mctech.stocktradetracking.feature.timeline_balance.timelineBalanceViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
        initNtpServer()
    }

    private fun initDependencyInjection() {
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    // Libraries
                    loggingModule,
                    databaseModule,
                    navigatorModule,
                    coroutineScopeModule,

                    // Features
                    stockShareDataModule,
                    stockShareNetworkingModule,
                    stockShareViewModelModule,
                    stockShareUseCasesModule,

                    stockShareFilterDataModule,
                    stockShareFilterViewModelModule,
                    stockShareFilterUseCasesModule,

                    timelineBalanceDataModule,
                    timelineBalanceViewModel,
                    timelineUseCasesModule
                )
            )
        }
    }

    private fun initNtpServer() {
        GlobalScope.launch(Dispatchers.IO){
            TrueTime.build()
                .withNtpHost("time.google.com")
                .withSharedPreferencesCache(this@App)
                .initialize()
        }
    }
}
