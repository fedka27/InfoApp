package info.renamed.ui.base;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class RecyclerRow {
    private List<Row> rowList = new ArrayList<>();

    public void addRow(Row row) {
        rowList.add(row);
    }

    public void removeRow(Row row) {
        rowList.remove(row);
    }

    public int size() {
        return rowList.size();
    }

    public Row getRow(Object item) {
        for (Row row : rowList) {
            if (row.is(item)) {
                return row;
            }
        }
        throw new RuntimeException("There is no such item type - " + item);
    }

    public Row getRow(int viewType) {
        for (Row row : rowList) {
            if (row.typeLayout() == viewType) {
                return row;
            }
        }
        throw new RuntimeException("There is no such item type - " + viewType);
    }

    public interface Row {

        boolean is(Object item);

        int typeLayout();

        BaseRecyclerViewHolder viewHolder(ViewGroup parent);

        void bind(BaseRecyclerViewHolder holder, Object item);
    }
}