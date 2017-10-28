package com.example.julia.livecookbook;

import android.app.Application;

import ru.yandex.speechkit.SpeechKit;
import timber.log.Timber;

/**
 * Created by julia on 27.10.17.
 */

public class CookbookApplication extends Application {
    private String API_KEY = "a5baa3ab-6a50-47d4-967c-2e4bd27aef51";
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        SpeechKit.getInstance().configure(getApplicationContext(), API_KEY );
    }
}
