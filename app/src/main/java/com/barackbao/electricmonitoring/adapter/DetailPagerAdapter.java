package com.barackbao.electricmonitoring.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.barackbao.electricmonitoring.R;
import com.barackbao.electricmonitoring.fragment.DetailFragment;


/**
 * Created by Wangtianrui on 2018/5/1.
 */

public class DetailPagerAdapter extends FragmentPagerAdapter {
    private final String[] TITLES;
    private DetailFragment[] fragments;

    public DetailPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.sections);
        fragments = new DetailFragment[TITLES.length];
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = DetailFragment.newInstance();
                    break;
                case 1:
                    fragments[position] = DetailFragment.newInstance();
                    break;
                case 2:
                    fragments[position] = DetailFragment.newInstance();
                    break;
                case 3:
                    fragments[position] = DetailFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }

    public void changePage(int position){
        fragments[position].changePage();
    }
}
