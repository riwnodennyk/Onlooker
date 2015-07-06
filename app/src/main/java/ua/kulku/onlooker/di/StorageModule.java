package ua.kulku.onlooker.di;

import android.content.Context;

import com.firebase.client.Firebase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ua.kulku.onlooker.MyApplication;

@Module
public class StorageModule {

    @Provides
    public Context provideContext() {
        return MyApplication.sInstance;
    }

    @Provides
    @Singleton
    public Firebase provideFirebase() {
        Firebase firebase = new Firebase("https://onlooker.firebaseio.com/questions/");
        firebase.keepSynced(true);
        return firebase;
    }
}
