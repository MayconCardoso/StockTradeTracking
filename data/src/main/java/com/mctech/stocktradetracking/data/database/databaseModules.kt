package com.mctech.stocktradetracking.data.database

import androidx.room.Room
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
}