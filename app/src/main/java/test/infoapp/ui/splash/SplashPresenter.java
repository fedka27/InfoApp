package test.infoapp.ui.splash;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodeal.ads.NonSkippableVideoCallbacks;

import io.reactivex.disposables.CompositeDisposable;
import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.injection.model.managers.AdsManager;
import test.infoapp.injection.model.repositories.ConfigRepository;
import test.infoapp.util.connection.ConnectionUtilAbs;
import test.infoapp.util.rx.RxSchedulersAbs;

public class SplashPresenter implements SplashContract.Presenter {
    private static final String TAG = SplashPresenter.class.getSimpleName();
    private SplashContract.View view;

    private ConfigRepository configRepository;
    private RxSchedulersAbs rxSchedulersAbs;
    private AdsManager adsManager;
    private ConnectionUtilAbs connectionUtilAbs;
    private CompositeDisposable compositeDisposable;

    public SplashPresenter(ConfigRepository configRepository,
                           RxSchedulersAbs rxSchedulersAbs,
                           AdsManager adsManager,
                           ConnectionUtilAbs connectionUtilAbs,
                           CompositeDisposable compositeDisposable) {
        this.configRepository = configRepository;
        this.rxSchedulersAbs = rxSchedulersAbs;
        this.adsManager = adsManager;
        this.connectionUtilAbs = connectionUtilAbs;
        this.compositeDisposable = compositeDisposable;
    }

    @Override
    public void setView(SplashContract.View view) {
        this.view = view;
    }

    @Override
    public void onStart() {
        if (!connectionUtilAbs.isOnline()) {
            view.openLocalActivity();
            return;
        }

        compositeDisposable.add(configRepository.getConfig()
                .compose(rxSchedulersAbs.getComputationToMainTransformer())
                .subscribe(this::showAdsOrOpenNextScreen));
    }

    private void showAdsOrOpenNextScreen(Config config) {

        boolean isShowVideo = adsManager.showAdsOfType(config, view);

        if (!isShowVideo) {
            runNextActivity(config.isOffline());
            return;
        }
        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            @Override
            public void onInterstitialLoaded(boolean b) {

            }

            @Override
            public void onInterstitialFailedToLoad() {
            }

            @Override
            public void onInterstitialShown() {
            }

            @Override
            public void onInterstitialClicked() {

            }

            @Override
            public void onInterstitialClosed() {
                runNextActivity(config.isOffline());
            }
        });

        Appodeal.setNonSkippableVideoCallbacks(new NonSkippableVideoCallbacks() {
            @Override
            public void onNonSkippableVideoLoaded() {

            }

            @Override
            public void onNonSkippableVideoFailedToLoad() {
            }

            @Override
            public void onNonSkippableVideoShown() {

            }

            @Override
            public void onNonSkippableVideoFinished() {

            }

            @Override
            public void onNonSkippableVideoClosed(boolean b) {
                runNextActivity(config.isOffline());
            }
        });
    }


    private synchronized void runNextActivity(boolean offline) {
        if (offline) {
            view.openLocalActivity();
        } else {
            view.openListActivity();
        }
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
    }
}
