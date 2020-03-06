package com.mobile.tengesa;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.constants.ProjectConfiguration;
import com.fragments.FragmentAccount;
import com.fragments.FragmentCategories;
import com.fragments.FragmentHome;
import com.fragments.FragmentLogin;
import com.fragments.FragmentMyCart;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.helpers.PreferenceManagement;
import com.objects.OrderData;
import com.objects.ProductOrder;
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
    private Fragment fragment;
    private Context context;
    String username;
    
    private MainPresenter presenter;
    
    public static MainActivity getInstance(){
        return mainActivity;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        
        mainActivity = this;
        
        if(presenter == null )
            presenter = new MainPresenter(context, this);
        
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener( mOnNavigationItemSelectedListener );
        
        username = PreferenceManagement.readString( context, ProjectConfiguration.userId, null );
        
        initialiseBadge();
        
        fragment = FragmentHome.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.linear_layout_main, fragment).commit();
        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        getCart();
    }
    
    public void getCart(){
        presenter.getFullCart( username );
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
    
    private void selectAccountPage(){
        String userId = PreferenceManagement.readString( context, ProjectConfiguration.userId, null );
        String tag = "Account";
        if( userId == null ){
            tag = "Login";
            fragment = FragmentLogin.newInstance();
        }else{
            fragment = FragmentAccount.newInstance();
        }
        
        StartFragment.startFragment( getSupportFragmentManager(), tag, fragment );
    }
    
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch ( item.getItemId()) {
                case R.id.navigation_home:
                    fragment = FragmentHome.newInstance();
                    StartFragment.startFragment(getSupportFragmentManager(), "Home", fragment);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.linear_layout_main, fragment).commit();
                    getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                case R.id.navigation_cart:
                    fragment = FragmentMyCart.newInstance();
                    StartFragment.startFragment(getSupportFragmentManager(), "Cart", fragment);
                    //getSupportFragmentManager().beginTransaction().replace(R.id.linear_layout_main, fragment).commit();
                    return true;
                case R.id.navigation_categories:
                    fragment = FragmentCategories.newInstance();
                    StartFragment.startFragment(getSupportFragmentManager(), "Categories", fragment);
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
}
