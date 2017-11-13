package test.infoapp.injection.model.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Spoiler implements Serializable {
    @SerializedName("button_text") private String buttonText;
    @SerializedName("spoiler_text") private String spoilerText;
    @SerializedName("icon") private String icon;
    @SerializedName("image") private String image;

    private Style style;

    public Spoiler(Spoiler spoiler, Style style) {
        this.buttonText = spoiler.getButtonText();
        this.spoilerText = spoiler.getSpoilerText();
        this.image = spoiler.getImage();
        this.icon = spoiler.getIcon();
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

    public String getIcon() {
        return icon;
    }

    public Style getStyle() {
        return style;
    }
}