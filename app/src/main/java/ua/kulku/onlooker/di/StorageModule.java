package ua.kulku.onlooker.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import ua.kulku.onlooker.MyApplication;
import ua.kulku.onlooker.model.FirebaseStorage;
import ua.kulku.onlooker.model.SharedPreferencesStorage;
import ua.kulku.onlooker.model.Storage;

@Module
public class StorageModule {

    @Provides
    public Context provideContext() {
        return MyApplication.sInstance;
    }

    @Provides
    public Storage provideStorage(SharedPreferencesStorage storage) {
        return storage;
    }
}
