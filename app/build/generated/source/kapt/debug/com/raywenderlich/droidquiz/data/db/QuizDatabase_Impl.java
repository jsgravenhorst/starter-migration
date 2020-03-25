package com.raywenderlich.droidquiz.data.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuizDatabase_Impl extends QuizDatabase {
  private volatile QuestionDao _questionDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `question` (`question_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `text` TEXT NOT NULL, `difficulty` TEXT NOT NULL, `category` TEXT NOT NULL)");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_question_question_id` ON `question` (`question_id`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `answer` (`answer_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `question_id` INTEGER NOT NULL, `is_correct` INTEGER NOT NULL, `text` TEXT NOT NULL, FOREIGN KEY(`question_id`) REFERENCES `question`(`question_id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE INDEX IF NOT EXISTS `index_answer_question_id` ON `answer` (`question_id`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f97e4be6645272b10dee080a9177c265')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `question`");
        _db.execSQL("DROP TABLE IF EXISTS `answer`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsQuestion = new HashMap<String, TableInfo.Column>(4);
        _columnsQuestion.put("question_id", new TableInfo.Column("question_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestion.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestion.put("difficulty", new TableInfo.Column("difficulty", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsQuestion.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysQuestion = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesQuestion = new HashSet<TableInfo.Index>(1);
        _indicesQuestion.add(new TableInfo.Index("index_question_question_id", false, Arrays.asList("question_id")));
        final TableInfo _infoQuestion = new TableInfo("question", _columnsQuestion, _foreignKeysQuestion, _indicesQuestion);
        final TableInfo _existingQuestion = TableInfo.read(_db, "question");
        if (! _infoQuestion.equals(_existingQuestion)) {
          return new RoomOpenHelper.ValidationResult(false, "question(com.raywenderlich.droidquiz.data.model.Question).\n"
                  + " Expected:\n" + _infoQuestion + "\n"
                  + " Found:\n" + _existingQuestion);
        }
        final HashMap<String, TableInfo.Column> _columnsAnswer = new HashMap<String, TableInfo.Column>(4);
        _columnsAnswer.put("answer_id", new TableInfo.Column("answer_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswer.put("question_id", new TableInfo.Column("question_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswer.put("is_correct", new TableInfo.Column("is_correct", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAnswer.put("text", new TableInfo.Column("text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAnswer = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysAnswer.add(new TableInfo.ForeignKey("question", "CASCADE", "NO ACTION",Arrays.asList("question_id"), Arrays.asList("question_id")));
        final HashSet<TableInfo.Index> _indicesAnswer = new HashSet<TableInfo.Index>(1);
        _indicesAnswer.add(new TableInfo.Index("index_answer_question_id", false, Arrays.asList("question_id")));
        final TableInfo _infoAnswer = new TableInfo("answer", _columnsAnswer, _foreignKeysAnswer, _indicesAnswer);
        final TableInfo _existingAnswer = TableInfo.read(_db, "answer");
        if (! _infoAnswer.equals(_existingAnswer)) {
          return new RoomOpenHelper.ValidationResult(false, "answer(com.raywenderlich.droidquiz.data.model.Answer).\n"
                  + " Expected:\n" + _infoAnswer + "\n"
                  + " Found:\n" + _existingAnswer);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f97e4be6645272b10dee080a9177c265", "dae4cee7d1ae558240aad2117b4cdebf");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "question","answer");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `question`");
      _db.execSQL("DELETE FROM `answer`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public QuestionDao questionsDao() {
    if (_questionDao != null) {
      return _questionDao;
    } else {
      synchronized(this) {
        if(_questionDao == null) {
          _questionDao = new QuestionDao_Impl(this);
        }
        return _questionDao;
      }
    }
  }
}
