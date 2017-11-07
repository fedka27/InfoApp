package test.infoapp.ui.list;

import android.support.transition.TransitionManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import test.infoapp.R;
import test.infoapp.injection.model.data.dto.ListItem;
import test.infoapp.ui.base.BaseRecyclerAdapter;
import test.infoapp.ui.base.BaseRecyclerViewHolder;
import test.infoapp.ui.base.RecyclerRow;


public class ListAdapter extends BaseRecyclerAdapter {

    private ListContract.AdapterPresenter adapterPresenter;

    public ListAdapter(ListContract.AdapterPresenter adapterPresenter) {
        this.adapterPresenter = adapterPresenter;
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

    public void setItems(List<ListItem> items) {
        clearList();
        itemList.addAll(items);
        notifyDataSetChanged();
    }

    class ViewLinkHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.link_button) Button linkButton;

        public ViewLinkHolder(ViewGroup viewGroup) {
            super(viewGroup, R.layout.item_list_link);
        }

        void bind(ListItem.Link link) {
            linkButton.setText(link.getText());

            linkButton.setOnClickListener(v -> {
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link.getLink()));
//                getView().getContext().startActivity(browserIntent);
                adapterPresenter.onLinkPressed(link.getLink());
            });
        }
    }

    class ViewSpoilerHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.container_spoiler) ViewGroup viewGroup;
        @BindView(R.id.spoiler_button) Button spoilerButton;
        @BindView(R.id.text_view) TextView textView;

        public ViewSpoilerHolder(ViewGroup viewGroup) {
            super(viewGroup, R.layout.item_list_spoiler);
        }

        void bind(ListItem.Spoiler spoiler) {
            spoilerButton.setText(spoiler.getButtonText());

            spoilerButton.setOnClickListener(v -> {
                textView.setText(spoiler.getSpoilerText());

                adapterPresenter.onClick(spoiler);

                TransitionManager.beginDelayedTransition(viewGroup);
                textView.setVisibility(textView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            });
        }
    }
}
