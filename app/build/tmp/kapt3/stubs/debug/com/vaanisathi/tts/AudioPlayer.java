package com.vaanisathi.tts;

@kotlin.Metadata(mv = {2, 2, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000fJ\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u000fJ \u0010\u0013\u001a\u00020\r2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u000f2\b\b\u0002\u0010\u0016\u001a\u00020\u0017J\u0006\u0010\u0018\u001a\u00020\rJ\u0006\u0010\u0019\u001a\u00020\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/vaanisathi/tts/AudioPlayer;", "", "context", "Landroid/content/Context;", "<init>", "(Landroid/content/Context;)V", "mediaPlayer", "Landroid/media/MediaPlayer;", "tts", "Landroid/speech/tts/TextToSpeech;", "ttsReady", "", "playFromAssets", "", "filename", "", "fallbackText", "speakNow", "text", "speakBothDirections", "santhaliText", "hindiText", "delayMs", "", "release", "shutdown", "app_debug"})
public final class AudioPlayer {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaPlayer mediaPlayer;
    @org.jetbrains.annotations.Nullable()
    private android.speech.tts.TextToSpeech tts;
    @kotlin.jvm.Volatile()
    private volatile boolean ttsReady = false;
    
    public AudioPlayer(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    public final void playFromAssets(@org.jetbrains.annotations.NotNull()
    java.lang.String filename, @org.jetbrains.annotations.NotNull()
    java.lang.String fallbackText) {
    }
    
    public final void speakNow(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
    }
    
    public final void speakBothDirections(@org.jetbrains.annotations.NotNull()
    java.lang.String santhaliText, @org.jetbrains.annotations.NotNull()
    java.lang.String hindiText, long delayMs) {
    }
    
    public final void release() {
    }
    
    public final void shutdown() {
    }
}