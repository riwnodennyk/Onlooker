package ua.kulku.onlooker;

import android.app.Application;

import com.firebase.client.Firebase;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Singleton;

import dagger.Component;
import ua.kulku.onlooker.di.StorageModule;
import ua.kulku.onlooker.model.Storage;

/**
 * Created by andrii.lavrinenko on 07.03.2015.
 */
public class MyApplication extends Application {
    public static volatile MyApplication sInstance;
    public static volatile AppComponent sComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        sInstance = this;
        sComponent = DaggerMyApplication_AppComponent.create();
    }

    @Singleton
    @Component(modules = {StorageModule.class})
    public interface AppComponent {
        Storage storage();
    }
}
