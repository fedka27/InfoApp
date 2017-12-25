package test.infoapp.ui.base.interfaces;

import android.support.annotation.StringRes;

public interface IThrowableListener {
    void showError(String message);

    void showError(@StringRes int message);
}
