package test.infoapp.ui.list;

import android.content.Intent;
import android.net.Uri;
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

import static test.infoapp.injection.model.data.dto.ListItemType.LINK;
import static test.infoapp.injection.model.data.dto.ListItemType.TEXT;


public class ListAdapter extends BaseRecyclerAdapter {

    private ListContract.AdapterPresenter adapterPresenter;

    public ListAdapter(ListContract.AdapterPresenter adapterPresenter) {
        this.adapterPresenter = adapterPresenter;
        recyclerRow.addRow(new RecyclerRow.Row() {

            @Override
            public boolean is(Object item) {
                return item instanceof ListItem && ((ListItem) item).getType().equals(LINK);
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

                viewTextHolder.bind((ListItem) item);
            }
        });
        recyclerRow.addRow(new RecyclerRow.Row() {

            @Override
            public boolean is(Object item) {
                return item instanceof ListItem && ((ListItem) item).getType().equals(TEXT);
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

                spoilerHolder.bind((ListItem) item);
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

        void bind(ListItem listItem) {
            linkButton.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listItem.getLink()));
                getView().getContext().startActivity(browserIntent);
            });
        }
    }

    class ViewSpoilerHolder extends BaseRecyclerViewHolder {

        @BindView(R.id.spoiler_button) Button spoilerButton;
        @BindView(R.id.text_view) TextView textView;

        public ViewSpoilerHolder(ViewGroup viewGroup) {
            super(viewGroup, R.layout.item_list_spoiler);
        }

        void bind(ListItem listItem) {
            spoilerButton.setOnClickListener(v -> {
                adapterPresenter.onClick(listItem);

                textView.setVisibility(textView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            });
        }
    }
}
