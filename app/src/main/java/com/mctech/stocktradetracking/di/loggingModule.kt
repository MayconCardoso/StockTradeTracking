package com.mctech.stocktradetracking.di

import com.mctech.library.logger.Logger
import com.mctech.library.logger.android.LogcatLogger
import org.koin.dsl.module

val loggingModule = module {
  single<Logger> { LogcatLogger }
}