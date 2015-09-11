package com.blackwhite.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.blackwhite.Fragments.SampleFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT =2;
    private String titles[] ;
    private String content1;
    private String content2;

    public ViewPagerAdapter(FragmentManager fm, String[] titles,String content1,String content2) {
        super(fm);
        this.titles=titles;
        this.content1 = content1;
        this.content2 = content2;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            // Open FragmentTab1.java
            case 0:
                return SampleFragment.newInstance(content1);
            case 1:
                return SampleFragment.newInstance(content2);
        }
        return null;
    }

    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}