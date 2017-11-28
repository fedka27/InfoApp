package info.renamed.ui.main;

import info.renamed.injection.model.data.dto.Config;
import info.renamed.ui.AdsView;
import info.renamed.ui.base.interfaces.BaseScreen;

public interface MainContract {
    interface View extends BaseScreen.View, AdsView {
        void configureAds(Config config);
    }

    interface Presenter extends BaseScreen.Presenter<View> {
        void onCategoryPressed();
    }
}
