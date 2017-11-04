package test.infoapp.injection.presenters;

import dagger.Subcomponent;
import test.infoapp.ui.list.ListActivity;
import test.infoapp.ui.main.MainActivity;
import test.infoapp.ui.splash.SplashActivity;

@PresenterScope
@Subcomponent(modules = PresenterModule.class)
public interface PresentersComponent {

    void inject(SplashActivity splashActivity);

    void inject(MainActivity mainActivity);

    void inject(ListActivity listActivity);
}
