package com.vaanisathi.db;

import androidx.annotation.NonNull;
import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteStatement;
import java.lang.Class;
import java.lang.NullPointerException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class PhraseDao_Impl implements PhraseDao {
  private final RoomDatabase __db;

  private final EntityInsertAdapter<FLNPhrase> __insertAdapterOfFLNPhrase;

  private final EntityInsertAdapter<NumeracyEntry> __insertAdapterOfNumeracyEntry;

  private final EntityInsertAdapter<VocabularyEntry> __insertAdapterOfVocabularyEntry;

  public PhraseDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertAdapterOfFLNPhrase = new EntityInsertAdapter<FLNPhrase>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `fln_phrases` (`id`,`phraseCode`,`domain`,`hindiText`,`santhaliOlChiki`,`santhaliRoman`,`audioFilename`,`gradeLevel`,`nipunCode`,`context`,`validated`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final FLNPhrase entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getPhraseCode() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getPhraseCode());
        }
        if (entity.getDomain() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getDomain());
        }
        if (entity.getHindiText() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getHindiText());
        }
        if (entity.getSanthaliOlChiki() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getSanthaliOlChiki());
        }
        if (entity.getSanthaliRoman() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getSanthaliRoman());
        }
        if (entity.getAudioFilename() == null) {
          statement.bindNull(7);
        } else {
          statement.bindText(7, entity.getAudioFilename());
        }
        statement.bindLong(8, entity.getGradeLevel());
        if (entity.getNipunCode() == null) {
          statement.bindNull(9);
        } else {
          statement.bindText(9, entity.getNipunCode());
        }
        if (entity.getContext() == null) {
          statement.bindNull(10);
        } else {
          statement.bindText(10, entity.getContext());
        }
        final int _tmp = entity.getValidated() ? 1 : 0;
        statement.bindLong(11, _tmp);
      }
    };
    this.__insertAdapterOfNumeracyEntry = new EntityInsertAdapter<NumeracyEntry>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `numeracy` (`number`,`hindiWord`,`santhaliWord`,`santhaliOlChiki`,`audioFilename`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final NumeracyEntry entity) {
        statement.bindLong(1, entity.getNumber());
        if (entity.getHindiWord() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getHindiWord());
        }
        if (entity.getSanthaliWord() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getSanthaliWord());
        }
        if (entity.getSanthaliOlChiki() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getSanthaliOlChiki());
        }
        if (entity.getAudioFilename() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getAudioFilename());
        }
      }
    };
    this.__insertAdapterOfVocabularyEntry = new EntityInsertAdapter<VocabularyEntry>() {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `vocabulary` (`id`,`hindiWord`,`santhaliWord`,`santhaliOlChiki`,`category`,`audioFilename`,`gradeLevel`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SQLiteStatement statement,
          @NonNull final VocabularyEntry entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getHindiWord() == null) {
          statement.bindNull(2);
        } else {
          statement.bindText(2, entity.getHindiWord());
        }
        if (entity.getSanthaliWord() == null) {
          statement.bindNull(3);
        } else {
          statement.bindText(3, entity.getSanthaliWord());
        }
        if (entity.getSanthaliOlChiki() == null) {
          statement.bindNull(4);
        } else {
          statement.bindText(4, entity.getSanthaliOlChiki());
        }
        if (entity.getCategory() == null) {
          statement.bindNull(5);
        } else {
          statement.bindText(5, entity.getCategory());
        }
        if (entity.getAudioFilename() == null) {
          statement.bindNull(6);
        } else {
          statement.bindText(6, entity.getAudioFilename());
        }
        statement.bindLong(7, entity.getGradeLevel());
      }
    };
  }

  @Override
  public Object insertPhrase(final FLNPhrase phrase, final Continuation<? super Unit> $completion) {
    if (phrase == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfFLNPhrase.insert(_connection, phrase);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object insertPhrases(final List<FLNPhrase> phrases,
      final Continuation<? super Unit> $completion) {
    if (phrases == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfFLNPhrase.insert(_connection, phrases);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object insertNumber(final NumeracyEntry entry,
      final Continuation<? super Unit> $completion) {
    if (entry == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfNumeracyEntry.insert(_connection, entry);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object insertVocab(final VocabularyEntry entry,
      final Continuation<? super Unit> $completion) {
    if (entry == null) throw new NullPointerException();
    return DBUtil.performSuspending(__db, false, true, (_connection) -> {
      __insertAdapterOfVocabularyEntry.insert(_connection, entry);
      return Unit.INSTANCE;
    }, $completion);
  }

  @Override
  public Object searchPhrases(final String query,
      final Continuation<? super List<FLNPhrase>> $completion) {
    final String _sql = "\n"
            + "        SELECT fln_phrases.* FROM fln_phrases \n"
            + "        INNER JOIN fln_phrases_fts ON fln_phrases.rowid = fln_phrases_fts.rowid\n"
            + "        WHERE fln_phrases_fts MATCH ?\n"
            + "        ORDER BY fln_phrases.gradeLevel ASC\n"
            + "        LIMIT 5\n"
            + "    ";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (query == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, query);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfPhraseCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phraseCode");
        final int _columnIndexOfDomain = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "domain");
        final int _columnIndexOfHindiText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiText");
        final int _columnIndexOfSanthaliOlChiki = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliOlChiki");
        final int _columnIndexOfSanthaliRoman = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliRoman");
        final int _columnIndexOfAudioFilename = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "audioFilename");
        final int _columnIndexOfGradeLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gradeLevel");
        final int _columnIndexOfNipunCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nipunCode");
        final int _columnIndexOfContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "context");
        final int _columnIndexOfValidated = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "validated");
        final List<FLNPhrase> _result = new ArrayList<FLNPhrase>();
        while (_stmt.step()) {
          final FLNPhrase _item;
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpPhraseCode;
          if (_stmt.isNull(_columnIndexOfPhraseCode)) {
            _tmpPhraseCode = null;
          } else {
            _tmpPhraseCode = _stmt.getText(_columnIndexOfPhraseCode);
          }
          final String _tmpDomain;
          if (_stmt.isNull(_columnIndexOfDomain)) {
            _tmpDomain = null;
          } else {
            _tmpDomain = _stmt.getText(_columnIndexOfDomain);
          }
          final String _tmpHindiText;
          if (_stmt.isNull(_columnIndexOfHindiText)) {
            _tmpHindiText = null;
          } else {
            _tmpHindiText = _stmt.getText(_columnIndexOfHindiText);
          }
          final String _tmpSanthaliOlChiki;
          if (_stmt.isNull(_columnIndexOfSanthaliOlChiki)) {
            _tmpSanthaliOlChiki = null;
          } else {
            _tmpSanthaliOlChiki = _stmt.getText(_columnIndexOfSanthaliOlChiki);
          }
          final String _tmpSanthaliRoman;
          if (_stmt.isNull(_columnIndexOfSanthaliRoman)) {
            _tmpSanthaliRoman = null;
          } else {
            _tmpSanthaliRoman = _stmt.getText(_columnIndexOfSanthaliRoman);
          }
          final String _tmpAudioFilename;
          if (_stmt.isNull(_columnIndexOfAudioFilename)) {
            _tmpAudioFilename = null;
          } else {
            _tmpAudioFilename = _stmt.getText(_columnIndexOfAudioFilename);
          }
          final int _tmpGradeLevel;
          _tmpGradeLevel = (int) (_stmt.getLong(_columnIndexOfGradeLevel));
          final String _tmpNipunCode;
          if (_stmt.isNull(_columnIndexOfNipunCode)) {
            _tmpNipunCode = null;
          } else {
            _tmpNipunCode = _stmt.getText(_columnIndexOfNipunCode);
          }
          final String _tmpContext;
          if (_stmt.isNull(_columnIndexOfContext)) {
            _tmpContext = null;
          } else {
            _tmpContext = _stmt.getText(_columnIndexOfContext);
          }
          final boolean _tmpValidated;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfValidated));
          _tmpValidated = _tmp != 0;
          _item = new FLNPhrase(_tmpId,_tmpPhraseCode,_tmpDomain,_tmpHindiText,_tmpSanthaliOlChiki,_tmpSanthaliRoman,_tmpAudioFilename,_tmpGradeLevel,_tmpNipunCode,_tmpContext,_tmpValidated);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object exactMatch(final String exact, final Continuation<? super FLNPhrase> $completion) {
    final String _sql = "SELECT * FROM fln_phrases WHERE hindiText = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (exact == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, exact);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfPhraseCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phraseCode");
        final int _columnIndexOfDomain = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "domain");
        final int _columnIndexOfHindiText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiText");
        final int _columnIndexOfSanthaliOlChiki = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliOlChiki");
        final int _columnIndexOfSanthaliRoman = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliRoman");
        final int _columnIndexOfAudioFilename = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "audioFilename");
        final int _columnIndexOfGradeLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gradeLevel");
        final int _columnIndexOfNipunCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nipunCode");
        final int _columnIndexOfContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "context");
        final int _columnIndexOfValidated = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "validated");
        final FLNPhrase _result;
        if (_stmt.step()) {
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpPhraseCode;
          if (_stmt.isNull(_columnIndexOfPhraseCode)) {
            _tmpPhraseCode = null;
          } else {
            _tmpPhraseCode = _stmt.getText(_columnIndexOfPhraseCode);
          }
          final String _tmpDomain;
          if (_stmt.isNull(_columnIndexOfDomain)) {
            _tmpDomain = null;
          } else {
            _tmpDomain = _stmt.getText(_columnIndexOfDomain);
          }
          final String _tmpHindiText;
          if (_stmt.isNull(_columnIndexOfHindiText)) {
            _tmpHindiText = null;
          } else {
            _tmpHindiText = _stmt.getText(_columnIndexOfHindiText);
          }
          final String _tmpSanthaliOlChiki;
          if (_stmt.isNull(_columnIndexOfSanthaliOlChiki)) {
            _tmpSanthaliOlChiki = null;
          } else {
            _tmpSanthaliOlChiki = _stmt.getText(_columnIndexOfSanthaliOlChiki);
          }
          final String _tmpSanthaliRoman;
          if (_stmt.isNull(_columnIndexOfSanthaliRoman)) {
            _tmpSanthaliRoman = null;
          } else {
            _tmpSanthaliRoman = _stmt.getText(_columnIndexOfSanthaliRoman);
          }
          final String _tmpAudioFilename;
          if (_stmt.isNull(_columnIndexOfAudioFilename)) {
            _tmpAudioFilename = null;
          } else {
            _tmpAudioFilename = _stmt.getText(_columnIndexOfAudioFilename);
          }
          final int _tmpGradeLevel;
          _tmpGradeLevel = (int) (_stmt.getLong(_columnIndexOfGradeLevel));
          final String _tmpNipunCode;
          if (_stmt.isNull(_columnIndexOfNipunCode)) {
            _tmpNipunCode = null;
          } else {
            _tmpNipunCode = _stmt.getText(_columnIndexOfNipunCode);
          }
          final String _tmpContext;
          if (_stmt.isNull(_columnIndexOfContext)) {
            _tmpContext = null;
          } else {
            _tmpContext = _stmt.getText(_columnIndexOfContext);
          }
          final boolean _tmpValidated;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfValidated));
          _tmpValidated = _tmp != 0;
          _result = new FLNPhrase(_tmpId,_tmpPhraseCode,_tmpDomain,_tmpHindiText,_tmpSanthaliOlChiki,_tmpSanthaliRoman,_tmpAudioFilename,_tmpGradeLevel,_tmpNipunCode,_tmpContext,_tmpValidated);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getPhrasesByDomain(final String domain,
      final Continuation<? super List<FLNPhrase>> $completion) {
    final String _sql = "SELECT * FROM fln_phrases WHERE domain = ? ORDER BY phraseCode";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (domain == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, domain);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfPhraseCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phraseCode");
        final int _columnIndexOfDomain = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "domain");
        final int _columnIndexOfHindiText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiText");
        final int _columnIndexOfSanthaliOlChiki = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliOlChiki");
        final int _columnIndexOfSanthaliRoman = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliRoman");
        final int _columnIndexOfAudioFilename = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "audioFilename");
        final int _columnIndexOfGradeLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gradeLevel");
        final int _columnIndexOfNipunCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nipunCode");
        final int _columnIndexOfContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "context");
        final int _columnIndexOfValidated = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "validated");
        final List<FLNPhrase> _result = new ArrayList<FLNPhrase>();
        while (_stmt.step()) {
          final FLNPhrase _item;
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpPhraseCode;
          if (_stmt.isNull(_columnIndexOfPhraseCode)) {
            _tmpPhraseCode = null;
          } else {
            _tmpPhraseCode = _stmt.getText(_columnIndexOfPhraseCode);
          }
          final String _tmpDomain;
          if (_stmt.isNull(_columnIndexOfDomain)) {
            _tmpDomain = null;
          } else {
            _tmpDomain = _stmt.getText(_columnIndexOfDomain);
          }
          final String _tmpHindiText;
          if (_stmt.isNull(_columnIndexOfHindiText)) {
            _tmpHindiText = null;
          } else {
            _tmpHindiText = _stmt.getText(_columnIndexOfHindiText);
          }
          final String _tmpSanthaliOlChiki;
          if (_stmt.isNull(_columnIndexOfSanthaliOlChiki)) {
            _tmpSanthaliOlChiki = null;
          } else {
            _tmpSanthaliOlChiki = _stmt.getText(_columnIndexOfSanthaliOlChiki);
          }
          final String _tmpSanthaliRoman;
          if (_stmt.isNull(_columnIndexOfSanthaliRoman)) {
            _tmpSanthaliRoman = null;
          } else {
            _tmpSanthaliRoman = _stmt.getText(_columnIndexOfSanthaliRoman);
          }
          final String _tmpAudioFilename;
          if (_stmt.isNull(_columnIndexOfAudioFilename)) {
            _tmpAudioFilename = null;
          } else {
            _tmpAudioFilename = _stmt.getText(_columnIndexOfAudioFilename);
          }
          final int _tmpGradeLevel;
          _tmpGradeLevel = (int) (_stmt.getLong(_columnIndexOfGradeLevel));
          final String _tmpNipunCode;
          if (_stmt.isNull(_columnIndexOfNipunCode)) {
            _tmpNipunCode = null;
          } else {
            _tmpNipunCode = _stmt.getText(_columnIndexOfNipunCode);
          }
          final String _tmpContext;
          if (_stmt.isNull(_columnIndexOfContext)) {
            _tmpContext = null;
          } else {
            _tmpContext = _stmt.getText(_columnIndexOfContext);
          }
          final boolean _tmpValidated;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfValidated));
          _tmpValidated = _tmp != 0;
          _item = new FLNPhrase(_tmpId,_tmpPhraseCode,_tmpDomain,_tmpHindiText,_tmpSanthaliOlChiki,_tmpSanthaliRoman,_tmpAudioFilename,_tmpGradeLevel,_tmpNipunCode,_tmpContext,_tmpValidated);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getClassroomPhrases(final Continuation<? super List<FLNPhrase>> $completion) {
    final String _sql = "SELECT * FROM fln_phrases WHERE context = 'classroom_mgmt'";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfPhraseCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phraseCode");
        final int _columnIndexOfDomain = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "domain");
        final int _columnIndexOfHindiText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiText");
        final int _columnIndexOfSanthaliOlChiki = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliOlChiki");
        final int _columnIndexOfSanthaliRoman = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliRoman");
        final int _columnIndexOfAudioFilename = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "audioFilename");
        final int _columnIndexOfGradeLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gradeLevel");
        final int _columnIndexOfNipunCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nipunCode");
        final int _columnIndexOfContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "context");
        final int _columnIndexOfValidated = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "validated");
        final List<FLNPhrase> _result = new ArrayList<FLNPhrase>();
        while (_stmt.step()) {
          final FLNPhrase _item;
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpPhraseCode;
          if (_stmt.isNull(_columnIndexOfPhraseCode)) {
            _tmpPhraseCode = null;
          } else {
            _tmpPhraseCode = _stmt.getText(_columnIndexOfPhraseCode);
          }
          final String _tmpDomain;
          if (_stmt.isNull(_columnIndexOfDomain)) {
            _tmpDomain = null;
          } else {
            _tmpDomain = _stmt.getText(_columnIndexOfDomain);
          }
          final String _tmpHindiText;
          if (_stmt.isNull(_columnIndexOfHindiText)) {
            _tmpHindiText = null;
          } else {
            _tmpHindiText = _stmt.getText(_columnIndexOfHindiText);
          }
          final String _tmpSanthaliOlChiki;
          if (_stmt.isNull(_columnIndexOfSanthaliOlChiki)) {
            _tmpSanthaliOlChiki = null;
          } else {
            _tmpSanthaliOlChiki = _stmt.getText(_columnIndexOfSanthaliOlChiki);
          }
          final String _tmpSanthaliRoman;
          if (_stmt.isNull(_columnIndexOfSanthaliRoman)) {
            _tmpSanthaliRoman = null;
          } else {
            _tmpSanthaliRoman = _stmt.getText(_columnIndexOfSanthaliRoman);
          }
          final String _tmpAudioFilename;
          if (_stmt.isNull(_columnIndexOfAudioFilename)) {
            _tmpAudioFilename = null;
          } else {
            _tmpAudioFilename = _stmt.getText(_columnIndexOfAudioFilename);
          }
          final int _tmpGradeLevel;
          _tmpGradeLevel = (int) (_stmt.getLong(_columnIndexOfGradeLevel));
          final String _tmpNipunCode;
          if (_stmt.isNull(_columnIndexOfNipunCode)) {
            _tmpNipunCode = null;
          } else {
            _tmpNipunCode = _stmt.getText(_columnIndexOfNipunCode);
          }
          final String _tmpContext;
          if (_stmt.isNull(_columnIndexOfContext)) {
            _tmpContext = null;
          } else {
            _tmpContext = _stmt.getText(_columnIndexOfContext);
          }
          final boolean _tmpValidated;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfValidated));
          _tmpValidated = _tmp != 0;
          _item = new FLNPhrase(_tmpId,_tmpPhraseCode,_tmpDomain,_tmpHindiText,_tmpSanthaliOlChiki,_tmpSanthaliRoman,_tmpAudioFilename,_tmpGradeLevel,_tmpNipunCode,_tmpContext,_tmpValidated);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getAllPhrases(final Continuation<? super List<FLNPhrase>> $completion) {
    final String _sql = "SELECT * FROM fln_phrases";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfPhraseCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phraseCode");
        final int _columnIndexOfDomain = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "domain");
        final int _columnIndexOfHindiText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiText");
        final int _columnIndexOfSanthaliOlChiki = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliOlChiki");
        final int _columnIndexOfSanthaliRoman = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliRoman");
        final int _columnIndexOfAudioFilename = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "audioFilename");
        final int _columnIndexOfGradeLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gradeLevel");
        final int _columnIndexOfNipunCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nipunCode");
        final int _columnIndexOfContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "context");
        final int _columnIndexOfValidated = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "validated");
        final List<FLNPhrase> _result = new ArrayList<FLNPhrase>();
        while (_stmt.step()) {
          final FLNPhrase _item;
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpPhraseCode;
          if (_stmt.isNull(_columnIndexOfPhraseCode)) {
            _tmpPhraseCode = null;
          } else {
            _tmpPhraseCode = _stmt.getText(_columnIndexOfPhraseCode);
          }
          final String _tmpDomain;
          if (_stmt.isNull(_columnIndexOfDomain)) {
            _tmpDomain = null;
          } else {
            _tmpDomain = _stmt.getText(_columnIndexOfDomain);
          }
          final String _tmpHindiText;
          if (_stmt.isNull(_columnIndexOfHindiText)) {
            _tmpHindiText = null;
          } else {
            _tmpHindiText = _stmt.getText(_columnIndexOfHindiText);
          }
          final String _tmpSanthaliOlChiki;
          if (_stmt.isNull(_columnIndexOfSanthaliOlChiki)) {
            _tmpSanthaliOlChiki = null;
          } else {
            _tmpSanthaliOlChiki = _stmt.getText(_columnIndexOfSanthaliOlChiki);
          }
          final String _tmpSanthaliRoman;
          if (_stmt.isNull(_columnIndexOfSanthaliRoman)) {
            _tmpSanthaliRoman = null;
          } else {
            _tmpSanthaliRoman = _stmt.getText(_columnIndexOfSanthaliRoman);
          }
          final String _tmpAudioFilename;
          if (_stmt.isNull(_columnIndexOfAudioFilename)) {
            _tmpAudioFilename = null;
          } else {
            _tmpAudioFilename = _stmt.getText(_columnIndexOfAudioFilename);
          }
          final int _tmpGradeLevel;
          _tmpGradeLevel = (int) (_stmt.getLong(_columnIndexOfGradeLevel));
          final String _tmpNipunCode;
          if (_stmt.isNull(_columnIndexOfNipunCode)) {
            _tmpNipunCode = null;
          } else {
            _tmpNipunCode = _stmt.getText(_columnIndexOfNipunCode);
          }
          final String _tmpContext;
          if (_stmt.isNull(_columnIndexOfContext)) {
            _tmpContext = null;
          } else {
            _tmpContext = _stmt.getText(_columnIndexOfContext);
          }
          final boolean _tmpValidated;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfValidated));
          _tmpValidated = _tmp != 0;
          _item = new FLNPhrase(_tmpId,_tmpPhraseCode,_tmpDomain,_tmpHindiText,_tmpSanthaliOlChiki,_tmpSanthaliRoman,_tmpAudioFilename,_tmpGradeLevel,_tmpNipunCode,_tmpContext,_tmpValidated);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object reverseLookup(final String santhali,
      final Continuation<? super FLNPhrase> $completion) {
    final String _sql = "SELECT * FROM fln_phrases WHERE santhaliOlChiki = ? LIMIT 1";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (santhali == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, santhali);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfPhraseCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "phraseCode");
        final int _columnIndexOfDomain = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "domain");
        final int _columnIndexOfHindiText = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiText");
        final int _columnIndexOfSanthaliOlChiki = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliOlChiki");
        final int _columnIndexOfSanthaliRoman = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliRoman");
        final int _columnIndexOfAudioFilename = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "audioFilename");
        final int _columnIndexOfGradeLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gradeLevel");
        final int _columnIndexOfNipunCode = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "nipunCode");
        final int _columnIndexOfContext = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "context");
        final int _columnIndexOfValidated = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "validated");
        final FLNPhrase _result;
        if (_stmt.step()) {
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpPhraseCode;
          if (_stmt.isNull(_columnIndexOfPhraseCode)) {
            _tmpPhraseCode = null;
          } else {
            _tmpPhraseCode = _stmt.getText(_columnIndexOfPhraseCode);
          }
          final String _tmpDomain;
          if (_stmt.isNull(_columnIndexOfDomain)) {
            _tmpDomain = null;
          } else {
            _tmpDomain = _stmt.getText(_columnIndexOfDomain);
          }
          final String _tmpHindiText;
          if (_stmt.isNull(_columnIndexOfHindiText)) {
            _tmpHindiText = null;
          } else {
            _tmpHindiText = _stmt.getText(_columnIndexOfHindiText);
          }
          final String _tmpSanthaliOlChiki;
          if (_stmt.isNull(_columnIndexOfSanthaliOlChiki)) {
            _tmpSanthaliOlChiki = null;
          } else {
            _tmpSanthaliOlChiki = _stmt.getText(_columnIndexOfSanthaliOlChiki);
          }
          final String _tmpSanthaliRoman;
          if (_stmt.isNull(_columnIndexOfSanthaliRoman)) {
            _tmpSanthaliRoman = null;
          } else {
            _tmpSanthaliRoman = _stmt.getText(_columnIndexOfSanthaliRoman);
          }
          final String _tmpAudioFilename;
          if (_stmt.isNull(_columnIndexOfAudioFilename)) {
            _tmpAudioFilename = null;
          } else {
            _tmpAudioFilename = _stmt.getText(_columnIndexOfAudioFilename);
          }
          final int _tmpGradeLevel;
          _tmpGradeLevel = (int) (_stmt.getLong(_columnIndexOfGradeLevel));
          final String _tmpNipunCode;
          if (_stmt.isNull(_columnIndexOfNipunCode)) {
            _tmpNipunCode = null;
          } else {
            _tmpNipunCode = _stmt.getText(_columnIndexOfNipunCode);
          }
          final String _tmpContext;
          if (_stmt.isNull(_columnIndexOfContext)) {
            _tmpContext = null;
          } else {
            _tmpContext = _stmt.getText(_columnIndexOfContext);
          }
          final boolean _tmpValidated;
          final int _tmp;
          _tmp = (int) (_stmt.getLong(_columnIndexOfValidated));
          _tmpValidated = _tmp != 0;
          _result = new FLNPhrase(_tmpId,_tmpPhraseCode,_tmpDomain,_tmpHindiText,_tmpSanthaliOlChiki,_tmpSanthaliRoman,_tmpAudioFilename,_tmpGradeLevel,_tmpNipunCode,_tmpContext,_tmpValidated);
        } else {
          _result = null;
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getAllNumbers(final Continuation<? super List<NumeracyEntry>> $completion) {
    final String _sql = "SELECT * FROM numeracy ORDER BY number";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        final int _columnIndexOfNumber = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "number");
        final int _columnIndexOfHindiWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiWord");
        final int _columnIndexOfSanthaliWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliWord");
        final int _columnIndexOfSanthaliOlChiki = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliOlChiki");
        final int _columnIndexOfAudioFilename = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "audioFilename");
        final List<NumeracyEntry> _result = new ArrayList<NumeracyEntry>();
        while (_stmt.step()) {
          final NumeracyEntry _item;
          final int _tmpNumber;
          _tmpNumber = (int) (_stmt.getLong(_columnIndexOfNumber));
          final String _tmpHindiWord;
          if (_stmt.isNull(_columnIndexOfHindiWord)) {
            _tmpHindiWord = null;
          } else {
            _tmpHindiWord = _stmt.getText(_columnIndexOfHindiWord);
          }
          final String _tmpSanthaliWord;
          if (_stmt.isNull(_columnIndexOfSanthaliWord)) {
            _tmpSanthaliWord = null;
          } else {
            _tmpSanthaliWord = _stmt.getText(_columnIndexOfSanthaliWord);
          }
          final String _tmpSanthaliOlChiki;
          if (_stmt.isNull(_columnIndexOfSanthaliOlChiki)) {
            _tmpSanthaliOlChiki = null;
          } else {
            _tmpSanthaliOlChiki = _stmt.getText(_columnIndexOfSanthaliOlChiki);
          }
          final String _tmpAudioFilename;
          if (_stmt.isNull(_columnIndexOfAudioFilename)) {
            _tmpAudioFilename = null;
          } else {
            _tmpAudioFilename = _stmt.getText(_columnIndexOfAudioFilename);
          }
          _item = new NumeracyEntry(_tmpNumber,_tmpHindiWord,_tmpSanthaliWord,_tmpSanthaliOlChiki,_tmpAudioFilename);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @Override
  public Object getVocabByCategory(final String category,
      final Continuation<? super List<VocabularyEntry>> $completion) {
    final String _sql = "SELECT * FROM vocabulary WHERE category = ?";
    return DBUtil.performSuspending(__db, true, false, (_connection) -> {
      final SQLiteStatement _stmt = _connection.prepare(_sql);
      try {
        int _argIndex = 1;
        if (category == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindText(_argIndex, category);
        }
        final int _columnIndexOfId = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "id");
        final int _columnIndexOfHindiWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "hindiWord");
        final int _columnIndexOfSanthaliWord = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliWord");
        final int _columnIndexOfSanthaliOlChiki = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "santhaliOlChiki");
        final int _columnIndexOfCategory = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "category");
        final int _columnIndexOfAudioFilename = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "audioFilename");
        final int _columnIndexOfGradeLevel = SQLiteStatementUtil.getColumnIndexOrThrow(_stmt, "gradeLevel");
        final List<VocabularyEntry> _result = new ArrayList<VocabularyEntry>();
        while (_stmt.step()) {
          final VocabularyEntry _item;
          final int _tmpId;
          _tmpId = (int) (_stmt.getLong(_columnIndexOfId));
          final String _tmpHindiWord;
          if (_stmt.isNull(_columnIndexOfHindiWord)) {
            _tmpHindiWord = null;
          } else {
            _tmpHindiWord = _stmt.getText(_columnIndexOfHindiWord);
          }
          final String _tmpSanthaliWord;
          if (_stmt.isNull(_columnIndexOfSanthaliWord)) {
            _tmpSanthaliWord = null;
          } else {
            _tmpSanthaliWord = _stmt.getText(_columnIndexOfSanthaliWord);
          }
          final String _tmpSanthaliOlChiki;
          if (_stmt.isNull(_columnIndexOfSanthaliOlChiki)) {
            _tmpSanthaliOlChiki = null;
          } else {
            _tmpSanthaliOlChiki = _stmt.getText(_columnIndexOfSanthaliOlChiki);
          }
          final String _tmpCategory;
          if (_stmt.isNull(_columnIndexOfCategory)) {
            _tmpCategory = null;
          } else {
            _tmpCategory = _stmt.getText(_columnIndexOfCategory);
          }
          final String _tmpAudioFilename;
          if (_stmt.isNull(_columnIndexOfAudioFilename)) {
            _tmpAudioFilename = null;
          } else {
            _tmpAudioFilename = _stmt.getText(_columnIndexOfAudioFilename);
          }
          final int _tmpGradeLevel;
          _tmpGradeLevel = (int) (_stmt.getLong(_columnIndexOfGradeLevel));
          _item = new VocabularyEntry(_tmpId,_tmpHindiWord,_tmpSanthaliWord,_tmpSanthaliOlChiki,_tmpCategory,_tmpAudioFilename,_tmpGradeLevel);
          _result.add(_item);
        }
        return _result;
      } finally {
        _stmt.close();
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
