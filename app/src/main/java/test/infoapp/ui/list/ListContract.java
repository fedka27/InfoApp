package test.infoapp.ui.list;

import java.util.List;

import test.infoapp.data.model.ListItem;
import test.infoapp.ui.base.interfaces.BaseScreen;
import test.infoapp.ui.base.interfaces.IProgressListener;
import test.infoapp.ui.base.interfaces.IThrowableListener;

public interface ListContract {
    interface View extends BaseScreen.View, IProgressListener, IThrowableListener {
        void setList(List<ListItem> listItems);

        void showAdsVideo();

        void showAdsFullScreen();
    }

    interface Presenter extends BaseScreen.Presenter<View>, AdapterPresenter {
    }

    interface AdapterPresenter extends BaseScreen.AdapterPresenter {
        void onClick(ListItem listItem);
    }
}
