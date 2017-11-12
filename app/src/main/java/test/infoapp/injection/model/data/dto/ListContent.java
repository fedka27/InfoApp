package test.infoapp.injection.model.data.dto;

import java.util.List;

public class ListContent {
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
