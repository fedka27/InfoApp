package test.infoapp.injection.model.managers;

import com.appodeal.ads.Appodeal;

import java.util.Random;

import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.ui.AdsView;
import test.infoapp.util.L;

public class AdsManager {
    private static final String TAG = AdsManager.class.getSimpleName();
    private final int clickCountToAds;
    private int clickCount = 0;

    public AdsManager(int clickCountToAds) {
        this.clickCountToAds = clickCountToAds;
    }

    public boolean clickToLinkAndIsShowAds() {
        clickCount++;

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

    public boolean isVideoTypeAds(Config config) {
        return isVideoTypeAds(config.getAds_video(), config.getAds_interstetial());
    }

    public boolean isVideoLoaded() {
        return Appodeal.isLoaded(Appodeal.NON_SKIPPABLE_VIDEO);
    }

    public boolean isInterstitialLoaded() {
        return Appodeal.isLoaded(Appodeal.INTERSTITIAL);
    }

    public void showAdsOfType(Config config, AdsView view) {
        if (isVideoTypeAds(config)) {
            if (isVideoLoaded()) view.showAdsVideo();
            else if (isInterstitialLoaded()) view.showAdsInterstitial();
        } else {
            if (isInterstitialLoaded()) view.showAdsInterstitial();
            else if (isVideoLoaded()) view.showAdsVideo();
        }
    }
}
