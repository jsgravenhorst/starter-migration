package com.raywenderlich.droidquiz.data.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1To2: Migration(1, 2) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE question ADD COLUMN difficulty INTEGER NOT NULL DEFAULT 0")
  }
}