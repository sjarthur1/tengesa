package com.mobile.access_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.constants.ProjectConfiguration;
import com.fragments.FragmentLogin;
import com.mobile.tengesa.R;
import com.presenter.StartFragment;

public class ActivityAccessControl extends AppCompatActivity {
    private Fragment fragment;
    private Bundle bundle;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_access_control );
    
        bundle = getIntent().getExtras();
        fragment = FragmentLogin.newInstance();
        if( bundle != null ){
            fragment.setArguments( bundle );
        }
        StartFragment.startFragment( getSupportFragmentManager(), fragment );
    }
    
}
