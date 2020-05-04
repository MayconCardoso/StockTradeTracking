package com.mctech.stocktradetracking

import android.app.Application
import com.mctech.stocktradetracking.di.coroutineScopeModule
import com.mctech.stocktradetracking.di.data.databaseModule
import com.mctech.stocktradetracking.di.data.stockShareDataModule
import com.mctech.stocktradetracking.di.data.stockShareNetworkingModule
import com.mctech.stocktradetracking.di.data.timelineBalanceDataModule
import com.mctech.stocktradetracking.di.loggingModule
import com.mctech.stocktradetracking.di.navigatorModule
import com.mctech.stocktradetracking.di.useCasesModule
import com.mctech.stocktradetracking.feature.stock_share.stockShareViewModelModule
import com.mctech.stocktradetracking.feature.timeline_balance.timelineBalanceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initDependencyInjection()
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
                    coroutineScopeModule,

                    // App
                    useCasesModule,
                    navigatorModule,

                    // Features
                    stockShareDataModule,
                    stockShareNetworkingModule,
                    stockShareViewModelModule,

                    timelineBalanceDataModule,
                    timelineBalanceViewModel
                )
            )
        }
    }
}
