package com.app.infideap.whatsappsearchtoolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by sks on 11/3/18.
 */

public class SectionsPagerAdapter1 extends FragmentPagerAdapter {

    public SectionsPagerAdapter1(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                TrendingFragment  trendingFragment = new TrendingFragment();
                return trendingFragment;
            case 1:
                ExploreFragment exploreFragment = new ExploreFragment();
                return exploreFragment;
            case 2:
                NearbyFragment nearbyFragment = new NearbyFragment();
                return nearbyFragment;
            case 3:
                WishlistFragment wishlistFragment = new WishlistFragment();
                return wishlistFragment;
        }
        return null;

    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Trending";
            case 1:
                return "Explore";
            case 2:
                return "Nearby";
            case 3:
                return "Wishlist";
        }
        return null;
    }
}
