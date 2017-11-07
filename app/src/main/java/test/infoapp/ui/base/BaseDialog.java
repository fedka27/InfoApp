package test.infoapp.ui.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import butterknife.ButterKnife;
import test.infoapp.R;

public abstract class BaseDialog extends Dialog {

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.StyleDialog);
        initView();
    }

    private void initView() {
        setContentView(initLayout());
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        if (getWindow() != null) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE &
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }

        ButterKnife.bind(this);
    }

    public abstract int initLayout();

    @Override
    protected void onStart() {
        super.onStart();
        onViewCreated();
    }

    protected abstract void onViewCreated();

}
