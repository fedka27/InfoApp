package test.infoapp.injection.model.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Item {
    @SerializedName("id") private long id;
    @SerializedName("spoiler") private Spoiler spoiler;
    @SerializedName("link") private Link link;

    public Item(Spoiler spoiler) {
        this.spoiler = spoiler;
    }

    public Item(Link link) {
        this.link = link;
    }

    public long getId() {
        return id;
    }

    public boolean isSpoiler() {
        return spoiler != null;
    }

    public boolean isLink() {
        return link != null;
    }

    public Spoiler getSpoiler() {
        return spoiler;
    }

    public Link getLink() {
        return link;
    }

    public Object getItem() {
        if (isLink()) return getLink();
        else if (isSpoiler()) return getSpoiler();
        else return null;
    }

    public static class Spoiler implements Serializable {
        @SerializedName("button_text") private String buttonText;
        @SerializedName("spoiler_text") private String spoilerText;
        @SerializedName("image") private String image;
        private Style style;

        public Spoiler(Spoiler spoiler, Style style) {
            this.buttonText = spoiler.getButtonText();
            this.spoilerText = spoiler.getSpoilerText();
            this.image = spoiler.getImage();
            this.style = style;
        }

        public String getButtonText() {
            return buttonText;
        }

        public String getSpoilerText() {
            return spoilerText;
        }

        public String getImage() {
            return image;
        }

        public Style getStyle() {
            return style;
        }
    }

    public static class Link implements Serializable {
        @SerializedName("link") private String link;
        @SerializedName("text") private String text;
        private Style style;

        public Link(Link link, Style style) {
            this.link = link.getLink();
            this.text = link.getText();
            this.style = style;
        }

        public String getLink() {
            return link;
        }

        public String getText() {
            return text;
        }

        public Style getStyle() {
            return style;
        }
    }
}
