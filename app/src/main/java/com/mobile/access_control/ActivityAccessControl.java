package com.mobile.access_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.constants.ProjectConfiguration;
import com.fragments.FragmentLogin;
import com.mobile.tengesa.R;
import com.presenter.StartFragment;

public class ActivityAccessControl extends AppCompatActivity {
    private Fragment fragment;
    private Bundle bundle;
    private View view;
    private InputMethodManager inputMethodManager;
    private Context context;
    private static ActivityAccessControl activityAccessControl;
    
    public static ActivityAccessControl getInstance(){
        return activityAccessControl;
    }
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_access_control );
        
        context = getApplicationContext();
        activityAccessControl = this;
        bundle = getIntent().getExtras();
        
        inputMethodManager = (InputMethodManager) getSystemService( INPUT_METHOD_SERVICE );
        fragment = FragmentLogin.newInstance();
        if( bundle != null ){
            fragment.setArguments( bundle );
        }
        StartFragment.startFragment( getSupportFragmentManager(), fragment );
    }
    
    public void hideSoftKeyboard(View view){
        if(inputMethodManager != null)
            inputMethodManager .hideSoftInputFromWindow( view.getWindowToken(), 0 );
    }
    
    public void showSoftKeyboard(){
        view = getCurrentFocus();
        if( view == null )
            view = new View(context);
        inputMethodManager.showSoftInput( view, 0 );
    }
    
    
}
