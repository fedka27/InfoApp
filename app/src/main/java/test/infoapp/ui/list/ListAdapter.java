package test.infoapp.ui.list;

import android.graphics.drawable.GradientDrawable;
import android.support.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yandex.metrica.YandexMetrica;

import java.util.List;

import butterknife.BindView;
import test.infoapp.R;
import test.infoapp.injection.model.data.dto.Item;
import test.infoapp.injection.model.data.dto.Link;
import test.infoapp.injection.model.data.dto.Spoiler;
import test.infoapp.injection.model.data.dto.Style;
import test.infoapp.ui.base.BaseRecyclerAdapter;
import test.infoapp.ui.base.BaseRecyclerViewHolder;
import test.infoapp.ui.base.RecyclerRow;
import test.infoapp.util.HtmlSpannableUtil;


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
                return item instanceof Item && ((Item) item).isLink();
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

                viewTextHolder.bind(((Item) item).getLink());
            }
        });
        recyclerRow.addRow(new RecyclerRow.Row() {

            @Override
            public boolean is(Object item) {
                return item instanceof Item && ((Item) item).isSpoiler();
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

                spoilerHolder.bind(((Item) item).getSpoiler());
            }
        });
    }

    private void addToolbarHolder() {
        itemList.add(null);
    }

    public void setItems(List<Item> items) {
        clearList();
        addToolbarHolder();
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    private void setStyle(ViewGroup viewGroup,
                          TextView textView,
                          String icon,
                          ImageView iconImageView,
                          Style style) {

        if (iconImageView != null) {
            Glide.with(iconImageView.getContext())
                    .load(icon)
                    .into(iconImageView);
        }

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

        void bind(Link link) {
            linkTextView.setText(HtmlSpannableUtil.getString(link.getText()));

            setStyle(containerButton, linkTextView, link.getIcon(), iconImageView, link.getStyle());

            containerButton.setOnClickListener(v -> adapterPresenter.onLinkPressed(link));
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

        void bind(Spoiler spoiler) {
            spoilerTextView.setText(HtmlSpannableUtil.getString(spoiler.getButtonText()));

            textView.setText(HtmlSpannableUtil.getString(spoiler.getSpoilerText()));

            setStyle(containerButton, spoilerTextView, spoiler.getIcon(), iconImageView, spoiler.getStyle());


            Glide.with(getView().getContext())
                    .load(spoiler.getImage())
                    .into(imageView);

            containerButton.setOnClickListener(v -> {

                YandexMetrica.reportEvent(getView().getContext()
                        .getString(R.string.metrica_event_click_button, spoiler.getButtonText()));

                adapterPresenter.onClick(spoiler);

                toggleSpoiler(spoiler);
            });

//            if (spoiler.isOpen()) {
//                openSpoiler(spoiler.isImage());
//            } else {
//                closeSpoiler(spoiler.isImage());
//            }
        }

        private void toggleSpoiler(Spoiler spoiler) {
            boolean isOpen = textView.getVisibility() != View.GONE;

            if (isOpen) {
                closeSpoiler(spoiler.isImage());
            } else {
                openSpoiler(spoiler.isImage());
            }
            isOpen = textView.getVisibility() != View.GONE;

            spoiler.setOpen(isOpen);
        }

        private void openSpoiler(boolean isImage) {
            TransitionManager.beginDelayedTransition(containerSpoiler);
            textView.setVisibility(View.VISIBLE);
            if (isImage) {
                imageView.setVisibility(View.VISIBLE);
            }
        }

        private void closeSpoiler(boolean isImage) {
            TransitionManager.beginDelayedTransition(containerSpoiler);
            textView.setVisibility(View.GONE);
            if (isImage) {
                imageView.setVisibility(View.GONE);
            }
        }
    }

    private class ToolbarHolder extends BaseRecyclerViewHolder {
        public ToolbarHolder(ViewGroup parent) {
            super(parent, R.layout.toolbar);
        }
    }
}
