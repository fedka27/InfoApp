package test.infoapp.ui.list;

import com.appodeal.ads.Appodeal;

import io.reactivex.disposables.CompositeDisposable;
import test.infoapp.injection.model.data.dto.ListItem;
import test.infoapp.injection.model.interactors.ViewInteractor;
import test.infoapp.injection.model.managers.AdsManager;
import test.infoapp.injection.model.repositories.ConfigRepository;
import test.infoapp.injection.model.repositories.ContentRepository;
import test.infoapp.util.L;
import test.infoapp.util.rx.RxSchedulersAbs;

public class ListPresenter implements ListContract.Presenter {
    private static final String TAG = ListPresenter.class.getSimpleName();
    private ListContract.View view;

    private ConfigRepository configRepository;
    private ContentRepository contentRepository;
    private AdsManager adsManager;
    private RxSchedulersAbs rxSchedulersAbs;
    private ViewInteractor viewInteractor;
    private CompositeDisposable compositeDisposable;

    public ListPresenter(ConfigRepository configRepository,
                         ContentRepository contentRepository,
                         AdsManager adsClickManager,
                         RxSchedulersAbs rxSchedulersAbs,
                         ViewInteractor viewInteractor,
                         CompositeDisposable compositeDisposable) {
        this.configRepository = configRepository;
        this.contentRepository = contentRepository;
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
        view.configureAds(configRepository.getConfigSaved());
        loadData();
    }

    private void loadData() {
        compositeDisposable.add(contentRepository.items()
                .compose(rxSchedulersAbs.getIOToMainTransformerSingle())
                .compose(viewInteractor.manageProgressSingle(view))
                .subscribe(listItems -> view.setList(listItems),
                        throwable -> viewInteractor.manageError(view, throwable)));

    }

    @Override
    public void onClick(ListItem.Spoiler spoiler) {
        boolean isShowAds = adsManager.clickToLinkAndIsShowAds();
        L.d(TAG, "isShowAds - " + isShowAds);
        if (isShowAds) {
            if (adsManager.isVideoTypeAds(configRepository.getConfigSaved())) {
                if (Appodeal.isLoaded(Appodeal.NON_SKIPPABLE_VIDEO)) view.showAdsVideo();
                else if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) view.showAdsFullScreen();
            } else {
                if (Appodeal.isLoaded(Appodeal.INTERSTITIAL)) view.showAdsFullScreen();
                else if (Appodeal.isLoaded(Appodeal.NON_SKIPPABLE_VIDEO)) view.showAdsVideo();
            }
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
    }
}
