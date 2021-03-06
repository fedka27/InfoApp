package info.renamed.ui.splash;

import android.os.Bundle;

import javax.inject.Inject;

import info.renamed.R;
import info.renamed.injection.ComponentProvider;
import info.renamed.ui.base.BaseActivity;
import info.renamed.ui.list.ListActivity;
import info.renamed.ui.main.MainActivity;

public class SplashActivity extends BaseActivity implements SplashContract.View {
    @Inject SplashContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
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
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
}
