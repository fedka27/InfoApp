package test.infoapp.ui.main;

import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.ui.base.interfaces.BaseScreen;

public interface MainContract {
    interface View extends BaseScreen.View {
        void configureAds(Config config);
    }

    interface Presenter extends BaseScreen.Presenter<View> {
    }
}
