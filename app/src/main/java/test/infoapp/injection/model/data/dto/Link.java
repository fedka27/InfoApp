package test.infoapp.injection.model.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Link implements Serializable {
    @SerializedName("id") private long id;
    @SerializedName("text") private String text;
    @SerializedName("icon") private String icon;
    @SerializedName("link") private String link;
    private Style style;

    public Link(Link link, Style style) {
        this.id = link.getId();
        this.text = link.getText();
        this.icon = link.getIcon();
        this.link = link.getLink();
        this.style = style;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

    public String getLink() {
        return link;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }
}