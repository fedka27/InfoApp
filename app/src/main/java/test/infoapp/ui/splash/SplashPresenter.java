package test.infoapp.ui.splash;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodeal.ads.NonSkippableVideoCallbacks;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import test.infoapp.data.model.Config;
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

        // TODO: 31.10.2017 IsLocalData;
        compositeDisposable.add(Observable.timer(2, TimeUnit.SECONDS)
                .compose(rxSchedulersAbs.getComputationToMainTransformer())
                .onErrorReturn(throwable -> 2L)
                .map(aLong -> {
                    configRepository.setConfig(new Config(null,
                            false,
                            true,
                            true,
                            50,
                            50,
                            true,
                            3));
                    return configRepository.getConfig();
                })
                .subscribe(this::showAdsOrOpenNextScreen));
    }

    private void showAdsOrOpenNextScreen(Config config) {
        if (!config.isAds()) {
            runNextActivity(config.isOffline());
            return;
        }

        boolean isVideo = adsManager.isVideoTypeAds(config.getAds_video(), config.getAds_interstetial());

        if (!isVideo) {
            if (!Appodeal.isLoaded(Appodeal.INTERSTITIAL)) {
                runNextActivity(config.isOffline());
                return;
            }
            Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
                @Override
                public void onInterstitialLoaded(boolean b) {

                }

                @Override
                public void onInterstitialFailedToLoad() {
                    runNextActivity(config.isOffline());
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
            view.showAdsInterstitial();
        } else {
            if (!Appodeal.isLoaded(Appodeal.NON_SKIPPABLE_VIDEO)) {
                runNextActivity(config.isOffline());
                return;
            }
            Appodeal.setNonSkippableVideoCallbacks(new NonSkippableVideoCallbacks() {
                @Override
                public void onNonSkippableVideoLoaded() {

                }

                @Override
                public void onNonSkippableVideoFailedToLoad() {
                    runNextActivity(config.isOffline());

                }

                @Override
                public void onNonSkippableVideoShown() {

                }

                @Override
                public void onNonSkippableVideoFinished() {
                    runNextActivity(config.isOffline());

                }

                @Override
                public void onNonSkippableVideoClosed(boolean b) {
                    runNextActivity(config.isOffline());
                }
            });
            view.showAdsVideo();
        }
    }

    private void runNextActivity(boolean offline) {
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
