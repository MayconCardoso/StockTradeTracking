package com.mctech.stocktradetracking.data.database.migration

import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object Migrations {
  val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
      database.execSQL("ALTER TABLE stock_share ADD COLUMN initialShareAmount INTEGER NOT NULL default 0")
      database.execSQL("ALTER TABLE stock_share ADD COLUMN initialPurchasePrice REAL NOT NULL default 0.0")
      database.execSQL("UPDATE stock_share set initialShareAmount = shareAmount")
      database.execSQL("UPDATE stock_share set initialPurchasePrice  = purchasePrice")
    }
  }
}

fun <T : RoomDatabase> RoomDatabase.Builder<T>.bindMigrations(): RoomDatabase.Builder<T> {
  addMigrations(
    Migrations.MIGRATION_1_2
  )
  return this
}
