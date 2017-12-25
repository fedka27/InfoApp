package test.infoapp.ui.base;


import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {

    public BaseRecyclerViewHolder(ViewGroup viewGroup, @LayoutRes int layoutIdRes) {
        super(LayoutInflater.from(viewGroup.getContext()).inflate(layoutIdRes, viewGroup, false));
        ButterKnife.bind(this, getView());
    }

    public View getView() {
        return itemView;
    }
}

