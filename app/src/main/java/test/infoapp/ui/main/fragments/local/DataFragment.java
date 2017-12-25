package test.infoapp.ui.main.fragments.local;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.renamed.R;
import test.infoapp.ui.base.BaseFragment;


public class DataFragment extends BaseFragment {
    private static final String TAG = DataFragment.class.getSimpleName();
    private static final String EXTRA_BOOLEAN_LOCAL_DATA = TAG + "_BOOLEAN_LOCAL_DATA";

    public static Fragment newInstance(boolean isLocalData) {
        DataFragment dataFragment = new DataFragment();

        Bundle bundle = new Bundle();
        bundle.putBoolean(EXTRA_BOOLEAN_LOCAL_DATA, isLocalData);

        dataFragment.setArguments(bundle);

        return dataFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, container, false);
    }
}
