package ua.kulku.onlooker;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;
import ua.kulku.onlooker.di.StorageModule;
import ua.kulku.onlooker.storage.Storage;

public class MyApplication extends Application {
    public static volatile MyApplication sInstance;
    public static volatile AppComponent sComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        sComponent = DaggerMyApplication_AppComponent.create();
    }

    @Singleton
    @Component(modules = {StorageModule.class})
    public interface AppComponent {
        Storage storage();
    }
}
