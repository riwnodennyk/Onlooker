package ua.kulku.onlooker.di;

import javax.inject.Singleton;

import dagger.Component;
import ua.kulku.onlooker.model.Storage;

@Singleton
@Component(modules = {StorageModule.class})
public interface StorageComponent {
    @Singleton
    Storage storage();
}
