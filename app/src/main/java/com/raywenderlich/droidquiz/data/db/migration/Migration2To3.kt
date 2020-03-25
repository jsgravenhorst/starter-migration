package com.raywenderlich.droidquiz.data.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration2To3: Migration(2, 3) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE question ADD COLUMN category TEXT NOT NULL DEFAULT 'Android'")
  }

}

