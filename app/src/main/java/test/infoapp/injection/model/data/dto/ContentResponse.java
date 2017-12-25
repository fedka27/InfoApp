package test.infoapp.injection.model.data.dto;

import android.graphics.Color;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContentResponse extends ColorHelper {
    @SerializedName("list") List<ListItem> list;
    @SerializedName("image_bg") private String imageBg;
    @SerializedName("image_color") private String imageColor;

    public String getImageBg() {
        return imageBg;
    }

    public void setImageBg(String imageBg) {
        this.imageBg = imageBg;
    }

    public int getBgColor() {
        return parseColor(imageColor, Color.DKGRAY);
    }

    public List<ListItem> getList() {
        return list;
    }

    public void setList(List<ListItem> list) {
        this.list = list;
    }
}
