package com.audiofocus;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class AudioFocusModule extends ReactContextBaseJavaModule implements AudioManager.OnAudioFocusChangeListener {
    private static ReactApplicationContext reactContext;
    private AudioManager audioManager;

    AudioFocusModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Override
    public String getName() {
        return "AudioFocusModule";
    }

    @ReactMethod
    public void requestAudioFocus() {
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.d("AudioFocusModule", "Audio focus request granted");
        } else {
            Log.d("AudioFocusModule", "Audio focus request failed");
        }
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
                event = "AUDIOFOCUS_GAIN";
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                event = "AUDIOFOCUS_LOSS";
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                event = "AUDIOFOCUS_LOSS_TRANSIENT";
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                event = "AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK";
                break;
        }
        if (event != null) {
            sendEvent(event);
        }
    }

    private void sendEvent(String eventName) {
        reactContext
            .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit(eventName, null);
    }
}
