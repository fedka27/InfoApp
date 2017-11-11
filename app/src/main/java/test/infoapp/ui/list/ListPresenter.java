package test.infoapp.ui.list;

import io.reactivex.disposables.CompositeDisposable;
import test.infoapp.injection.model.data.dto.ListItem;
import test.infoapp.injection.model.data.dto.Style;
import test.infoapp.injection.model.interactors.ViewInteractor;
import test.infoapp.injection.model.managers.AdsManager;
import test.infoapp.injection.model.repositories.ConfigRepository;
import test.infoapp.injection.model.repositories.ContentRepository;
import test.infoapp.injection.model.repositories.StylesRepository;
import test.infoapp.util.L;
import test.infoapp.util.rx.RxSchedulersAbs;

public class ListPresenter implements ListContract.Presenter {
    private static final String TAG = ListPresenter.class.getSimpleName();
    private ListContract.View view;

    private ConfigRepository configRepository;
    private ContentRepository contentRepository;
    private StylesRepository stylesRepository;
    private AdsManager adsManager;
    private RxSchedulersAbs rxSchedulersAbs;
    private ViewInteractor viewInteractor;
    private CompositeDisposable compositeDisposable;

    private boolean isShowAdsByFirstClick;

    public ListPresenter(ConfigRepository configRepository,
                         ContentRepository contentRepository,
                         StylesRepository stylesRepository,
                         AdsManager adsClickManager,
                         RxSchedulersAbs rxSchedulersAbs,
                         ViewInteractor viewInteractor,
                         CompositeDisposable compositeDisposable) {
        this.configRepository = configRepository;
        this.contentRepository = contentRepository;
        this.stylesRepository = stylesRepository;
        this.adsManager = adsClickManager;
        this.rxSchedulersAbs = rxSchedulersAbs;
        this.viewInteractor = viewInteractor;
        this.compositeDisposable = compositeDisposable;

        isShowAdsByFirstClick = true;
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
        compositeDisposable.add(contentRepository.content()
                .compose(rxSchedulersAbs.getIOToMainTransformerSingle())
                .compose(viewInteractor.manageProgressSingle(view))
                .subscribe(content -> {
                            view.loadBgOrParseColor(content.getImageBg(), content.getBgColor());
                            view.setList(content.getList());
                        },
                        throwable -> viewInteractor.manageError(view, throwable)));

    }

    @Override
    public void onClick(ListItem.Spoiler spoiler) {
        boolean isShowAds = adsManager.clickToLinkAndIsShowAds();
        L.d(TAG, "isShowAds - " + isShowAds);
        if (isShowAdsByFirstClick) {
            isShowAdsByFirstClick = false;
            adsManager.showAdsOfType(configRepository.getConfigSaved(), view);
        }
        if (isShowAds) {
            adsManager.showAdsOfType(configRepository.getConfigSaved(), view);
        }
    }

    @Override
    public Style getStyleById(int styleId) {
        return stylesRepository.getStyleById(styleId);
    }

    @Override
    public void onLinkPressed(String link) {
        view.showProgressWebDialog(link);
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
