package test.infoapp.ui.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.appodeal.ads.Appodeal;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import test.infoapp.R;
import test.infoapp.injection.ComponentProvider;
import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.injection.model.data.dto.ListItem;
import test.infoapp.ui.base.BaseActivity;

public class ListActivity extends BaseActivity implements ListContract.View {

    @Inject ListContract.Presenter presenter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private ListAdapter listAdapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, ListActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initPresenter() {
        ComponentProvider.getInstance().getPresentersComponent().inject(this);
        presenter.setView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        listAdapter = new ListAdapter(presenter);
        recyclerView.setAdapter(listAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(presenter);

        String appKey = getString(R.string.ads_key);
        Appodeal.initialize(this, appKey, Appodeal.INTERSTITIAL | Appodeal.NON_SKIPPABLE_VIDEO | Appodeal.BANNER);
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
    public void setList(List<ListItem> listItems) {
        listAdapter.setItems(listItems);
    }

    @Override
    public void showAdsVideo() {
        Appodeal.show(this, Appodeal.NON_SKIPPABLE_VIDEO);
    }

    @Override
    public void showAdsFullScreen() {
        Appodeal.show(this, Appodeal.INTERSTITIAL);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
}
