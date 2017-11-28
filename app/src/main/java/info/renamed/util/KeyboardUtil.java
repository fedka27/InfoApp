package info.renamed.util;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardUtil {

    public static void showKeyboard(@NonNull Context context, @NonNull View anchorView) {
        anchorView.requestFocus();

        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(anchorView, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void hideKeyboard(@NonNull Context context, @NonNull View anchorView) {
        IBinder windowToken = anchorView.getWindowToken();

        InputMethodManager inputMethodManager =
                (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void preventShowingKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
