package test.infoapp.ui.list;

import android.graphics.drawable.GradientDrawable;
import android.support.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import test.infoapp.R;
import test.infoapp.injection.model.data.dto.ListItem;
import test.infoapp.injection.model.data.dto.Style;
import test.infoapp.ui.base.BaseRecyclerAdapter;
import test.infoapp.ui.base.BaseRecyclerViewHolder;
import test.infoapp.ui.base.RecyclerRow;


public class ListAdapter extends BaseRecyclerAdapter {
    private static final String TAG = ListAdapter.class.getSimpleName();

    private ListContract.AdapterPresenter adapterPresenter;

    public ListAdapter(ListContract.AdapterPresenter adapterPresenter) {
        this.adapterPresenter = adapterPresenter;

        addToolbarHolder();

        recyclerRow.addRow(new RecyclerRow.Row() {
            @Override
            public boolean is(Object item) {
                return item == null;
            }

            @Override
            public int typeLayout() {
                return -1;
            }

            @Override
            public BaseRecyclerViewHolder viewHolder(ViewGroup parent) {
                return new ToolbarHolder(parent);
            }

            @Override
            public void bind(BaseRecyclerViewHolder holder, Object item) {

            }
        });
        recyclerRow.addRow(new RecyclerRow.Row() {

            @Override
            public boolean is(Object item) {
                return item instanceof ListItem && ((ListItem) item).isLink();
            }

            @Override
            public int typeLayout() {
                return 0;
            }

            @Override
            public BaseRecyclerViewHolder viewHolder(ViewGroup parent) {
                return new ViewLinkHolder(parent);
            }

            @Override
            public void bind(BaseRecyclerViewHolder holder, Object item) {
                ViewLinkHolder viewTextHolder = (ViewLinkHolder) holder;

                viewTextHolder.bind(((ListItem) item).getLink());
            }
        });
        recyclerRow.addRow(new RecyclerRow.Row() {

            @Override
            public boolean is(Object item) {
                return item instanceof ListItem && ((ListItem) item).isSpoiler();
            }

            @Override
            public int typeLayout() {
                return 1;
            }

            @Override
            public BaseRecyclerViewHolder viewHolder(ViewGroup parent) {
                return new ViewSpoilerHolder(parent);
            }

            @Override
            public void bind(BaseRecyclerViewHolder holder, Object item) {
                ViewSpoilerHolder spoilerHolder = (ViewSpoilerHolder) holder;

                spoilerHolder.bind(((ListItem) item).getSpoiler());
            }
        });
    }

    private void addToolbarHolder() {
        itemList.add(null);
    }

    public void setItems(List<ListItem> items) {
        clearList();
        addToolbarHolder();
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    private void loadIcon(ImageView imageView, String icon) {
        if (icon != null) {
            Glide.with(imageView.getContext())
                    .load(icon)
                    .into(imageView);
        }
    }

    private void setStyle(ViewGroup viewGroup, TextView textView, int styleId) {
        Style style = adapterPresenter.getStyleById(styleId);

        GradientDrawable gradientDrawable = new GradientDrawable();

        gradientDrawable.setStroke(1, style.getBorderColor());

        gradientDrawable.setColor(style.getColor());

        gradientDrawable.setCornerRadius(style.getCornerRadius());

        textView.setTextColor(style.getTextColor());

        viewGroup.setBackground(gradientDrawable);
    }

    class ViewLinkHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.container_button) ViewGroup containerButton;
        @BindView(R.id.link_text_view) TextView linkTextView;
        @BindView(R.id.icon_image_view) ImageView iconImageView;

        public ViewLinkHolder(ViewGroup viewGroup) {
            super(viewGroup, R.layout.item_list_link);
        }

        void bind(ListItem.Link link) {
            linkTextView.setText(link.getText());

            loadIcon(iconImageView, link.getIcon());
            setStyle(containerButton, linkTextView, link.getStyleId());

            containerButton.setOnClickListener(v -> adapterPresenter.onLinkPressed(link.getLink()));
        }
    }

    class ViewSpoilerHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.container_spoiler) ViewGroup containerSpoiler;
        @BindView(R.id.container_button) ViewGroup containerButton;
        @BindView(R.id.icon_image_view) ImageView iconImageView;
        @BindView(R.id.spoiler_text_view) TextView spoilerTextView;
        @BindView(R.id.text_view) TextView textView;
        @BindView(R.id.image_view) ImageView imageView;

        public ViewSpoilerHolder(ViewGroup viewGroup) {
            super(viewGroup, R.layout.item_list_spoiler);
        }

        void bind(ListItem.Spoiler spoiler) {
            spoilerTextView.setText(spoiler.getButtonText());

            loadIcon(iconImageView, spoiler.getIcon());
            setStyle(containerButton, spoilerTextView, spoiler.getStyleId());

            containerButton.setOnClickListener(v -> {
                textView.setText(spoiler.getSpoilerText());

                Glide.with(getView().getContext())
                        .load(spoiler.getImage())
                        .into(imageView);

                adapterPresenter.onClick(spoiler);

                TransitionManager.beginDelayedTransition(containerSpoiler);
                textView.setVisibility(textView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                imageView.setVisibility(imageView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            });
        }
    }

    private class ToolbarHolder extends BaseRecyclerViewHolder {
        public ToolbarHolder(ViewGroup parent) {
            super(parent, R.layout.toolbar);
        }
    }
}
