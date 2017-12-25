package test.infoapp.util;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import info.renamed.R;

import static android.support.design.widget.Snackbar.LENGTH_INDEFINITE;
import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class NotificationUtil {
    private int DURATION_LONG = LENGTH_LONG;
    @NonNull
    private Context context;
    @NonNull
    private View view;
    private Snackbar snackbar;

    public NotificationUtil(@NonNull Context context,
                            @NonNull View view) {
        this.context = context;
        this.view = view;
    }

    @NonNull
    public View getView() {
        return view;
    }

    public void showError(@StringRes int res) {
        showError(context.getString(res));
    }

    public void showToast(int res) {
        showToast(context.getString(res));
    }

    public void showError(String message) {
        showSnackBarNotification(message, DURATION_LONG, R.color.error,
                null, null);
    }

    public void showError(String message, String action, View.OnClickListener onClickListener) {
        showSnackBarNotification(message, DURATION_LONG, R.color.error,
                action,
                onClickListener);
    }

    public void showProgress(@StringRes int res) {
        showSnackBarNotification(context.getString(res), LENGTH_INDEFINITE,
                R.color.processing, null, null);
    }

    public void showToast(String message) {
        showSnackBarNotification(message, LENGTH_LONG, R.color.toast,
                null, null);
    }

    private void showSnackBarNotification(String message, int duration, @ColorRes int color,
                                          @Nullable String action,
                                          @Nullable View.OnClickListener onClickListener) {
        snackbar = Snackbar.make(view, message, duration);

        snackbar.getView()
                .setBackgroundColor(ContextCompat.getColor(context, color));

        if (action != null && onClickListener != null) {
            snackbar.setAction(action, onClickListener);
        }

        snackbar.show();
    }
}
