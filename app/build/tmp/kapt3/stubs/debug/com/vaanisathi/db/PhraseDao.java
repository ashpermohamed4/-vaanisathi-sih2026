package com.vaanisathi.db;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u001c\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0005\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0018\u0010\b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\t\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u000b\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0010\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0004H\u00a7@\u00a2\u0006\u0002\u0010\u0014J\u001c\u0010\u0015\u001a\u00020\u00122\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0017J\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u0003H\u00a7@\u00a2\u0006\u0002\u0010\rJ\u0016\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u0019H\u00a7@\u00a2\u0006\u0002\u0010\u001cJ\u001c\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001e0\u00032\u0006\u0010\u001f\u001a\u00020\u0006H\u00a7@\u00a2\u0006\u0002\u0010\u0007J\u0016\u0010 \u001a\u00020\u00122\u0006\u0010\u001b\u001a\u00020\u001eH\u00a7@\u00a2\u0006\u0002\u0010!\u00a8\u0006\"\u00c0\u0006\u0003"}, d2 = {"Lcom/vaanisathi/db/PhraseDao;", "", "searchPhrases", "", "Lcom/vaanisathi/db/FLNPhrase;", "query", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "exactMatch", "exact", "getPhrasesByDomain", "domain", "getClassroomPhrases", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPhrases", "reverseLookup", "santhali", "insertPhrase", "", "phrase", "(Lcom/vaanisathi/db/FLNPhrase;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertPhrases", "phrases", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllNumbers", "Lcom/vaanisathi/db/NumeracyEntry;", "insertNumber", "entry", "(Lcom/vaanisathi/db/NumeracyEntry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getVocabByCategory", "Lcom/vaanisathi/db/VocabularyEntry;", "category", "insertVocab", "(Lcom/vaanisathi/db/VocabularyEntry;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao()
public abstract interface PhraseDao {
    
    @androidx.room.Query(value = "\n        SELECT fln_phrases.* FROM fln_phrases \n        INNER JOIN fln_phrases_fts ON fln_phrases.rowid = fln_phrases_fts.rowid\n        WHERE fln_phrases_fts MATCH :query\n        ORDER BY fln_phrases.gradeLevel ASC\n        LIMIT 5\n    ")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object searchPhrases(@org.jetbrains.annotations.NotNull()
    java.lang.String query, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vaanisathi.db.FLNPhrase>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM fln_phrases WHERE hindiText = :exact LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object exactMatch(@org.jetbrains.annotations.NotNull()
    java.lang.String exact, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vaanisathi.db.FLNPhrase> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM fln_phrases WHERE domain = :domain ORDER BY phraseCode")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPhrasesByDomain(@org.jetbrains.annotations.NotNull()
    java.lang.String domain, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vaanisathi.db.FLNPhrase>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM fln_phrases WHERE context = 'classroom_mgmt'")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getClassroomPhrases(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vaanisathi.db.FLNPhrase>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM fln_phrases")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllPhrases(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vaanisathi.db.FLNPhrase>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM fln_phrases WHERE santhaliOlChiki = :santhali LIMIT 1")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object reverseLookup(@org.jetbrains.annotations.NotNull()
    java.lang.String santhali, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vaanisathi.db.FLNPhrase> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPhrase(@org.jetbrains.annotations.NotNull()
    com.vaanisathi.db.FLNPhrase phrase, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPhrases(@org.jetbrains.annotations.NotNull()
    java.util.List<com.vaanisathi.db.FLNPhrase> phrases, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM numeracy ORDER BY number")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllNumbers(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vaanisathi.db.NumeracyEntry>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertNumber(@org.jetbrains.annotations.NotNull()
    com.vaanisathi.db.NumeracyEntry entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM vocabulary WHERE category = :category")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getVocabByCategory(@org.jetbrains.annotations.NotNull()
    java.lang.String category, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.vaanisathi.db.VocabularyEntry>> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertVocab(@org.jetbrains.annotations.NotNull()
    com.vaanisathi.db.VocabularyEntry entry, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}