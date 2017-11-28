package info.renamed.injection.model.data.dto;

import android.graphics.Color;

import java.io.Serializable;

public class ColorHelper implements Serializable {

    public static int parseColor(String color, int defaultColor) {
        if (color == null) return defaultColor;
        if (!color.startsWith("#")) color = "#" + color;
        try {
            return Color.parseColor(color);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return defaultColor;
        }
    }
}
