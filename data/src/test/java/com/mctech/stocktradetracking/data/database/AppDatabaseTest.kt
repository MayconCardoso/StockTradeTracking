package com.mctech.stocktradetracking.data.database

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.mctech.stocktradetracking.data.BaseCoroutineTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
open class AppDatabaseTest : BaseCoroutineTest() {

  lateinit var database: AppDatabase


  @Before
  fun initDb() {
    database = Room
      .inMemoryDatabaseBuilder(
        InstrumentationRegistry.getInstrumentation().context,
        AppDatabase::class.java
      )
      .allowMainThreadQueries()
      .build()
  }

  @After
  fun closeDb() {
    database.close()
  }

}