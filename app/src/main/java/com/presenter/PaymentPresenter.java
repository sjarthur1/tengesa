package com.presenter;

import android.content.Context;
import android.util.Log;

import com.constants.ProjectConfiguration;
import com.google.gson.Gson;
import com.helpers.PreferenceManagement;
import com.mobile.tengesa.MainActivity;
import com.network_layer.CartServiceLayer;
import com.network_layer.ProductServiceLayer;
import com.network_layer.ReturnDoubleCallback;
import com.network_layer.callback.ReturnOrderCallback;
import com.network_layer.callback.ReturnOrderFullCallback;
import com.objects.FullOrder;
import com.objects.OrderData;
import com.objects.Orders;
import com.objects.ProductOrder;
import com.objects.UserAddresses;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PaymentPresenter {
    private Context context;
    private PaymentView view;
    private UserAddresses address;
    private OrderData orderData;
    private List<ProductOrder> productOrderList;
    private double additionalShippingCost = 0.0;
    private String username;
    
    public PaymentPresenter( Context context, PaymentView view ) {
        this.context = context;
        this.view = view;
        username = PreferenceManagement.readString( context, ProjectConfiguration.userId, null );
    }
    
    public void setOrderAddress( UserAddresses address ){
        this.address = address;
    }
    
    public void setTotalAmount( OrderData orderData ){
        this.orderData = orderData;
    }
    
    public void setProductList( List<ProductOrder> productOrderList ){
        this.productOrderList = productOrderList;
    }
    
    public void getAdditionalShippingFee( String city ){
        CartServiceLayer.getAdditionalShippingFee(city, new ReturnDoubleCallback() {
            @Override
            public void successful( double amount ) {
                additionalShippingCost = amount;
                view.successful( amount );
            }
            
            @Override
            public void failure( String message ) {
                view.failure( message, ErrorType.feeError );
            }
        });
    }
    
    public void pressOrder(){
        String orderListString = new Gson().toJson( productOrderList );
        try {
            JSONArray jsonArray = new JSONArray( orderListString );
            JSONObject jsonObject = new JSONObject();
            jsonObject.put( ProjectConfiguration._id, null );
            jsonObject.put( ProjectConfiguration.orderId, null );
            jsonObject.put( ProjectConfiguration.OrderUserID, username );
            jsonObject.put( ProjectConfiguration.orderAmount, orderData.getTotalAmount() );
            jsonObject.put( ProjectConfiguration.OrderAddressID, address.getAddressID() );
            jsonObject.put( ProjectConfiguration.AdditionalShippingCost, additionalShippingCost );
            jsonObject.put( ProjectConfiguration.OrderDateTime, null );
            jsonObject.put( ProjectConfiguration.OrderDetailsList, jsonArray );
            Log.d( "Char", "Here");
            ProductServiceLayer.placeOrder(jsonObject, new ReturnOrderCallback() {
                @Override
                public void successful(Orders orders) {
                    view.successful( orders );
                }
    
                @Override
                public void failure(String errorMessage) {
                    view.failure( errorMessage, ErrorType.orderError );
                    
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    public void getOrders(){
        ProductServiceLayer.getOrder(username, new ReturnOrderFullCallback() {
            @Override
            public void successful( List<FullOrder> fullOrder ) {
                Log.d("Here", "Here" );
                
            }
    
            @Override
            public void failure( String errorMessage ) {
                Log.d("Here", "Here" );
                
            }
        });
    }
    
    public interface PaymentView{
        void successful( double amount );
        void successful( Orders orders );
        void failure( String message, ErrorType errorType );
    }
    
    public enum ErrorType{
        feeError, orderError
    }
}
