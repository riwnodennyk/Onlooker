package ua.kulku.onlooker;

import android.content.SharedPreferences;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */
public class MyApplication extends android.app.Application {
    private static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }

    public static SharedPreferences getSharedPreferences() {
        return getInstance().getSharedPreferences("ModelStorage", MODE_PRIVATE);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }
}
