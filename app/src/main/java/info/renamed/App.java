package info.renamed;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.appodeal.ads.Appodeal;
import com.appodeal.ads.BannerCallbacks;
import com.appodeal.ads.InterstitialCallbacks;
import com.appodeal.ads.NonSkippableVideoCallbacks;
import com.yandex.metrica.YandexMetrica;

import info.renamed.injection.AppComponent;
import info.renamed.injection.AppModule;
import info.renamed.injection.ComponentProvider;
import info.renamed.injection.DaggerAppComponent;
import info.renamed.injection.manager.UtilsModule;
import info.renamed.injection.presenters.PresenterModule;
import info.renamed.injection.presenters.PresentersComponent;
import info.renamed.util.L;
import io.paperdb.Paper;

public class App extends MultiDexApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .utilsModule(new UtilsModule())
                .build();

        PresentersComponent presentersComponent = appComponent.plus(new PresenterModule());

        ComponentProvider.getInstance().init(appComponent, presentersComponent);

        Paper.init(this);

        initMetrica();

        Appodeal.disableNetwork(this, "facebook");
        Appodeal.disableNetwork(this, "vungle");
    }

    private void initMetrica() {
        YandexMetrica.activate(this, getString(info.renamed.R.string.key_metrica));
        YandexMetrica.enableActivityAutoTracking(this);

        Appodeal.setBannerCallbacks(new BannerCallbacks() {
            @Override
            public void onBannerLoaded(int i, boolean b) {
                L.d("Banner loaded - " + i + " - " + b);
            }

            @Override
            public void onBannerFailedToLoad() {

            }

            @Override
            public void onBannerShown() {
                L.d("onBannerShown");
                YandexMetrica.reportEvent(getString(info.renamed.R.string.metrica_event_show_banner));
            }

            @Override
            public void onBannerClicked() {

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
                L.d("onVideoShown");
                YandexMetrica.reportEvent(getString(info.renamed.R.string.metrica_event_show_nonskipable));
            }

            @Override
            public void onNonSkippableVideoFinished() {

            }

            @Override
            public void onNonSkippableVideoClosed(boolean b) {

            }
        });

        Appodeal.setInterstitialCallbacks(new InterstitialCallbacks() {
            @Override
            public void onInterstitialLoaded(boolean b) {

            }

            @Override
            public void onInterstitialFailedToLoad() {

            }

            @Override
            public void onInterstitialShown() {
                L.d("onInterstitialShown");
                YandexMetrica.reportEvent(getString(info.renamed.R.string.metrica_event_show_interstitial));
            }

            @Override
            public void onInterstitialClicked() {

            }

            @Override
            public void onInterstitialClosed() {

            }
        });
    }
}
