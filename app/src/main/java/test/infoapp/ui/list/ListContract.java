package test.infoapp.ui.list;

import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.injection.model.data.dto.ListItem;
import test.infoapp.ui.base.interfaces.BaseScreen;
import test.infoapp.ui.base.interfaces.IProgressListener;
import test.infoapp.ui.base.interfaces.IThrowableListener;

public interface ListContract {
    interface View extends BaseScreen.View, IProgressListener, IThrowableListener {
        void configureAds(Config config);

        void setList(List<ListItem> listItems);

        void showAdsVideo();

        void showAdsFullScreen();
    }

    interface Presenter extends BaseScreen.Presenter<View>, AdapterPresenter, SwipeRefreshLayout.OnRefreshListener {
    }

    interface AdapterPresenter extends BaseScreen.AdapterPresenter {
        void onClick(ListItem.Spoiler spoiler);
    }
}
