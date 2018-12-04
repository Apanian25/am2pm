package com.dawson.jonat.stockers.Hints;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dawson.jonat.stockers.Entity.Hint;

import java.util.List;

public class HintsFragmentPagerAdapter extends FragmentPagerAdapter{
    private final String TAG = "HintsPagerAdapter";
    private List<Hint> hintList;

    public HintsFragmentPagerAdapter(FragmentManager fm, List<Hint> hintList) {
        super(fm);
        this.hintList = hintList;

    }

    @Override
    public Fragment getItem(int position) {
        HintsFragment frag = null;

        if(position < this.hintList.size() && position >= 0){
            frag = new HintsFragment();
            frag.setHint(this.hintList.get(position));
        }else{
            throw new IllegalArgumentException("No matching fragment");
        }
        return frag;
    }

    @Override
    public int getCount() {
        return hintList.size();
    }
}
