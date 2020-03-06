package com.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adapters.OrdersAdapter;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.FullOrder;
import com.presenter.OrdersPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment shows orders
 */
public class FragmentOrders extends Fragment implements OrdersPresenter.OrdersView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentOrders fragment;
    private View view;
    private RecyclerView recyclerViewOrders;
    private ImageView imageViewBack;
    
    // TODO: Rename and change types of parameters
    private OrdersPresenter presenter;
    private Context context;
    ArrayList<FullOrder.ListUserOrderDetailsObject> orderList;
    private OrdersAdapter adapter;
    
    public FragmentOrders() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentOrders newInstance() {
        fragment = new FragmentOrders();
        return fragment;
    }
    
    public static FragmentOrders getInstance(){
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        
        context = getContext();
        initializeView();
        
        return view;
    }
    
    private void initializeView(){
        recyclerViewOrders = view.findViewById( R.id.recycler_view_orders );
        imageViewBack = view.findViewById( R.id.image_view_back );
        imageViewBack.setOnClickListener( onClickListener );
        
        presenter = new OrdersPresenter( context, this );
        
        orderList = new ArrayList<>();
        adapter = new OrdersAdapter( context, orderList );
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewOrders.setAdapter( adapter );
        
        presenter.getOrders();
    }
    
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
            }
        }
    };
    
    @Override
    public void successful(List<FullOrder> fullOrder) {
        orderList.clear();
        if(fullOrder != null){
            for(int orderIndex = 0; orderIndex < fullOrder.size(); orderIndex++){
                FullOrder order = fullOrder.get(orderIndex);
                ArrayList<FullOrder.ListUserOrderDetailsObject> orderItems = order.getListUserOrderDetails();
                orderList.addAll( orderItems );
            }
        }
        
        recyclerViewOrders.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
    
    @Override
    public void failure(String errorMessage) {
    
    }
}
