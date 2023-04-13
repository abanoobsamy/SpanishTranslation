package com.example.spanishtranslation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {

        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0)
        {
            return new NumberFragment();
        }
        else if (position == 1)
        {
            return new FamilyFragment();
        }
        else if (position == 2)
        {
            return new ColorFragment();
        }
        else
        {
            return new PhrasesFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        if (position == 0)
        {
            return mContext.getString(R.string.number);
        }
        else if (position == 1)
        {
            return mContext.getString(R.string.family);
        }
        else if (position == 2)
        {
            return mContext.getString(R.string.color);
        }
        else
        {
            return mContext.getString(R.string.phrases);
        }
    }
}
