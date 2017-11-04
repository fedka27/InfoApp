package test.infoapp.util;

import android.os.Build;
import android.support.v4.text.TextUtilsCompat;
import android.text.Html;
import android.text.Spanned;

public class HtmlSpannableUtil {

    public static Spanned getString(String string) {
        return getSpanned(string);
    }

    private static Spanned getSpanned(String text) {
        if (text == null) {
            return null;
        }
        text = TextUtilsCompat.htmlEncode(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }
}
