package com.vaanisathi.asr;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001%B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u000e\u0010\u0018\u001a\u00020\u0019H\u0086@\u00a2\u0006\u0002\u0010\u001aJ\u001a\u0010\u001b\u001a\u0004\u0018\u00010\u001c2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001d\u001a\u00020\u001cH\u0002J \u0010\u001e\u001a\u00020\u00192\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001f\u001a\u00020\u001c2\u0006\u0010 \u001a\u00020!H\u0002J\u0006\u0010\"\u001a\u00020\u0019J\u0006\u0010#\u001a\u00020\u0019J\u0006\u0010$\u001a\u00020\u0019R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/vaanisathi/asr/VoskASREngine;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "_state", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/vaanisathi/asr/VoskASREngine$ASRState;", "state", "Lkotlinx/coroutines/flow/StateFlow;", "getState", "()Lkotlinx/coroutines/flow/StateFlow;", "model", "Lorg/vosk/Model;", "recognizer", "Lorg/vosk/Recognizer;", "audioRecord", "Landroid/media/AudioRecord;", "isListening", "", "SAMPLE_RATE", "", "BUFFER_SIZE", "initialize", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "copyModelFromAssets", "", "assetFolder", "copyAssetFolder", "assetPath", "outDir", "Ljava/io/File;", "startListening", "stopListening", "release", "ASRState", "app_debug"})
public final class VoskASREngine {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.vaanisathi.asr.VoskASREngine.ASRState> _state = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.vaanisathi.asr.VoskASREngine.ASRState> state = null;
    @org.jetbrains.annotations.Nullable()
    private org.vosk.Model model;
    @org.jetbrains.annotations.Nullable()
    private org.vosk.Recognizer recognizer;
    @org.jetbrains.annotations.Nullable()
    private android.media.AudioRecord audioRecord;
    private boolean isListening = false;
    private final int SAMPLE_RATE = 16000;
    private final int BUFFER_SIZE = 0;
    
    public VoskASREngine(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.vaanisathi.asr.VoskASREngine.ASRState> getState() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object initialize(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final java.lang.String copyModelFromAssets(android.content.Context context, java.lang.String assetFolder) {
        return null;
    }
    
    private final void copyAssetFolder(android.content.Context context, java.lang.String assetPath, java.io.File outDir) {
    }
    
    public final void startListening() {
    }
    
    public final void stopListening() {
    }
    
    public final void release() {
    }
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0004\u0005\u0006\u0007\bB\t\b\u0004\u00a2\u0006\u0004\b\u0002\u0010\u0003\u0082\u0001\u0005\t\n\u000b\f\r\u00a8\u0006\u000e"}, d2 = {"Lcom/vaanisathi/asr/VoskASREngine$ASRState;", "", "<init>", "()V", "Idle", "Loading", "Listening", "Result", "Error", "Lcom/vaanisathi/asr/VoskASREngine$ASRState$Error;", "Lcom/vaanisathi/asr/VoskASREngine$ASRState$Idle;", "Lcom/vaanisathi/asr/VoskASREngine$ASRState$Listening;", "Lcom/vaanisathi/asr/VoskASREngine$ASRState$Loading;", "Lcom/vaanisathi/asr/VoskASREngine$ASRState$Result;", "app_debug"})
    public static abstract class ASRState {
        
        private ASRState() {
            super();
        }
        
        @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\t\u0010\b\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0011"}, d2 = {"Lcom/vaanisathi/asr/VoskASREngine$ASRState$Error;", "Lcom/vaanisathi/asr/VoskASREngine$ASRState;", "message", "", "<init>", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class Error extends com.vaanisathi.asr.VoskASREngine.ASRState {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String message = null;
            
            public Error(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getMessage() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component1() {
                return null;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.vaanisathi.asr.VoskASREngine.ASRState.Error copy(@org.jetbrains.annotations.NotNull()
            java.lang.String message) {
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
        
        @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2 = {"Lcom/vaanisathi/asr/VoskASREngine$ASRState$Idle;", "Lcom/vaanisathi/asr/VoskASREngine$ASRState;", "<init>", "()V", "app_debug"})
        public static final class Idle extends com.vaanisathi.asr.VoskASREngine.ASRState {
            @org.jetbrains.annotations.NotNull()
            public static final com.vaanisathi.asr.VoskASREngine.ASRState.Idle INSTANCE = null;
            
            private Idle() {
            }
        }
        
        @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2 = {"Lcom/vaanisathi/asr/VoskASREngine$ASRState$Listening;", "Lcom/vaanisathi/asr/VoskASREngine$ASRState;", "<init>", "()V", "app_debug"})
        public static final class Listening extends com.vaanisathi.asr.VoskASREngine.ASRState {
            @org.jetbrains.annotations.NotNull()
            public static final com.vaanisathi.asr.VoskASREngine.ASRState.Listening INSTANCE = null;
            
            private Listening() {
            }
        }
        
        @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2 = {"Lcom/vaanisathi/asr/VoskASREngine$ASRState$Loading;", "Lcom/vaanisathi/asr/VoskASREngine$ASRState;", "<init>", "()V", "app_debug"})
        public static final class Loading extends com.vaanisathi.asr.VoskASREngine.ASRState {
            @org.jetbrains.annotations.NotNull()
            public static final com.vaanisathi.asr.VoskASREngine.ASRState.Loading INSTANCE = null;
            
            private Loading() {
            }
        }
        
        @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\r\u001a\u00020\u0005H\u00c6\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00c6\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u00d6\u0003J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001J\t\u0010\u0015\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0016"}, d2 = {"Lcom/vaanisathi/asr/VoskASREngine$ASRState$Result;", "Lcom/vaanisathi/asr/VoskASREngine$ASRState;", "text", "", "latencyMs", "", "<init>", "(Ljava/lang/String;J)V", "getText", "()Ljava/lang/String;", "getLatencyMs", "()J", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "app_debug"})
        public static final class Result extends com.vaanisathi.asr.VoskASREngine.ASRState {
            @org.jetbrains.annotations.NotNull()
            private final java.lang.String text = null;
            private final long latencyMs = 0L;
            
            public Result(@org.jetbrains.annotations.NotNull()
            java.lang.String text, long latencyMs) {
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String getText() {
                return null;
            }
            
            public final long getLatencyMs() {
                return 0L;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final java.lang.String component1() {
                return null;
            }
            
            public final long component2() {
                return 0L;
            }
            
            @org.jetbrains.annotations.NotNull()
            public final com.vaanisathi.asr.VoskASREngine.ASRState.Result copy(@org.jetbrains.annotations.NotNull()
            java.lang.String text, long latencyMs) {
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
}