package com.raywenderlich.droidquiz.data.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration3To4: Migration(3, 4) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("CREATE TABLE question_new (question_id INTEGER NOT NULL," +
        " text TEXT NOT NULL," +
        "difficulty TEXT NOT NULL," +
        "category TEXT NOT NULL, " +
        "PRIMARY KEY (question_id))"
    )
    database.execSQL("CREATE INDEX index_question_new_question_id ON question_new(question_id)")
    database.execSQL("INSERT INTO question_new (question_id, text, difficulty, category)" +
        "SELECT question_id, text, difficulty, category FROM question")
    database.execSQL("DROP TABLE question")
    database.execSQL("ALTER TABLE question_new RENAME TO question")
  }
}