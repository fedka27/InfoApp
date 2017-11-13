package test.infoapp.injection.model.data.dto.buttons;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import test.infoapp.injection.model.data.dto.Link;

public class ButtonsResponse {
    @SerializedName("buttons") private List<Link> buttons;

    public List<Link> getLinks() {
        return buttons;
    }
}
