package test.infoapp.injection.model.data.dto;

import android.graphics.Color;

public class ColorHelper {

    protected int parseColor(String color, int defaultColor) {
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
