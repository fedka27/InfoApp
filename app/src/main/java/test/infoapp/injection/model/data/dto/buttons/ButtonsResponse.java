package test.infoapp.injection.model.data.dto.buttons;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import test.infoapp.injection.model.data.dto.Item;

public class ButtonsResponse {
    @SerializedName("buttons") private List<Item> buttons;

    public List<Item> getButtons() {
        return buttons;
    }
}
