package com.mobile.tengesa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.constants.ProjectConfiguration;
import com.fragments.FragmentHome;
import com.helpers.PreferenceManagement;
import com.network_layer.AccountServiceLayer;
import com.network_layer.callback.ReturnCountriesCallback;
import com.network_layer.callback.UserDetailsCallBack;
import com.objects.UserDetails;
import com.objects.list_objects.Country;

import java.util.List;

public class ActivitySplashScreen extends AppCompatActivity {
    
    private LinearLayout linearLayoutError;
    private ImageView imageViewLogo;
    private TextView textViewError, textViewReload;
    
    private Context context;
    private Handler handler;
    private String error, userId;
    public static ActivitySplashScreen thisInstance;
    
    public static ActivitySplashScreen getInstance(){
        return thisInstance;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        
        context = getApplicationContext();
        handler = new Handler();
        thisInstance = this;
        initializeView();
    }
    
    private void initializeView(){
        linearLayoutError = findViewById( R.id.linear_layout_error );
        imageViewLogo     = findViewById( R.id.image_view_logo );
        textViewError     = findViewById( R.id.text_view_error );
        textViewReload    = findViewById( R.id.text_view_reload );
    
        ProjectConfiguration.setLogo( imageViewLogo );
        textViewReload.setOnClickListener(onClickListener);
        if(error != null){
            linearLayoutError.setVisibility(View.VISIBLE);
            textViewError.setText(error);
        }
        MainActivity.getInstance().presenter.getUserDetails();
        //getUserDetails();
    }
    
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            linearLayoutError.setVisibility( View.GONE );
            if(FragmentHome.getInstance() != null)
                FragmentHome.getInstance().reload();
            MainActivity.getInstance().presenter.getUserDetails();
        }
    };
    
    public void setError( String error ){
        this.error = error;
        textViewError.setText( error );
        linearLayoutError.setVisibility( View.VISIBLE );
    }
    
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            textViewError.setText( error );
            linearLayoutError.setVisibility( View.VISIBLE );
        }
    };
    
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.getInstance().finish();
        finish();
    }
}
