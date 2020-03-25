package com.raywenderlich.droidquiz.data.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration1To4: Migration(1, 4) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE question ADD COLUMN difficulty TEXT NOT NULL DEFAULT '0'")
    database.execSQL("ALTER TABLE question ADD COLUMN category TEXT NOT NULL DEFAULT 'Android'")
  }
}