package info.renamed.ui.base;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<String> titleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();

    public BaseFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    final public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    final public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    final public int getCount() {
        return titleList.size();
    }

    final protected void addTab(@NonNull String title, @NonNull Fragment fragment) {
        titleList.add(title);
        fragmentList.add(fragment);
    }

    final protected void setFragment(int position, Fragment fragment) {
        fragmentList.set(position, fragment);
    }
}
