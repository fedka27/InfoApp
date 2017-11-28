package info.renamed.ui.list;

import android.support.v4.widget.SwipeRefreshLayout;

import java.util.List;

import info.renamed.injection.model.data.dto.Config;
import info.renamed.injection.model.data.dto.Item;
import info.renamed.injection.model.data.dto.Link;
import info.renamed.injection.model.data.dto.Spoiler;
import info.renamed.ui.AdsView;
import info.renamed.ui.base.interfaces.BaseScreen;
import info.renamed.ui.base.interfaces.IProgressListener;
import info.renamed.ui.base.interfaces.IThrowableListener;

public interface ListContract {
    interface View extends BaseScreen.View,
            IProgressListener,
            IThrowableListener,
            AdsView {
        void configureAds(Config config);

        void loadBgOrParseColor(String bgImage, int bgColor);

        void setList(List<Item> listItems);

        void showProgressWebDialog(String linkTitle, String link);
    }

    interface Presenter extends BaseScreen.Presenter<View>, AdapterPresenter, SwipeRefreshLayout.OnRefreshListener {
    }

    interface AdapterPresenter extends BaseScreen.AdapterPresenter {
        void onClick(Spoiler spoiler);

        void onLinkPressed(Link link);
    }
}
