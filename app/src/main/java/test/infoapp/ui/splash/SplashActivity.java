package test.infoapp.ui.splash;

import android.os.Bundle;

import com.appodeal.ads.Appodeal;

import javax.inject.Inject;

import test.infoapp.R;
import test.infoapp.injection.ComponentProvider;
import test.infoapp.ui.base.BaseActivity;
import test.infoapp.ui.list.ListActivity;
import test.infoapp.ui.main.MainActivity;

public class SplashActivity extends BaseActivity implements SplashContract.View {
    @Inject SplashContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initAds();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    private void initAds() {
        String appKey = getString(R.string.ads_key);
        Appodeal.initialize(this, appKey, Appodeal.INTERSTITIAL & Appodeal.NON_SKIPPABLE_VIDEO);
    }

    @Override
    protected void initPresenter() {
        ComponentProvider.getInstance().getPresentersComponent().inject(this);
        presenter.setView(this);
    }

    @Override
    public void openLocalActivity() {
        MainActivity.start(this);
    }

    @Override
    public void openListActivity() {
        ListActivity.start(this);
    }

    @Override
    public void showAdsInterstitial() {
        Appodeal.show(this, Appodeal.INTERSTITIAL);
    }

    @Override
    public void showAdsVideo() {
        Appodeal.show(this, Appodeal.NON_SKIPPABLE_VIDEO);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
}
