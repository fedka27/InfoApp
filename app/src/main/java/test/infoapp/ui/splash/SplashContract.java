package test.infoapp.ui.splash;

import test.infoapp.ui.AdsView;
import test.infoapp.ui.base.interfaces.BaseScreen;

public interface SplashContract extends BaseScreen {
    interface View extends BaseScreen.View, AdsView {
        void openLocalActivity();

        void openListActivity();
    }

    interface Presenter extends BaseScreen.Presenter<View> {

    }
}
