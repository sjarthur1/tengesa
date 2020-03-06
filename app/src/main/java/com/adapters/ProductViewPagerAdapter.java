package com.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.mobile.tengesa.ProductImages;

import java.util.ArrayList;

public class ProductViewPagerAdapter extends FragmentStatePagerAdapter {
    
    private ArrayList<String> imageArray;
    private Fragment fragment;
    public ProductViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<String> imageArray) {
        super(fm);
        this.imageArray = imageArray;
    }
    
    @NonNull
    @Override
    public Fragment getItem(int position) {
        fragment = ProductImages.newInstance(imageArray.get(position));
        return fragment;
    }
    
    @Override
    public int getCount() {
        return imageArray.size();
    }
}
