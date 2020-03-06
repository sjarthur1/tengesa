package com.presenter;

import android.view.View;

public class FragmentHomePresenter {
    HomeInterface view;
    public FragmentHomePresenter(HomeInterface view){
        this.view = view;
    }
    
    public void onItemClicked(){
    
    }
    
    
    public interface HomeInterface{
        void updateFirstScroll();
        void updateBunner();
    }
}
