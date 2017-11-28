package info.renamed.injection.model.data.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class StylesResponse implements Serializable {
    @SerializedName("styles") private List<Style> styles;

    public List<Style> getStyles() {
        return styles == null ? Collections.emptyList() : styles;
    }
}
