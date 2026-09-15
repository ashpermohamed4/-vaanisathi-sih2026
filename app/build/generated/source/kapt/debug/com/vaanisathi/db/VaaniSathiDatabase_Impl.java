package com.vaanisathi.db;

import androidx.annotation.NonNull;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.FtsTableInfo;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation", "removal"})
public final class VaaniSathiDatabase_Impl extends VaaniSathiDatabase {
  private volatile PhraseDao _phraseDao;

  @Override
  @NonNull
  protected RoomOpenDelegate createOpenDelegate() {
    final RoomOpenDelegate _openDelegate = new RoomOpenDelegate(2, "de39464cb63e193956f170fc0159ddc2", "76d30e216c222115d4b07767e91e5d96") {
      @Override
      public void createAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `fln_phrases` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `phraseCode` TEXT NOT NULL, `domain` TEXT NOT NULL, `hindiText` TEXT NOT NULL, `santhaliOlChiki` TEXT NOT NULL, `santhaliRoman` TEXT NOT NULL, `audioFilename` TEXT NOT NULL, `gradeLevel` INTEGER NOT NULL, `nipunCode` TEXT NOT NULL, `context` TEXT NOT NULL, `validated` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE VIRTUAL TABLE IF NOT EXISTS `fln_phrases_fts` USING FTS4(`hindiText` TEXT NOT NULL, content=`fln_phrases`)");
        SQLite.execSQL(connection, "CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_fln_phrases_fts_BEFORE_UPDATE BEFORE UPDATE ON `fln_phrases` BEGIN DELETE FROM `fln_phrases_fts` WHERE `docid`=OLD.`rowid`; END");
        SQLite.execSQL(connection, "CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_fln_phrases_fts_BEFORE_DELETE BEFORE DELETE ON `fln_phrases` BEGIN DELETE FROM `fln_phrases_fts` WHERE `docid`=OLD.`rowid`; END");
        SQLite.execSQL(connection, "CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_fln_phrases_fts_AFTER_UPDATE AFTER UPDATE ON `fln_phrases` BEGIN INSERT INTO `fln_phrases_fts`(`docid`, `hindiText`) VALUES (NEW.`rowid`, NEW.`hindiText`); END");
        SQLite.execSQL(connection, "CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_fln_phrases_fts_AFTER_INSERT AFTER INSERT ON `fln_phrases` BEGIN INSERT INTO `fln_phrases_fts`(`docid`, `hindiText`) VALUES (NEW.`rowid`, NEW.`hindiText`); END");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `numeracy` (`number` INTEGER NOT NULL, `hindiWord` TEXT NOT NULL, `santhaliWord` TEXT NOT NULL, `santhaliOlChiki` TEXT NOT NULL, `audioFilename` TEXT NOT NULL, PRIMARY KEY(`number`))");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `vocabulary` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `hindiWord` TEXT NOT NULL, `santhaliWord` TEXT NOT NULL, `santhaliOlChiki` TEXT NOT NULL, `category` TEXT NOT NULL, `audioFilename` TEXT NOT NULL, `gradeLevel` INTEGER NOT NULL)");
        SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'de39464cb63e193956f170fc0159ddc2')");
      }

      @Override
      public void dropAllTables(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `fln_phrases`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `fln_phrases_fts`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `numeracy`");
        SQLite.execSQL(connection, "DROP TABLE IF EXISTS `vocabulary`");
      }

      @Override
      public void onCreate(@NonNull final SQLiteConnection connection) {
      }

      @Override
      public void onOpen(@NonNull final SQLiteConnection connection) {
        internalInitInvalidationTracker(connection);
      }

      @Override
      public void onPreMigrate(@NonNull final SQLiteConnection connection) {
        DBUtil.dropFtsSyncTriggers(connection);
      }

      @Override
      public void onPostMigrate(@NonNull final SQLiteConnection connection) {
        SQLite.execSQL(connection, "CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_fln_phrases_fts_BEFORE_UPDATE BEFORE UPDATE ON `fln_phrases` BEGIN DELETE FROM `fln_phrases_fts` WHERE `docid`=OLD.`rowid`; END");
        SQLite.execSQL(connection, "CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_fln_phrases_fts_BEFORE_DELETE BEFORE DELETE ON `fln_phrases` BEGIN DELETE FROM `fln_phrases_fts` WHERE `docid`=OLD.`rowid`; END");
        SQLite.execSQL(connection, "CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_fln_phrases_fts_AFTER_UPDATE AFTER UPDATE ON `fln_phrases` BEGIN INSERT INTO `fln_phrases_fts`(`docid`, `hindiText`) VALUES (NEW.`rowid`, NEW.`hindiText`); END");
        SQLite.execSQL(connection, "CREATE TRIGGER IF NOT EXISTS room_fts_content_sync_fln_phrases_fts_AFTER_INSERT AFTER INSERT ON `fln_phrases` BEGIN INSERT INTO `fln_phrases_fts`(`docid`, `hindiText`) VALUES (NEW.`rowid`, NEW.`hindiText`); END");
      }

      @Override
      @NonNull
      public RoomOpenDelegate.ValidationResult onValidateSchema(
          @NonNull final SQLiteConnection connection) {
        final Map<String, TableInfo.Column> _columnsFlnPhrases = new HashMap<String, TableInfo.Column>(11);
        _columnsFlnPhrases.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlnPhrases.put("phraseCode", new TableInfo.Column("phraseCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlnPhrases.put("domain", new TableInfo.Column("domain", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlnPhrases.put("hindiText", new TableInfo.Column("hindiText", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlnPhrases.put("santhaliOlChiki", new TableInfo.Column("santhaliOlChiki", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlnPhrases.put("santhaliRoman", new TableInfo.Column("santhaliRoman", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlnPhrases.put("audioFilename", new TableInfo.Column("audioFilename", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlnPhrases.put("gradeLevel", new TableInfo.Column("gradeLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlnPhrases.put("nipunCode", new TableInfo.Column("nipunCode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlnPhrases.put("context", new TableInfo.Column("context", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFlnPhrases.put("validated", new TableInfo.Column("validated", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysFlnPhrases = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesFlnPhrases = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFlnPhrases = new TableInfo("fln_phrases", _columnsFlnPhrases, _foreignKeysFlnPhrases, _indicesFlnPhrases);
        final TableInfo _existingFlnPhrases = TableInfo.read(connection, "fln_phrases");
        if (!_infoFlnPhrases.equals(_existingFlnPhrases)) {
          return new RoomOpenDelegate.ValidationResult(false, "fln_phrases(com.vaanisathi.db.FLNPhrase).\n"
                  + " Expected:\n" + _infoFlnPhrases + "\n"
                  + " Found:\n" + _existingFlnPhrases);
        }
        final Set<String> _columnsFlnPhrasesFts = new HashSet<String>(1);
        _columnsFlnPhrasesFts.add("hindiText");
        final FtsTableInfo _infoFlnPhrasesFts = new FtsTableInfo("fln_phrases_fts", _columnsFlnPhrasesFts, "CREATE VIRTUAL TABLE IF NOT EXISTS `fln_phrases_fts` USING FTS4(`hindiText` TEXT NOT NULL, content=`fln_phrases`)");
        final FtsTableInfo _existingFlnPhrasesFts = FtsTableInfo.read(connection, "fln_phrases_fts");
        if (!_infoFlnPhrasesFts.equals(_existingFlnPhrasesFts)) {
          return new RoomOpenDelegate.ValidationResult(false, "fln_phrases_fts(com.vaanisathi.db.FLNPhraseFts).\n"
                  + " Expected:\n" + _infoFlnPhrasesFts + "\n"
                  + " Found:\n" + _existingFlnPhrasesFts);
        }
        final Map<String, TableInfo.Column> _columnsNumeracy = new HashMap<String, TableInfo.Column>(5);
        _columnsNumeracy.put("number", new TableInfo.Column("number", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNumeracy.put("hindiWord", new TableInfo.Column("hindiWord", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNumeracy.put("santhaliWord", new TableInfo.Column("santhaliWord", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNumeracy.put("santhaliOlChiki", new TableInfo.Column("santhaliOlChiki", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNumeracy.put("audioFilename", new TableInfo.Column("audioFilename", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysNumeracy = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesNumeracy = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNumeracy = new TableInfo("numeracy", _columnsNumeracy, _foreignKeysNumeracy, _indicesNumeracy);
        final TableInfo _existingNumeracy = TableInfo.read(connection, "numeracy");
        if (!_infoNumeracy.equals(_existingNumeracy)) {
          return new RoomOpenDelegate.ValidationResult(false, "numeracy(com.vaanisathi.db.NumeracyEntry).\n"
                  + " Expected:\n" + _infoNumeracy + "\n"
                  + " Found:\n" + _existingNumeracy);
        }
        final Map<String, TableInfo.Column> _columnsVocabulary = new HashMap<String, TableInfo.Column>(7);
        _columnsVocabulary.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("hindiWord", new TableInfo.Column("hindiWord", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("santhaliWord", new TableInfo.Column("santhaliWord", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("santhaliOlChiki", new TableInfo.Column("santhaliOlChiki", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("audioFilename", new TableInfo.Column("audioFilename", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsVocabulary.put("gradeLevel", new TableInfo.Column("gradeLevel", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final Set<TableInfo.ForeignKey> _foreignKeysVocabulary = new HashSet<TableInfo.ForeignKey>(0);
        final Set<TableInfo.Index> _indicesVocabulary = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoVocabulary = new TableInfo("vocabulary", _columnsVocabulary, _foreignKeysVocabulary, _indicesVocabulary);
        final TableInfo _existingVocabulary = TableInfo.read(connection, "vocabulary");
        if (!_infoVocabulary.equals(_existingVocabulary)) {
          return new RoomOpenDelegate.ValidationResult(false, "vocabulary(com.vaanisathi.db.VocabularyEntry).\n"
                  + " Expected:\n" + _infoVocabulary + "\n"
                  + " Found:\n" + _existingVocabulary);
        }
        return new RoomOpenDelegate.ValidationResult(true, null);
      }
    };
    return _openDelegate;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final Map<String, String> _shadowTablesMap = new HashMap<String, String>(1);
    _shadowTablesMap.put("fln_phrases_fts", "fln_phrases");
    final Map<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "fln_phrases", "fln_phrases_fts", "numeracy", "vocabulary");
  }

  @Override
  public void clearAllTables() {
    super.performClear(false, "fln_phrases", "fln_phrases_fts", "numeracy", "vocabulary");
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final Map<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PhraseDao.class, PhraseDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final Set<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public PhraseDao phraseDao() {
    if (_phraseDao != null) {
      return _phraseDao;
    } else {
      synchronized(this) {
        if(_phraseDao == null) {
          _phraseDao = new PhraseDao_Impl(this);
        }
        return _phraseDao;
      }
    }
  }
}
