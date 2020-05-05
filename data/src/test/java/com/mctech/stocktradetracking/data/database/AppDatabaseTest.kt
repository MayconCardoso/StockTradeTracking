package com.mctech.stocktradetracking.data.database

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.mctech.stocktradetracking.data.CoroutinesMainTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule

@ExperimentalCoroutinesApi
open class AppDatabaseTest {

    lateinit var database: AppDatabase

    @get:Rule
    val coroutinesTestRule =
        CoroutinesMainTestRule()

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