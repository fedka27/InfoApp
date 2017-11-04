package test.infoapp.injection.model.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListItem {
    @SerializedName("spoiler") private Spoiler spoiler;
    @SerializedName("link") private Link link;


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

    public class Spoiler implements Serializable {
        @SerializedName("button_text") private String buttonText;
        @SerializedName("spoiler_text") private String spoilerText;
        @SerializedName("image") private String image;

        public String getButtonText() {
            return buttonText;
        }

        public String getSpoilerText() {
            return spoilerText;
        }

        public String getImage() {
            return image;
        }
    }

    public class Link implements Serializable {
        @SerializedName("link") private String link;
        @SerializedName("text") private String text;

        public String getLink() {
            return link;
        }

        public String getText() {
            return text;
        }
    }
}
