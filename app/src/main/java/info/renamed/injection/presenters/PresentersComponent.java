package info.renamed.injection.presenters;

import dagger.Subcomponent;
import info.renamed.ui.list.ListActivity;
import info.renamed.ui.main.MainActivity;
import info.renamed.ui.splash.SplashActivity;

@PresenterScope
@Subcomponent(modules = PresenterModule.class)
public interface PresentersComponent {

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(ListActivity listActivity);
}
