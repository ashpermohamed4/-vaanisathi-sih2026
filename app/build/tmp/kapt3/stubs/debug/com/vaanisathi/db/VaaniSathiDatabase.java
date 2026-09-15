package com.vaanisathi.db;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b'\u0018\u0000 \u00062\u00020\u0001:\u0002\u0006\u0007B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H&\u00a8\u0006\b"}, d2 = {"Lcom/vaanisathi/db/VaaniSathiDatabase;", "Landroidx/room/RoomDatabase;", "<init>", "()V", "phraseDao", "Lcom/vaanisathi/db/PhraseDao;", "Companion", "SeedCallback", "app_debug"})
@androidx.room.Database(entities = {com.vaanisathi.db.FLNPhrase.class, com.vaanisathi.db.FLNPhraseFts.class, com.vaanisathi.db.NumeracyEntry.class, com.vaanisathi.db.VocabularyEntry.class}, version = 2, exportSchema = false)
public abstract class VaaniSathiDatabase extends androidx.room.RoomDatabase {
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private static volatile com.vaanisathi.db.VaaniSathiDatabase INSTANCE;
    @org.jetbrains.annotations.NotNull()
    public static final com.vaanisathi.db.VaaniSathiDatabase.Companion Companion = null;
    
    public VaaniSathiDatabase() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.vaanisathi.db.PhraseDao phraseDao();
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0007\u001a\u00020\bR\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/vaanisathi/db/VaaniSathiDatabase$Companion;", "", "<init>", "()V", "INSTANCE", "Lcom/vaanisathi/db/VaaniSathiDatabase;", "getInstance", "context", "Landroid/content/Context;", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.vaanisathi.db.VaaniSathiDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/vaanisathi/db/VaaniSathiDatabase$SeedCallback;", "Landroidx/room/RoomDatabase$Callback;", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "onCreate", "", "db", "Landroidx/sqlite/db/SupportSQLiteDatabase;", "app_debug"})
    static final class SeedCallback extends androidx.room.RoomDatabase.Callback {
        @org.jetbrains.annotations.NotNull()
        private final android.content.Context context = null;
        
        public SeedCallback(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            super();
        }
        
        @java.lang.Override()
        public void onCreate(@org.jetbrains.annotations.NotNull()
        androidx.sqlite.db.SupportSQLiteDatabase db) {
        }
    }
}