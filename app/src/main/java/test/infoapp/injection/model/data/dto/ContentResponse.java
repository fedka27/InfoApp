package test.infoapp.injection.model.data.dto;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContentResponse {
    @SerializedName("list") List<ListItem> list;
    @SerializedName("image_bg") private String imageBg;
    @SerializedName("image_color") private String imageColor;

    public String getImageBg() {
        return imageBg;
    }

    public void setImageBg(String imageBg) {
        this.imageBg = imageBg;
    }

    @Nullable
    public String getImageColor() {
        if (imageColor != null && !imageColor.contains("#")) {
            imageColor = "#" + imageColor;
        }
        return imageColor;
    }

    public List<ListItem> getList() {
        return list;
    }

    public void setList(List<ListItem> list) {
        this.list = list;
    }
}
