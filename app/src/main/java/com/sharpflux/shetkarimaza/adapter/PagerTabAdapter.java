package com.sharpflux.shetkarimaza.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sharpflux.shetkarimaza.fragment.LoginFragment;
import com.sharpflux.shetkarimaza.fragment.SignupFragment;

public class PagerTabAdapter extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                LoginFragment tab1 = new LoginFragment();
                return tab1;
            case 1:
                SignupFragment tab2 = new SignupFragment();
                return tab2;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
