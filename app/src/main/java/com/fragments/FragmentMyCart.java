package com.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.adapters.HorizontalListItemAdapter;
import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.mobile.access_control.ActivityAccessControl;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.CartObject;
import com.objects.OrderData;
import com.objects.ProductOrder;
import com.presenter.MyCartPresenter;
import com.presenter.StartFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentMyCart#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentMyCart extends Fragment implements MyCartPresenter.MyCartView {
    
    private static FragmentMyCart fragment;
    private View view;
    private Fragment newFragment;
    
    private RecyclerView recyclerViewCart;
    private TextView textViewTotalPrice;
    private Button buttonCheckOut;
    private SwipeRefreshLayout swipeRefreshLayout;
    
    public MyCartPresenter presenter;
    private List<CartObject> cartList;
    private HorizontalListItemAdapter adapter;
    private String userId;
    private boolean refreshing = false;
    private Context context;
    int totalAmount;
    
    
    public FragmentMyCart() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentMyCart.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentMyCart newInstance() {
        fragment = new FragmentMyCart();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        
        context = getContext();
        textViewTotalPrice = view.findViewById( R.id.text_view_total_price );
        recyclerViewCart = view.findViewById( R.id.recycler_view_cart );
        buttonCheckOut = view.findViewById( R.id.button_check_out );
        swipeRefreshLayout = view.findViewById( R.id.swipe_refresh_layout );
        
        if(presenter == null)
            presenter = new MyCartPresenter( context,this );
        
        buttonCheckOut.setOnClickListener( clickListener );
        swipeRefreshLayout.setOnRefreshListener( refreshListener );
        
        cartList = presenter.cartList;
        adapter = new HorizontalListItemAdapter(context, cartList, presenter, getFragmentManager());
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewCart.setAdapter( adapter );
        
        userId = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
        //if(userId != null) {
        swipeRefreshLayout.setRefreshing( true );
        refreshing = swipeRefreshLayout.isRefreshing();
        presenter.getFullCart( userId );
        //}
        
        return view;
    }
    
    SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(!refreshing){
                swipeRefreshLayout.setRefreshing( true );
                refreshing = swipeRefreshLayout.isRefreshing();
                presenter.getFullCart(userId);
            }
        }
    };
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch ( view.getId() ) {
                case R.id.button_check_out:
                    if (userId != null) {
                        newFragment = FragmentSelectAddress.newInstance();
                        StartFragment.startFragment(getFragmentManager(), newFragment);
                    } else {
                        /*newFragment = FragmentLogin.newInstance();
                        Bundle bundle = new Bundle();
                        bundle.putString(ProjectConfiguration.PAGE, ProjectConfiguration.ADDRESS);
                        newFragment.setArguments(bundle);
                        StartFragment.startFragment(getFragmentManager(), "Login", newFragment);*/
                        Intent intent = new Intent( context, ActivityAccessControl.class );
                        intent.putExtra( ProjectConfiguration.PAGE, ProjectConfiguration.page_select_address );
                        startActivity( intent );
                    }
                    break;
            }
        }
    };
    
    @Override
    public void successful(List<CartObject> cartObject) {
        refreshing = false;
        cartList.clear();
        swipeRefreshLayout.setRefreshing(false);
        if(cartObject != null) {
            cartList.addAll(cartObject);
        }
        recyclerViewCart.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
        List<ProductOrder> productList = new ArrayList<>();
        for( int index = 0; index < cartObject.size(); index++ ){
            CartObject cart = cartObject.get( index );
            double totalPrice = cart.getPrice() * cart.getQuantity();
            productList.add( new ProductOrder( cart.getProductID(), cart.getSoldBy(), cart.getPrice(), cart.getQuantity(), totalPrice, cart.getShippingAmount() ) );
        }
    
        MainActivity.getInstance().saveCartItems( productList );
    }
    
    @Override
    public void successful() {
        refreshing = false;
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(context, "Product has been added to wish-list.", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void successful(int position) {
        refreshing = false;
        swipeRefreshLayout.setRefreshing(false);
        try {
            cartList.remove(position);
            recyclerViewCart.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }catch (Exception exc){
            exc.printStackTrace();
        }
    }
    
    @Override
    public void failed(String message) {
        swipeRefreshLayout.setRefreshing(false);
    
    }
    
    @Override
    public void calculateTotal(int position, int quantity) {
        totalAmount = 0;
        for(int count = 0; count < cartList.size(); count++) {
            double total = cartList.get(count).getPrice() * cartList.get(count).getQuantity();
            totalAmount += total;
        }
        textViewTotalPrice.setText(totalAmount+"");
    }
    
    @Override
    public void showOrderTotals(OrderData orderData) {
        textViewTotalPrice.setText( orderData.getTotalAmount()+"" );
        MainActivity.getInstance().saveOrderData( orderData );
    }
}
