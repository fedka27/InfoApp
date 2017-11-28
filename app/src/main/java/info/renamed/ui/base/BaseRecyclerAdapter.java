package info.renamed.ui.base;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseRecyclerViewHolder> {
    protected List<Object> itemList = new ArrayList<>();
    protected RecyclerRow recyclerRow = new RecyclerRow();
    @Nullable
    private OnTopScrolledListener onTopScrolledListener;
    @Nullable
    private OnBottomScrolledListener onBottomScrolledListener;

    @Override
    final public int getItemViewType(int position) {
        return recyclerRow.getRow(itemList.get(position)).typeLayout();
    }

    @Override
    final public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return recyclerRow.getRow(viewType).viewHolder(parent);
    }

    @Override
    final public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
        Object item = itemList.get(position);
        recyclerRow.getRow(item).bind(holder, item);
    }

    public void setOnTopScrolledListener(@Nullable OnTopScrolledListener onTopScrolledListener) {
        this.onTopScrolledListener = onTopScrolledListener;
    }

    public void setOnBottomScrolledListener(@Nullable OnBottomScrolledListener onBottomScrolledListener) {
        this.onBottomScrolledListener = onBottomScrolledListener;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void registerLazyTop(RecyclerView recyclerView) {
        LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (lm.findFirstVisibleItemPosition() == 0) {
                    if (onTopScrolledListener != null) {
                        onTopScrolledListener.onTopScrolled();
                    }
                }
            }
        });
    }

    public void registerLazyBottom(RecyclerView recyclerView) {
        LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (lm.findLastVisibleItemPosition() == getItemCount() - 2) {
                    if (onBottomScrolledListener != null) {
                        onBottomScrolledListener.onBottomScrolled();
                    }
                }
            }
        });
    }

    public void clearList() {
        itemList.clear();
        notifyDataSetChanged();
    }

    public void setCollection(List<?> objects) {
        itemList.clear();
        itemList.addAll(objects);
        notifyDataSetChanged();
    }

    public interface OnTopScrolledListener {
        void onTopScrolled();
    }

    public interface OnBottomScrolledListener {
        void onBottomScrolled();
    }
}
