package test.infoapp.ui.list;

import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.injection.model.data.dto.Item;
import test.infoapp.ui.AdsView;
import test.infoapp.ui.base.interfaces.BaseScreen;
import test.infoapp.ui.base.interfaces.IProgressListener;
import test.infoapp.ui.base.interfaces.IThrowableListener;

public interface ListContract {
    interface View extends BaseScreen.View,
            IProgressListener,
            IThrowableListener,
            AdsView {
        void configureAds(Config config);

        void loadBgOrParseColor(String bgImage, int bgColor);

        void setList(List<Item> listItems);

        void showProgressWebDialog(String link);
    }

    interface Presenter extends BaseScreen.Presenter<View>, AdapterPresenter, SwipeRefreshLayout.OnRefreshListener {
    }

    interface AdapterPresenter extends BaseScreen.AdapterPresenter {
        void onClick(Item.Spoiler spoiler);

        void onLinkPressed(String link);
    }
}
