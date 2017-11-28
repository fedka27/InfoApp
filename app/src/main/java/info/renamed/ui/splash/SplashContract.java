package info.renamed.ui.splash;

import info.renamed.ui.AdsView;
import info.renamed.ui.base.interfaces.BaseScreen;

public interface SplashContract extends BaseScreen {
    interface View extends BaseScreen.View, AdsView {
        void openLocalActivity();

        void openListActivity();
    }

    interface Presenter extends BaseScreen.Presenter<View> {

    }
}
