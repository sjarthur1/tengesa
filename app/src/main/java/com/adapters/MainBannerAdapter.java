package com.adapters;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.mobile.tengesa.FirstBanner;

import java.util.ArrayList;
import java.util.List;

public class MainBannerAdapter extends FragmentStatePagerAdapter {
    private Fragment fragment;
    private List<String> imageArray;
    
    public MainBannerAdapter(FragmentManager fm,  List<String> imageArray) {
        super(fm);
        this.imageArray = imageArray;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Fragment getItem(int i) {
        fragment = FirstBanner.newInstance();
        return fragment;
    }
    
    @Override
    public int getCount() {
        return imageArray.size();
    }
}
