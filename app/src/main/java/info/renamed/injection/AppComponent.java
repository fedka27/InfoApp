package info.renamed.injection;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import info.renamed.injection.api.ApiModule;
import info.renamed.injection.manager.UtilsModule;
import info.renamed.injection.presenters.PresenterModule;
import info.renamed.injection.presenters.PresentersComponent;
import info.renamed.injection.repositories.RepositoriesModule;

@Singleton
@Component(modules = {AppModule.class,
        ApiModule.class,
        RepositoriesModule.class,
        UtilsModule.class})
public interface AppComponent {

    Context getContext();

    PresentersComponent plus(PresenterModule module);
}
