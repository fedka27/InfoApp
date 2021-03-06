package info.renamed.injection.model.data.dto;

import java.io.Serializable;
import java.util.List;

public class ListContent implements Serializable {
    private String imageBg;
    private int bgColor;
    private List<Item> items;

    public ListContent(String imageBg, int bgColor, List<Item> items) {
        this.imageBg = imageBg;
        this.bgColor = bgColor;
        this.items = items;
    }

    public int getBgColor() {
        return bgColor;
    }

    public String getImageBg() {
        return imageBg;
    }

    public List<Item> getItems() {
        return items;
    }
}
