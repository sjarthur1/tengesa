package com.presenter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.mobile.tengesa.R;

public class StartFragment {
    
    public static void startFragment(FragmentManager fragmentManager, String tag, Fragment fragment){
        if(fragmentManager.findFragmentByTag(tag) != null){
            fragmentManager.beginTransaction().replace(R.id.linear_layout_main, fragment).commit();
        }else{
            fragmentManager.beginTransaction().replace(R.id.linear_layout_main, fragment).addToBackStack(tag).commit();
        }
    }
    
    public static void startFragment(FragmentManager fragmentManager, Fragment fragment){
        fragmentManager.beginTransaction().replace(R.id.linear_layout_main, fragment).commit();
    }
}
