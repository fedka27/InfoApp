package test.infoapp.data.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import static test.infoapp.data.model.ListItemType.TEXT;

public class ListItem {
    @SerializedName("type") private String type;
    @SerializedName("link") @Nullable private String link;
    @SerializedName("text") private String text;

    public ListItem(@ListItemType String type, @Nullable String link, String text) {
        this.type = type;
        this.link = link;
        this.text = text;
    }


    @ListItemType
    public String getType() {
        return type == null ? ListItemType.LINK :
                type.equals(TEXT) ? TEXT : ListItemType.LINK;
    }

    @Nullable
    public String getLink() {
        return link;
    }

    public String getText() {
        return text;
    }

}
