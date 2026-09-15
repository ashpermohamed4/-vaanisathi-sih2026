package com.vaanisathi.nmt;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001\u000bB\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u0086@\u00a2\u0006\u0002\u0010\nR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/vaanisathi/nmt/BhashiniTranslator;", "", "<init>", "()V", "API_URL", "", "API_KEY", "translateHindiToSanthali", "Lcom/vaanisathi/nmt/BhashiniTranslator$TranslationResult;", "hindiText", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "TranslationResult", "app_debug"})
public final class BhashiniTranslator {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String API_URL = "https://dhruva-api.bhashini.gov.in/services/inference/pipeline";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String API_KEY = "YOUR_BHASHINI_API_KEY";
    @org.jetbrains.annotations.NotNull()
    public static final com.vaanisathi.nmt.BhashiniTranslator INSTANCE = null;
    
    private BhashiniTranslator() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object translateHindiToSanthali(@org.jetbrains.annotations.NotNull()
    java.lang.String hindiText, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.vaanisathi.nmt.BhashiniTranslator.TranslationResult> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00052\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0014"}, d2 = {"Lcom/vaanisathi/nmt/BhashiniTranslator$TranslationResult;", "", "santhaliText", "", "success", "", "<init>", "(Ljava/lang/String;Z)V", "getSanthaliText", "()Ljava/lang/String;", "getSuccess", "()Z", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
    public static final class TranslationResult {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String santhaliText = null;
        private final boolean success = false;
        
        public TranslationResult(@org.jetbrains.annotations.NotNull()
        java.lang.String santhaliText, boolean success) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getSanthaliText() {
            return null;
        }
        
        public final boolean getSuccess() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        public final boolean component2() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.vaanisathi.nmt.BhashiniTranslator.TranslationResult copy(@org.jetbrains.annotations.NotNull()
        java.lang.String santhaliText, boolean success) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @java.lang.Override()
        @org.jetbrains.annotations.NotNull()
        public java.lang.String toString() {
            return null;
        }
    }
}