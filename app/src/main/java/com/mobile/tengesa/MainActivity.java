package com.mobile.tengesa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.constants.ProjectConfiguration;
import com.fragments.FragmentAccount;
import com.fragments.FragmentCategories;
import com.fragments.FragmentHome;
import com.fragments.FragmentLogin;
import com.fragments.FragmentMyCart;
import com.fragments.FragmentSelectAddress;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.helpers.PreferenceManagement;
import com.mobile.access_control.ActivityAccessControl;
import com.objects.OrderData;
import com.objects.ProductOrder;
import com.objects.UserDetails;
import com.presenter.MainPresenter;
import com.presenter.StartFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainPresenter.MainView {
    private static MainActivity mainActivity;
    private TextView mTextMessage, notificationCount;
    private BottomNavigationView navView;
    private BottomNavigationMenuView navigationMenuView;
    private BottomNavigationItemView navigationItemView;
    private View notificationBadge;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private Context context;
    private InputMethodManager inputMethodManager;
    private View view;
    public Bundle bundle;
    String username, page;
    
    public MainPresenter presenter;
    
    public static MainActivity getInstance(){
        return mainActivity;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        
        mainActivity = this;
        fragmentManager = getSupportFragmentManager();
        
        if(presenter == null )
            presenter = new MainPresenter(context, this);
        
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener );
    
        if(ActivityAccessControl.getInstance() == null ) {
            Intent intent = new Intent(context, ActivitySplashScreen.class);
            startActivity(intent);
        }
    
        bundle = getIntent().getExtras();
        if(bundle != null)
            page = bundle.getString( ProjectConfiguration.PAGE, null );
        if( page == null ) {
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        fragmentManager.beginTransaction().replace(R.id.linear_layout_main, FragmentHome.newInstance()).commit();
        
        initialiseBadge();
        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        //If there is no bundle sent with in the activity then show home activity
    
        if( bundle != null ){
            page = bundle.getString( ProjectConfiguration.PAGE, null );
            username = PreferenceManagement.readString( context, ProjectConfiguration.userId, null );
            if( username != null && page != null && page.equals( ProjectConfiguration.page_select_address ) ) {
                StartFragment.startFragment( getSupportFragmentManager(), ProjectConfiguration.page_select_address, FragmentSelectAddress.newInstance() );
            } else if( username != null && page != null && page.equals( ProjectConfiguration.page_account ) ) {
                StartFragment.startFragment( getSupportFragmentManager(), ProjectConfiguration.page_account, FragmentAccount.newInstance() );
            }
        }
        
        getCart();
        
        presenter.getFullCart();
        presenter.getUserDetails();
        //bundle.clear();
    }
    
    public void clearBundle(){
        if( bundle != null )
            bundle.clear();
        
        if( getIntent().getExtras() != null )
            getIntent().getExtras().clear();
        
        page = null;
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
    
    public void getCart(){
        presenter.getTotalCart();
    }
    
    public void clearCart(){
        presenter.clearFullCart();
        
    }
    
    public void reloadCart(){
        presenter.getFullCart();
    }
    
    private void initialiseBadge(){
        navigationMenuView = (BottomNavigationMenuView) navView.getChildAt( 0 );
        View view = navigationMenuView.getChildAt( 1 );
        navigationItemView = (BottomNavigationItemView) view;
        notificationBadge  = LayoutInflater.from( context ).inflate( R.layout.cart_badge, navView, false );
        notificationCount  = notificationBadge.findViewById( R.id.notification_badge );
        navigationItemView.addView( notificationBadge );
        
        countCartItems( 0 );
    }
    
    public void countCartItems(final int count ){
        if( notificationCount != null ){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notificationCount.setText( count+"" );
                    notificationCount.setVisibility( View.VISIBLE );
                }
            });
        }
    }
    
    public void saveCartItems(List<ProductOrder> productList){
        presenter.setCartList( productList );
    }
    
    public List<ProductOrder> getCartList(){
        return presenter.getCartList();
    }
    
    public void saveOrderData(OrderData orderData){
        presenter.orderData = orderData;
    }
    
    public OrderData getOrderData(){
        return presenter.orderData;
    }
    
    /*public void refreshOrderData(){
        presenter.getFullCart();
    }*/
    
    private void selectAccountPage(){
        String userId = PreferenceManagement.readString( context, ProjectConfiguration.userId, null );
        if( userId == null ){
            //fragment = FragmentLogin.newInstance();
            Intent intent = new Intent( context, ActivityAccessControl.class );
            startActivity( intent );
        }else{
            fragment = FragmentAccount.newInstance();
            StartFragment.startFragment( getSupportFragmentManager(), ProjectConfiguration.page_account, fragment );
        }
        
    }
    
    public void saveUserDetails(UserDetails userDetails){
        presenter.userDetails = userDetails;
    }
    
    public UserDetails getUserDetails(){
        return presenter.userDetails;
    }
    
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch ( item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fragment = FragmentHome.newInstance();
                    StartFragment.startFragment(getSupportFragmentManager(), fragment);
                    return true;
                case R.id.navigation_cart:
                    fragment = FragmentMyCart.newInstance();
                    StartFragment.startFragment(getSupportFragmentManager(), ProjectConfiguration.page_my_cart, fragment);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.linear_layout_main, fragment).commit();
                    return true;
                case R.id.navigation_categories:
                    fragment = FragmentCategories.newInstance();
                    StartFragment.startFragment(getSupportFragmentManager(), ProjectConfiguration.page_categories, fragment);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.linear_layout_main, fragment).commit();
                    return true;
                case R.id.navigation_my_account:
                    selectAccountPage();
                    //getSupportFragmentManager().beginTransaction().replace(R.id.linear_layout_main, fragment).commit();
                    return true;
                default:
                    fragment = FragmentHome.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.linear_layout_main, fragment).commit();
                    return true;
            }
        }
    };
    
    @Override
    public void successful(int numberInCart) {
        countCartItems( numberInCart );
    }
    
    @Override
    public void failure(String message, MainPresenter.ErrorType errorType) {
        Toast.makeText( context, message+"", Toast.LENGTH_LONG ).show();
    }
}
