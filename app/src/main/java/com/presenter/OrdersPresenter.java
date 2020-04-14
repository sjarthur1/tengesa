package com.presenter;

import android.content.Context;
import android.util.Log;

import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.network_layer.ProductServiceLayer;
import com.network_layer.callback.ReturnOrderFullCallback;
import com.objects.FullOrder;
import com.objects.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrdersPresenter {
    private Context context;
    private OrdersView view;
    private String username;
    
    public OrdersPresenter(Context context, OrdersView view) {
        this.context = context;
        this.view = view;
        username = PreferenceManagement.readString( context, ProjectConfiguration.userId, null );
    }
    
    public void getOrders(){
        ProductServiceLayer.getOrder(username, context, new ReturnOrderFullCallback() {
            @Override
            public void successful( List<FullOrder> fullOrder ) {
                view.successful( fullOrder );
            }
            
            @Override
            public void failure( String errorMessage ) {
                view.failure( errorMessage );
            }
        });
    }
    
    public interface OrdersView{
        void successful(List<FullOrder> fullOrder);
        void failure( String errorMessage );
    }
}
