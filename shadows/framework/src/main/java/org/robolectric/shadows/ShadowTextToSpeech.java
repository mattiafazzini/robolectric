package org.robolectric.shadows;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import java.util.HashMap;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;

@Implements(TextToSpeech.class)
public class ShadowTextToSpeech {

    private Context context;

    private TextToSpeech.OnInitListener listener;

    private String lastSpokenText;

    private boolean shutdown = false;

    private boolean stopped = true;

    private int queueMode = -1;

    @Implementation
    protected void __constructor__(Context context, TextToSpeech.OnInitListener listener) {
        System.out.println("ShadowTextToSpeech#__constructor__");
        this.context = context;
        this.listener = listener;
    }

    @Implementation
    protected int speak(final String text, final int queueMode, final HashMap<String, String> params) {
        System.out.println("ShadowTextToSpeech#speak");
        stopped = false;
        lastSpokenText = text;
        this.queueMode = queueMode;
        return TextToSpeech.SUCCESS;
    }

    @Implementation(minSdk = LOLLIPOP)
    protected int speak(final CharSequence text, final int queueMode, final Bundle params, final String utteranceId) {
        System.out.println("ShadowTextToSpeech#speak");
        return speak(text.toString(), queueMode, new HashMap<>());
    }

    @Implementation
    protected void shutdown() {
        System.out.println("ShadowTextToSpeech#shutdown");
        shutdown = true;
    }

    @Implementation
    protected int stop() {
        System.out.println("ShadowTextToSpeech#stop");
        stopped = true;
        return TextToSpeech.SUCCESS;
    }

    public Context getContext() {
        return context;
    }

    public TextToSpeech.OnInitListener getOnInitListener() {
        return listener;
    }

    public String getLastSpokenText() {
        return lastSpokenText;
    }

    public void clearLastSpokenText() {
        lastSpokenText = null;
    }

    public boolean isShutdown() {
        return shutdown;
    }

    /**
     * @return {@code true} if the TTS is stopped.
     */
    public boolean isStopped() {
        return stopped;
    }

    public int getQueueMode() {
        return queueMode;
    }
}

