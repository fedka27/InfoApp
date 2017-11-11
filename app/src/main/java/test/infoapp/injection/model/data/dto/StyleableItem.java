package test.infoapp.injection.model.data.dto;

import com.google.gson.annotations.SerializedName;

public class StyleableItem {
    @SerializedName("styleId") private int styleId;

    public int getStyleId() {
        return styleId;
    }
}
