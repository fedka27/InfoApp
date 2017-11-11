package test.infoapp.ui.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import test.infoapp.R;
import test.infoapp.injection.ComponentProvider;
import test.infoapp.injection.model.data.dto.Config;
import test.infoapp.injection.model.data.dto.ListItem;
import test.infoapp.ui.ProgressWebDialog;
import test.infoapp.ui.base.BaseActivity;

public class ListActivity extends BaseActivity implements ListContract.View {

    @Inject ListContract.Presenter presenter;

    @BindView(R.id.container) ViewGroup container;
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

        listAdapter = new ListAdapter(presenter);
        recyclerView.setAdapter(listAdapter);

        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(presenter);
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
    public void setList(List<ListItem> listItems) {
        listAdapter.setItems(listItems);
    }

    @Override
    public void hideProgress() {
        super.hideProgress();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgressWebDialog(String link) {
        new ProgressWebDialog(this, link).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
}
