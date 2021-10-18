package com.sharpflux.shetkarimaza.adapter;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

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
                SignupFragment tab2 = new SignupFragment();
                return tab2;
            case 1:
                LoginFragment tab1 = new LoginFragment();
                return tab1;
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
