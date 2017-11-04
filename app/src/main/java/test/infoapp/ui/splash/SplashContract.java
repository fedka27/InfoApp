package test.infoapp.ui.splash;

import test.infoapp.ui.base.interfaces.BaseScreen;

public interface SplashContract extends BaseScreen {
    interface View extends BaseScreen.View {
        void openLocalActivity();

        void openListActivity();

        void showAdsInterstitial();

        void showAdsVideo();
    }

    interface Presenter extends BaseScreen.Presenter<View> {

    }
}
