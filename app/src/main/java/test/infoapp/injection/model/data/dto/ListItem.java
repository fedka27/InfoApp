package test.infoapp.injection.model.data.dto;

import com.google.gson.annotations.SerializedName;

public class ListItem {
    @SerializedName("buttonId") private long buttonId;
    @SerializedName("styleId") private long styleId;

    public long getButtonId() {
        return buttonId;
    }

    public long getStyleId() {
        return styleId;
    }
}
