package com.raywenderlich.droidquiz.data.db;

import android.database.Cursor;
import androidx.collection.LongSparseArray;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.raywenderlich.droidquiz.data.model.Answer;
import com.raywenderlich.droidquiz.data.model.Question;
import com.raywenderlich.droidquiz.data.model.QuestionAndAllAnswers;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class QuestionDao_Impl implements QuestionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Question> __insertionAdapterOfQuestion;

  private final EntityInsertionAdapter<Answer> __insertionAdapterOfAnswer;

  private final EntityDeletionOrUpdateAdapter<Question> __deletionAdapterOfQuestion;

  private final SharedSQLiteStatement __preparedStmtOfClearQuestions;

  public QuestionDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQuestion = new EntityInsertionAdapter<Question>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `question` (`question_id`,`text`,`difficulty`,`category`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Question value) {
        stmt.bindLong(1, value.getQuestionId());
        if (value.getText() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getText());
        }
        if (value.getDifficulty() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDifficulty());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getCategory());
        }
      }
    };
    this.__insertionAdapterOfAnswer = new EntityInsertionAdapter<Answer>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `answer` (`answer_id`,`question_id`,`is_correct`,`text`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Answer value) {
        stmt.bindLong(1, value.getAnswerId());
        stmt.bindLong(2, value.getQuestionId());
        final int _tmp;
        _tmp = value.isCorrect() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        if (value.getText() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getText());
        }
      }
    };
    this.__deletionAdapterOfQuestion = new EntityDeletionOrUpdateAdapter<Question>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `question` WHERE `question_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Question value) {
        stmt.bindLong(1, value.getQuestionId());
      }
    };
    this.__preparedStmtOfClearQuestions = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM question";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Question question) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfQuestion.insert(question);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final Answer answer) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAnswer.insert(answer);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteQuestion(final Question question) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfQuestion.handle(question);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clearQuestions() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearQuestions.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClearQuestions.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Question>> getAllQuestions() {
    final String _sql = "SELECT * FROM question ORDER BY question_id";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"question"}, false, new Callable<List<Question>>() {
      @Override
      public List<Question> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfQuestionId = CursorUtil.getColumnIndexOrThrow(_cursor, "question_id");
          final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
          final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final List<Question> _result = new ArrayList<Question>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Question _item;
            final int _tmpQuestionId;
            _tmpQuestionId = _cursor.getInt(_cursorIndexOfQuestionId);
            final String _tmpText;
            _tmpText = _cursor.getString(_cursorIndexOfText);
            final String _tmpDifficulty;
            _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
            final String _tmpCategory;
            _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            _item = new Question(_tmpQuestionId,_tmpText,_tmpDifficulty,_tmpCategory);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<QuestionAndAllAnswers>> getQuestionAndAllAnswers() {
    final String _sql = "SELECT * FROM question";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"answer","question"}, true, new Callable<List<QuestionAndAllAnswers>>() {
      @Override
      public List<QuestionAndAllAnswers> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfQuestionId = CursorUtil.getColumnIndexOrThrow(_cursor, "question_id");
            final int _cursorIndexOfText = CursorUtil.getColumnIndexOrThrow(_cursor, "text");
            final int _cursorIndexOfDifficulty = CursorUtil.getColumnIndexOrThrow(_cursor, "difficulty");
            final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
            final LongSparseArray<ArrayList<Answer>> _collectionAnswers = new LongSparseArray<ArrayList<Answer>>();
            while (_cursor.moveToNext()) {
              if (!_cursor.isNull(_cursorIndexOfQuestionId)) {
                final long _tmpKey = _cursor.getLong(_cursorIndexOfQuestionId);
                ArrayList<Answer> _tmpAnswersCollection = _collectionAnswers.get(_tmpKey);
                if (_tmpAnswersCollection == null) {
                  _tmpAnswersCollection = new ArrayList<Answer>();
                  _collectionAnswers.put(_tmpKey, _tmpAnswersCollection);
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipanswerAscomRaywenderlichDroidquizDataModelAnswer(_collectionAnswers);
            final List<QuestionAndAllAnswers> _result = new ArrayList<QuestionAndAllAnswers>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final QuestionAndAllAnswers _item;
              final Question _tmpQuestion;
              if (! (_cursor.isNull(_cursorIndexOfQuestionId) && _cursor.isNull(_cursorIndexOfText) && _cursor.isNull(_cursorIndexOfDifficulty) && _cursor.isNull(_cursorIndexOfCategory))) {
                final int _tmpQuestionId;
                _tmpQuestionId = _cursor.getInt(_cursorIndexOfQuestionId);
                final String _tmpText;
                _tmpText = _cursor.getString(_cursorIndexOfText);
                final String _tmpDifficulty;
                _tmpDifficulty = _cursor.getString(_cursorIndexOfDifficulty);
                final String _tmpCategory;
                _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
                _tmpQuestion = new Question(_tmpQuestionId,_tmpText,_tmpDifficulty,_tmpCategory);
              }  else  {
                _tmpQuestion = null;
              }
              ArrayList<Answer> _tmpAnswersCollection_1 = null;
              if (!_cursor.isNull(_cursorIndexOfQuestionId)) {
                final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfQuestionId);
                _tmpAnswersCollection_1 = _collectionAnswers.get(_tmpKey_1);
              }
              if (_tmpAnswersCollection_1 == null) {
                _tmpAnswersCollection_1 = new ArrayList<Answer>();
              }
              _item = new QuestionAndAllAnswers();
              _item.setQuestion(_tmpQuestion);
              _item.setAnswers(_tmpAnswersCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  private void __fetchRelationshipanswerAscomRaywenderlichDroidquizDataModelAnswer(final LongSparseArray<ArrayList<Answer>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<Answer>> _tmpInnerMap = new LongSparseArray<ArrayList<Answer>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshipanswerAscomRaywenderlichDroidquizDataModelAnswer(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<Answer>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshipanswerAscomRaywenderlichDroidquizDataModelAnswer(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `answer_id`,`question_id`,`is_correct`,`text` FROM `answer` WHERE `question_id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "question_id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfAnswerId = CursorUtil.getColumnIndex(_cursor, "answer_id");
      final int _cursorIndexOfQuestionId = CursorUtil.getColumnIndex(_cursor, "question_id");
      final int _cursorIndexOfIsCorrect = CursorUtil.getColumnIndex(_cursor, "is_correct");
      final int _cursorIndexOfText = CursorUtil.getColumnIndex(_cursor, "text");
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey = _cursor.getLong(_itemKeyIndex);
          ArrayList<Answer> _tmpRelation = _map.get(_tmpKey);
          if (_tmpRelation != null) {
            final Answer _item_1;
            final int _tmpAnswerId;
            if (_cursorIndexOfAnswerId == -1) {
              _tmpAnswerId = 0;
            } else {
              _tmpAnswerId = _cursor.getInt(_cursorIndexOfAnswerId);
            }
            final int _tmpQuestionId;
            if (_cursorIndexOfQuestionId == -1) {
              _tmpQuestionId = 0;
            } else {
              _tmpQuestionId = _cursor.getInt(_cursorIndexOfQuestionId);
            }
            final boolean _tmpIsCorrect;
            if (_cursorIndexOfIsCorrect == -1) {
              _tmpIsCorrect = false;
            } else {
              final int _tmp;
              _tmp = _cursor.getInt(_cursorIndexOfIsCorrect);
              _tmpIsCorrect = _tmp != 0;
            }
            final String _tmpText;
            if (_cursorIndexOfText == -1) {
              _tmpText = null;
            } else {
              _tmpText = _cursor.getString(_cursorIndexOfText);
            }
            _item_1 = new Answer(_tmpAnswerId,_tmpQuestionId,_tmpIsCorrect,_tmpText);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
