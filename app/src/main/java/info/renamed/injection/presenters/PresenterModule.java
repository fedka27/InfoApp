package info.renamed.injection.presenters;


import dagger.Module;
import dagger.Provides;
import info.renamed.injection.model.interactors.ViewInteractor;
import info.renamed.injection.model.managers.AdsManager;
import info.renamed.injection.model.repositories.ConfigRepository;
import info.renamed.injection.model.repositories.ContentRepository;
import info.renamed.ui.list.ListContract;
import info.renamed.ui.list.ListPresenter;
import info.renamed.ui.main.MainContract;
import info.renamed.ui.main.MainPresenter;
import info.renamed.ui.splash.SplashContract;
import info.renamed.ui.splash.SplashPresenter;
import info.renamed.util.connection.ConnectionUtilAbs;
import info.renamed.util.rx.RxSchedulersAbs;
import io.reactivex.disposables.CompositeDisposable;

@Module
@PresenterScope
public class PresenterModule {

    @Provides
    SplashContract.Presenter provideSplashPresenter(ConfigRepository configRepository,
                                                    RxSchedulersAbs rxSchedulersAbs,
                                                    AdsManager adsManager,
                                                    ConnectionUtilAbs connectionUtilAbs,
                                                    CompositeDisposable compositeDisposable) {
        return new SplashPresenter(configRepository,
                rxSchedulersAbs,
                adsManager,
                connectionUtilAbs,
                compositeDisposable);
    }

    @Provides
    MainContract.Presenter provideMainPresenter(ConfigRepository configRepository,
                                                AdsManager adsManager) {
        return new MainPresenter(configRepository, adsManager);
    }

    @Provides
    @PresenterScope
    ListContract.Presenter provideListPresenter(ConfigRepository configRepository,
                                                ContentRepository contentRepository,
                                                AdsManager adsClickManager,
                                                RxSchedulersAbs rxSchedulersAbs,
                                                ViewInteractor viewInteractor,
                                                CompositeDisposable compositeDisposable) {
        return new ListPresenter(configRepository,
                contentRepository,
                adsClickManager,
                rxSchedulersAbs,
                viewInteractor,
                compositeDisposable);
    }

}
