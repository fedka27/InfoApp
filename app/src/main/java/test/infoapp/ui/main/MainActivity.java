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
import android.widget.ImageView;
import android.widget.TextView;

import com.appodeal.ads.Appodeal;

import javax.inject.Inject;

import butterknife.BindView;
import test.infoapp.R;
import test.infoapp.injection.ComponentProvider;
import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.ui.base.BaseActivity;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainContract.View {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject MainContract.Presenter presenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;
    @BindView(R.id.nav_view) NavigationView navigationView;

    @BindView(R.id.content_text_view) TextView textView;
    @BindView(R.id.image_view) ImageView imageView;

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


        onNavigationItemSelected(navigationView.getMenu().findItem(R.id.nav_cat_1));

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);

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

        if (id == R.id.nav_cat_1) {
            textView.setText(R.string.text_1);
            imageView.setImageResource(R.drawable.image_1);
        } else if (id == R.id.nav_cat_2) {
            textView.setText(R.string.text_2);
            imageView.setImageResource(R.drawable.image_2);
        } else if (id == R.id.nav_cat_3) {
            textView.setText(R.string.text_3);
            imageView.setImageResource(R.drawable.image_1);
        }

        presenter.onCategoryPressed();

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
}
