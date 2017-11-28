package info.renamed.ui.base;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.renamed.R;
import info.renamed.ui.base.interfaces.IKeyboardManageListener;
import info.renamed.ui.base.interfaces.IProgressListener;
import info.renamed.ui.base.interfaces.IThrowableListener;
import info.renamed.util.KeyboardUtil;
import info.renamed.util.NotificationUtil;

public abstract class BaseFragment extends Fragment implements IThrowableListener,
        IProgressListener,
        IKeyboardManageListener {
    protected NotificationUtil notificationUtil;
    @Nullable
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private FragmentManager fragmentManager;
    private Fragment progressFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        fragmentManager = getActivity().getSupportFragmentManager();
        initPresenter();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    protected void initPresenter() {
        //for childs
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        notificationUtil = new NotificationUtil(view.getContext(), view);
    }

    protected void replaceFragment(@IdRes int containerId,
                                   FragmentManager fragmentManager,
                                   BaseFragment fragment,
                                   boolean addToBackStack) {
        showFragment(containerId, fragmentManager, fragment, addToBackStack);
    }

    protected void replaceFragment(@IdRes int containerId,
                                   BaseFragment fragment,
                                   boolean addToBackStack) {
        showFragment(containerId, fragmentManager, fragment, addToBackStack);
    }


    protected void replaceFragment(@IdRes int containerId,
                                   FragmentManager fragmentManager,
                                   BaseFragment fragment) {
        showFragment(containerId, fragmentManager, fragment, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void showFragment(@IdRes int containerId,
                              FragmentManager fragmentManager,
                              BaseFragment fragment,
                              boolean addToBackStack) {
        try {
            ((ViewGroup) getActivity().findViewById(containerId)).removeAllViews();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(containerId, fragment, fragment.getClass().getSimpleName());
            if (addToBackStack) {
                fragmentTransaction.addToBackStack(null);
            }

            fragmentTransaction.setCustomAnimations(
                    getResourceIdFromCurrentThemeAttribute(getActivity(), android.R.attr.activityOpenEnterAnimation),
                    getResourceIdFromCurrentThemeAttribute(getActivity(), android.R.attr.activityOpenExitAnimation),
                    getResourceIdFromCurrentThemeAttribute(getActivity(), android.R.attr.activityCloseEnterAnimation),
                    getResourceIdFromCurrentThemeAttribute(getActivity(), android.R.attr.activityCloseExitAnimation)
            );

            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
            this.fragmentManager.executePendingTransactions();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getResourceIdFromCurrentThemeAttribute(FragmentActivity activity, int attribute) {
        TypedValue a = new TypedValue();
        activity.getTheme().resolveAttribute(attribute, a, false);
        return a.resourceId;
    }

    protected void setBackground(@DrawableRes int background) {
        getActivity().getWindow().setBackgroundDrawableResource(background);
    }

    @Override
    public void showProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void keyboardVisible(boolean isVisible) {
        if (isVisible) KeyboardUtil.showKeyboard(getContext(), getView());
        else KeyboardUtil.hideKeyboard(getContext(), getView());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void showError(String message) {
        notificationUtil.showError(message);
    }

    @Override
    public void showError(@StringRes int message) {
        notificationUtil.showError(message);
    }
}
