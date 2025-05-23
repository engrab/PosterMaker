package ab.cd.ef.postermaker.adapter;


import android.content.Context;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import ab.cd.ef.postermaker.R;
import ab.cd.ef.postermaker.main.FragmentDefault;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private String[] TITLES;
    private Context _context;
    String[] cateName = new String[]{"MY_TEMP", "FREE_TEMP", "FRIDAY_TEMP"};
    ArrayList<Fragment> fragments;

    public ViewPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this._context = context;
        this.fragments = new ArrayList();
        FragmentDefault bakgrndFragment = new FragmentDefault();
        this.TITLES = new String[]{context.getResources().getString(R.string.temp1), context.getResources().getString(R.string.temp2), context.getResources().getString(R.string.temp3)};
        for (int i = 0; i < this.TITLES.length; i++) {
            this.fragments.add(bakgrndFragment);
        }
    }

    public Fragment getItem(int position) {
        String categoryName = this.cateName[position];
        FragmentDefault bakgrndFragment = new FragmentDefault();
        Bundle bundle = new Bundle();
        bundle.putString("categoryName", categoryName);
        bakgrndFragment.setArguments(bundle);
        this.fragments.set(position, bakgrndFragment);
        return bakgrndFragment;
    }

    public CharSequence getPageTitle(int position) {
        return this.TITLES[position];
    }

    public int getCount() {
        return this.TITLES.length;
    }

    public Fragment currentFragment(int position) {
        return (Fragment) this.fragments.get(position);
    }
}
