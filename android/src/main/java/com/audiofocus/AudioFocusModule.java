package com.audiofocus;

import android.content.Context;
import android.media.AudioManager;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import javax.annotation.Nullable;

public class AudioFocusModule extends ReactContextBaseJavaModule implements AudioManager.OnAudioFocusChangeListener {
    private static ReactApplicationContext reactContext;
    private final AudioManager audioManager;

    AudioFocusModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @NonNull
    @Override
    public String getName() {
        return "AudioFocusModule";
    }

    @ReactMethod
    public void requestAudioFocus() {
        audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
    }

    @ReactMethod
    public void abandonAudioFocus() {
        audioManager.abandonAudioFocus(this);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        String event = null;
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                event = "AUDIO_FOCUS_GAIN";
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                event = "AUDIO_FOCUS_LOSS";
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                event = "AUDIO_FOCUS_LOSS_TRANSIENT";
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                event = "AUDIO_FOCUS_LOSS_TRANSIENT_CAN_DUCK";
                break;
        }
        if (event != null) {
            sendEvent(event);
        }
    }

    private void sendEvent(String eventName) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, null);
    }
}
