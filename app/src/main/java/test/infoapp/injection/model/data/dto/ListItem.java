package test.infoapp.injection.model.data.dto;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ListItem {
    @SerializedName("spoiler") @Nullable private Spoiler spoiler;
    @SerializedName("buttonId") private @Nullable Long buttonId;
    @SerializedName("styleId") private @Nullable Long styleId;

    public long getButtonId() {
        return buttonId == null ? -1 : buttonId;
    }

    public long getStyleId() {
        return styleId == null ? -1 : styleId;
    }

    public boolean isSpoiler() {
        return spoiler != null && buttonId == null;
    }

    public Spoiler getSpoiler() {
        return spoiler;
    }
}
