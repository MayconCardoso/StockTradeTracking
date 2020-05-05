package com.mctech.stocktradetracking.di

import com.mctech.stocktradetracking.domain.platform.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val coroutineScopeModule = module {

    single <CoroutineScope> {
        // To make the testing process easily
        return@single object : CoroutineScope{
            override fun io() = Dispatchers.IO
            override fun main() = Dispatchers.Main
            override fun default() = Dispatchers.Default
            override fun unconfined() = Dispatchers.Unconfined
        }
    }
}