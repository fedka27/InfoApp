package test.infoapp.ui.base;

import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.appodeal.ads.Appodeal;

import butterknife.BindView;
import butterknife.ButterKnife;
import test.infoapp.R;
import test.infoapp.ui.AdsView;
import test.infoapp.ui.base.interfaces.IKeyboardManageListener;
import test.infoapp.ui.base.interfaces.IProgressListener;
import test.infoapp.ui.base.interfaces.IThrowableListener;
import test.infoapp.util.KeyboardUtil;
import test.infoapp.util.NotificationUtil;

public abstract class BaseActivity extends AppCompatActivity implements IProgressListener,
        IThrowableListener,
        IKeyboardManageListener,
        AdsView {

    protected NotificationUtil notificationUtil;
    @Nullable @BindView(R.id.progress_bar) ProgressBar progressBar;

    protected void setBackground(@DrawableRes int background) {
        getWindow().setBackgroundDrawableResource(background);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        setContentView(LayoutInflater.from(this).inflate(layoutResID, null, false));
    }

    @Override
    public void setContentView(View view) {
        initPresenter();
        super.setContentView(view);
        init(view);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        initPresenter();
        super.setContentView(view, params);
        init(view);
    }

    private void init(View view) {
        ButterKnife.bind(this);
        notificationUtil = new NotificationUtil(this, view);

        String adsKey = getString(R.string.ads_key);
        Appodeal.initialize(this, adsKey, Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO | Appodeal.BANNER);
    }


    @Override
    public void showProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String message) {
        notificationUtil.showError(message);
    }

    @Override
    public void showError(int message) {
        notificationUtil.showError(message);
    }

    @Override
    public void keyboardVisible(boolean isVisible) {
        if (isVisible) {
            KeyboardUtil.showKeyboard(this, notificationUtil.getView());
        } else {
            KeyboardUtil.hideKeyboard(this, notificationUtil.getView());
        }
    }

    @Override
    public void showAdsVideo() {
        Appodeal.show(this, Appodeal.NON_SKIPPABLE_VIDEO);
    }

    @Override
    public void showAdsInterstitial() {
        Appodeal.show(this, Appodeal.INTERSTITIAL);
    }

    protected abstract void initPresenter();
}
