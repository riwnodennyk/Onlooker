package ua.kulku.onlooker;

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
        sInstance = this;
    }
}
