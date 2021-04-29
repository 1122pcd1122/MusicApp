package activitytest.example.com.mymusic.ui.main.home.nestedFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class CollectionPagerAdapter extends FragmentStatePagerAdapter {


    private final Fragment[] fragments;
    private final String[] titles;

    public CollectionPagerAdapter(@NonNull FragmentManager fm, Fragment[] fragments, String[] titles) {
        super ( fm);
        this.fragments = fragments;
        this.titles = titles;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
