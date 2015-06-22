package ua.kulku.onlooker;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;

import junit.framework.Assert;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Component;
import ua.kulku.onlooker.model.Storage;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    @Inject
    Storage storage;

    public ApplicationTest() {
        super(Application.class);
    }

    @MediumTest
    public void testEqualComponentSingleton() {
        DaggerApplicationTest_AComponent.create().inject(this);
        Storage firstStorage = storage;
        DaggerApplicationTest_BComponent.create().inject(this);
        Storage secondStorage = storage;
        Assert.assertEquals(firstStorage, secondStorage);
    }


    @Component(dependencies = MyApplication.AppComponent.class)
    @Singleton
    public interface AComponent extends MyApplication.AppComponent {
        void inject(ApplicationTest object);
    }


    @Component(dependencies = MyApplication.AppComponent.class)
    @Singleton
    public interface BComponent extends MyApplication.AppComponent {
        void inject(ApplicationTest object);
    }
}