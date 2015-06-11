package ua.kulku.onlooker;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */
public class MyApplication extends android.app.Application {
    private static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        sInstance = this;
    }
}
