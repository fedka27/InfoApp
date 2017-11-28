package info.renamed.ui.list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.appodeal.ads.Appodeal;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import info.renamed.R;
import info.renamed.injection.ComponentProvider;
import info.renamed.injection.model.data.dto.Config;
import info.renamed.injection.model.data.dto.Item;
import info.renamed.ui.ProgressWebDialog;
import info.renamed.ui.base.BaseActivity;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

public class ListActivity extends BaseActivity implements ListContract.View {
    private static final String TAG = ListActivity.class.getSimpleName();

    @Inject ListContract.Presenter presenter;

    @BindView(R.id.container) ViewGroup container;
    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private ListAdapter listAdapter;

    public static void start(Activity activity) {
        activity.startActivity(new Intent(activity, ListActivity.class));
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

        listAdapter = new ListAdapter(presenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(presenter);

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
    public void loadBgOrParseColor(String bgImage, int bgColor) {
        if (bgImage != null) {
            Glide.with(this)
                    .load(bgImage)
                    .bitmapTransform(new ColorFilterTransformation(this, R.color.black))
                    .placeholder(new ColorDrawable(bgColor))
                    .into(new SimpleTarget<GlideDrawable>() {
                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                            container.setBackground(resource);
                        }
                    });
        } else {
            Drawable drawable = new ColorDrawable(bgColor);
            container.setBackground(drawable);
        }
    }

    @Override
    public void setList(List<Item> listItems) {
        listAdapter.setItems(listItems);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgressWebDialog(String linkTitle, String link) {
        new ProgressWebDialog(this, linkTitle, link).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
}
