package ua.kulku.onlooker.di;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    public DatabaseReference provideFirebase() {
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        firebase.keepSynced(true);
        return firebase;
    }
}
