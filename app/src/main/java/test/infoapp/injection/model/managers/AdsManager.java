package test.infoapp.injection.model.managers;

import android.content.res.Resources;

import com.appodeal.ads.Appodeal;
import com.yandex.metrica.YandexMetrica;

import java.util.Random;

import info.renamed.R;
import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.ui.AdsView;
import test.infoapp.util.L;

public class AdsManager {
    private static final String TAG = AdsManager.class.getSimpleName();
    private final int clickCountToAds;
    private int clickCount = 0;
    private Resources resources;

    public AdsManager(int clickCountToAds, Resources resources) {
        this.clickCountToAds = clickCountToAds;
        this.resources = resources;
    }

    public boolean clickToLinkAndIsShowAds() {
        clickCount++;
        L.d(TAG, "click - " + clickCount + " of interval - " + clickCountToAds);
        if (clickCount >= clickCountToAds) {
            clearClickCount();
            return true;
        }
        return false;
    }

    public void clearClickCount() {
        clickCount = 0;
    }

    public boolean isVideoTypeAds(int videoPercent, int interstitialPercent) {
        Random random = new Random();
        int i = random.nextInt(interstitialPercent);
        int v = random.nextInt(videoPercent);

        int type = Math.max(v, i);
        L.e(TAG, "f - " + i + "\n v - " + v);
        L.e(TAG, "type- " + type);
        return v == type;
    }

    private boolean isVideoTypeAds(Config config) {
        return isVideoTypeAds(config.getAds_video(), config.getAds_interstetial());
    }

    private boolean isVideoLoaded() {
        return Appodeal.isLoaded(Appodeal.NON_SKIPPABLE_VIDEO);
    }

    private boolean isInterstitialLoaded() {
        return Appodeal.isLoaded(Appodeal.INTERSTITIAL);
    }

    public boolean showAdsOfType(Config config, AdsView view) {
        if (!config.isAds()) return false;
        if (isVideoTypeAds(config)) {
            if (isVideoLoaded()) {
                eventShowVideo();
                view.showAdsVideo();
                return true;
            } else if (isInterstitialLoaded()) {
                eventShowVideo();
                view.showAdsInterstitial();
                return true;
            }
            return false;
        } else {
            if (isInterstitialLoaded()) {
                eventShowInterstitial();
                view.showAdsInterstitial();
                return true;
            } else if (isVideoLoaded()) {
                eventShowVideo();
                view.showAdsVideo();
                return true;
            }
            return false;
        }
    }

    private void eventShowVideo() {
        YandexMetrica.reportEvent(resources.getString(R.string.metrica_event_call_nonskipable));
    }

    private void eventShowInterstitial() {
        YandexMetrica.reportEvent(resources.getString(R.string.metrica_event_call_interstitial));
    }
}
