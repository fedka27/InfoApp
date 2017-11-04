package test.infoapp.ui.list;

import com.appodeal.ads.Appodeal;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import test.infoapp.injection.model.data.dto.ListItem;
import test.infoapp.injection.model.data.dto.ListItemType;
import test.infoapp.injection.model.interactors.ViewInteractor;
import test.infoapp.injection.model.managers.AdsManager;
import test.infoapp.injection.model.repositories.ConfigRepository;
import test.infoapp.util.rx.RxSchedulersAbs;

public class ListPresenter implements ListContract.Presenter {
    private ListContract.View view;

    private ConfigRepository configRepository;
    private AdsManager adsManager;
    private RxSchedulersAbs rxSchedulersAbs;
    private ViewInteractor viewInteractor;
    private CompositeDisposable compositeDisposable;

    public ListPresenter(ConfigRepository configRepository,
                         AdsManager adsClickManager,
                         RxSchedulersAbs rxSchedulersAbs,
                         ViewInteractor viewInteractor,
                         CompositeDisposable compositeDisposable) {
        this.configRepository = configRepository;
        this.adsManager = adsClickManager;
        this.rxSchedulersAbs = rxSchedulersAbs;
        this.viewInteractor = viewInteractor;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void setView(ListContract.View view) {
        this.view = view;
    }

    @Override
    public void onStart() {
        loadData();
    }

    private void loadData() {


        compositeDisposable.add(Observable.just(new ArrayList<ListItem>())
                .map(list -> {
                    for (int i = 0; i < 20; i++) {
                        list.add(new ListItem(i % 2 == 0 ? ListItemType.LINK : ListItemType.TEXT,
                                "http://test_link.com",
                                "Text is - " + i));
                    }
                    return list;
                })
                .compose(rxSchedulersAbs.getIOToMainTransformer())
                .compose(viewInteractor.manageProgressObservable(view))
                .subscribe(listItems -> view.setList(listItems),
                        throwable -> viewInteractor.manageError(view, throwable)));

    }

    @Override
    public void onClick(ListItem listItem) {
        if (adsManager.isClickToAds()) {
            if (adsManager.isVideoTypeAds(configRepository.getConfigSaved())) {
                if (Appodeal.isLoaded(Appodeal.NON_SKIPPABLE_VIDEO)) view.showAdsVideo();
            } else {
                if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) view.showAdsFullScreen();
            }
        }
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
    }
}
