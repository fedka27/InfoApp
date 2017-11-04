package test.infoapp.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.appodeal.ads.Appodeal;

import javax.inject.Inject;

import butterknife.BindView;
import test.infoapp.R;
import test.infoapp.data.model.Config;
import test.infoapp.injection.ComponentProvider;
import test.infoapp.ui.base.BaseActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject MainContract.Presenter presenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.container_main) ViewGroup containerViewGroup;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK & Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void initPresenter() {
        ComponentProvider.getInstance().getPresentersComponent().inject(this);
        presenter.setView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);

        setTitle(R.string.activity_main_title);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationView.setNavigationItemSelectedListener(this);

        initAds();

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void initAds() {
        String appKey = getString(R.string.ads_key);

        Appodeal.initialize(this, appKey, Appodeal.INTERSTITIAL | Appodeal.REWARDED_VIDEO | Appodeal.BANNER);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void configureAds(Config config) {
        if (config.isAds_banner()) {
            Appodeal.setBannerViewId(R.id.banner_view);
            Appodeal.show(this, Appodeal.BANNER);
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
}
