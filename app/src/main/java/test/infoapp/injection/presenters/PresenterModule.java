package test.infoapp.injection.presenters;


import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import test.infoapp.injection.model.interactors.ViewInteractor;
import test.infoapp.injection.model.managers.AdsManager;
import test.infoapp.injection.model.repositories.ConfigRepository;
import test.infoapp.injection.model.repositories.ContentRepository;
import test.infoapp.ui.list.ListContract;
import test.infoapp.ui.list.ListPresenter;
import test.infoapp.ui.main.MainContract;
import test.infoapp.ui.main.MainPresenter;
import test.infoapp.ui.splash.SplashContract;
import test.infoapp.ui.splash.SplashPresenter;
import test.infoapp.util.connection.ConnectionUtilAbs;
import test.infoapp.util.rx.RxSchedulersAbs;

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
