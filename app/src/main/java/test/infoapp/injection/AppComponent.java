package test.infoapp.injection;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import test.infoapp.injection.api.ApiModule;
import test.infoapp.injection.manager.UtilsModule;
import test.infoapp.injection.presenters.PresenterModule;
import test.infoapp.injection.presenters.PresentersComponent;
import test.infoapp.injection.repositories.RepositoriesModule;

@Singleton
@Component(modules = {AppModule.class,
        ApiModule.class,
        RepositoriesModule.class,
        UtilsModule.class})
public interface AppComponent {

    Context getContext();

    PresentersComponent plus(PresenterModule module);
}
