package com.vaanisathi.asr;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0001\u0019B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0012J\u0014\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u0017J\u0006\u0010\u0018\u001a\u00020\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r@BX\u0086\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001a"}, d2 = {"Lcom/vaanisathi/asr/StudentVoiceRecorder;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "recorder", "Landroid/media/MediaRecorder;", "player", "Landroid/media/MediaPlayer;", "outputFile", "Ljava/io/File;", "value", "Lcom/vaanisathi/asr/StudentVoiceRecorder$State;", "state", "getState", "()Lcom/vaanisathi/asr/StudentVoiceRecorder$State;", "startRecording", "", "stopRecording", "playBack", "", "onComplete", "Lkotlin/Function0;", "release", "State", "app_debug"})
public final class StudentVoiceRecorder {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaRecorder recorder;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaPlayer player;
    @org.jetbrains.annotations.Nullable()
    private java.io.File outputFile;
    @org.jetbrains.annotations.NotNull()
    private com.vaanisathi.asr.StudentVoiceRecorder.State state = com.vaanisathi.asr.StudentVoiceRecorder.State.IDLE;
    
    public StudentVoiceRecorder(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.vaanisathi.asr.StudentVoiceRecorder.State getState() {
        return null;
    }
    
    public final boolean startRecording() {
        return false;
    }
    
    public final boolean stopRecording() {
        return false;
    }
    
    public final void playBack(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onComplete) {
    }
    
    public final void release() {
    }
    
    @kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/vaanisathi/asr/StudentVoiceRecorder$State;", "", "<init>", "(Ljava/lang/String;I)V", "IDLE", "RECORDING", "PLAYING", "app_debug"})
    public static enum State {
        /*public static final*/ IDLE /* = new IDLE() */,
        /*public static final*/ RECORDING /* = new RECORDING() */,
        /*public static final*/ PLAYING /* = new PLAYING() */;
        
        State() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.vaanisathi.asr.StudentVoiceRecorder.State> getEntries() {
            return null;
        }
    }
}