package com.mctech.stocktradetracking.di.data

import androidx.room.Room
import com.mctech.stocktradetracking.data.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "stock-trade-tracking-database"
        ).build()
    }

    single {
        val database: AppDatabase = get()
        database.stockShareDao()
    }

    single {
        val database: AppDatabase = get()
        database.timelineBalanceDao()
    }
}