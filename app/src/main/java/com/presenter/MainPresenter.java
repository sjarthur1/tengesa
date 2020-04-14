package com.presenter;

import android.content.Context;

import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.mobile.tengesa.ActivitySplashScreen;
import com.mobile.tengesa.MainActivity;
import com.network_layer.AccountServiceLayer;
import com.network_layer.CartServiceLayer;
import com.network_layer.callback.CartObjectCallback;
import com.network_layer.callback.OrderDataCallback;
import com.network_layer.callback.ReturnIntegerCallback;
import com.network_layer.callback.ReturnStringCallback;
import com.network_layer.callback.UserDetailsCallBack;
import com.objects.CartObject;
import com.objects.OrderData;
import com.objects.ProductOrder;
import com.objects.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter {
    
    private Context context;
    private MainView view;
    public List<CartObject> cartList;
    public OrderData orderData;
    private List<ProductOrder> productList;
    public String username;
    public UserDetails userDetails;
    
    public MainPresenter( Context context, MainView view ) {
        this.context = context;
        this.view = view;
        cartList = new ArrayList<>();
        productList = new ArrayList<>();
        username = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
    }
    
    public void setCartList(List<ProductOrder> productList){
        this.productList = productList;
    }
    
    public List<ProductOrder> getCartList(){
        return productList;
    }
    
    public void getTotalCart(){
        final ArrayList arrayList = new ArrayList();
        String productIdString = PreferenceManagement.readString( context, ProjectConfiguration.productId, null);
        if(productIdString != null) {
            productIdString = productIdString.trim();
            String[] cartItems = productIdString.split("#");
            for(int index = 0; index < cartItems.length; index++){
                arrayList.add(cartItems[index].trim());
            }
        }
        
        JSONArray jsArray = new JSONArray(arrayList);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put( ProjectConfiguration.username, username );
            jsonObject.put( ProjectConfiguration.localCartProducts, jsArray );
            CartServiceLayer.getCartNo( jsonObject, context, new ReturnIntegerCallback() {
                @Override
                public void onSuccess(int response) {
                    view.successful( response );
                }
    
                @Override
                public void onError(String error) {
        
                }
            } );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    public void clearFullCart(){
        final ArrayList arrayList = new ArrayList();
        
        JSONArray jsArray = new JSONArray(arrayList);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put( ProjectConfiguration.username, username );
            jsonObject.put( ProjectConfiguration.localCartProducts, jsArray );
            CartServiceLayer.getFullCart(jsonObject, context, new CartObjectCallback() {
                @Override
                public void onSuccess(List<CartObject> cartObject) {
                    for( CartObject object: cartObject ){
                        removeCartItem(object.getMongoId());
                    }
                    getTotalCart();
                }
                
                @Override
                public void onError(String error) {
                    view.failure( error, ErrorType.network );
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    public void removeCartItem( String mongoId ){
        
        CartServiceLayer.removeCart(mongoId, context, new ReturnStringCallback() {
            @Override
            public void onSuccess(String response) {
            
            }
            
            @Override
            public void onError(String error) {
            
            }
        });
    }
    
    public void getFullCart(){
        final ArrayList arrayList = new ArrayList();
        String productIdString = PreferenceManagement.readString( context, ProjectConfiguration.productId, null);
        if(productIdString != null) {
            productIdString = productIdString.trim();
            String[] cartItems = productIdString.split("#");
            for(int index = 0; index < cartItems.length; index++){
                arrayList.add(cartItems[index].trim());
            }
        }
        
        JSONArray jsArray = new JSONArray(arrayList);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put( ProjectConfiguration.username, username );
            jsonObject.put( ProjectConfiguration.localCartProducts, jsArray );
            CartServiceLayer.getFullCart(jsonObject, context, new CartObjectCallback() {
                @Override
                public void onSuccess(List<CartObject> cartObject) {
                    cartList = cartObject;
                    //getTotalAmount( arrayList );
                    List<ProductOrder> productList = new ArrayList<>();
                    for( CartObject cart : cartObject ){
                        double totalPrice = cart.getPrice() * cart.getQuantity();
                        productList.add( new ProductOrder( cart.getProductID(), cart.getSoldBy(), cart.getPrice(), cart.getQuantity(), totalPrice, cart.getShippingFee() ) );
                    }
                    
                    setCartList( productList );
                    getTotalAmount( arrayList );
                }
                
                @Override
                public void onError(String error) {
                    view.failure( error, ErrorType.network );
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    
    
    public void getUserDetails(){
        final String userId = PreferenceManagement.readString( context, ProjectConfiguration.userId, null );
        AccountServiceLayer.getUserDetails( userId, context, new UserDetailsCallBack() {
            @Override
            public void onSuccess(UserDetails userGeneralDetails) {
                userDetails = userGeneralDetails;
                /*MainActivity.getInstance().saveUserDetails( userDetails );
                view.successful(  );*/
                if(ActivitySplashScreen.getInstance() != null)
                    ActivitySplashScreen.getInstance().finish();
            }
            
            @Override
            public void onError(String error) {
                //view.failure(error, ErrorType.network);
                if( ActivitySplashScreen.getInstance() != null && !error.equalsIgnoreCase("Not Found") )
                    ActivitySplashScreen.getInstance().setError(error );
            }
        });
    }
    
    public void getTotalAmount(ArrayList<String> productList){
        String username = PreferenceManagement.readString( context, ProjectConfiguration.userId, null);
        JSONArray jsonArray = new JSONArray(productList);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProjectConfiguration.username, username);
            jsonObject.put(ProjectConfiguration.localCartProducts, jsonArray);
            CartServiceLayer.getTotalAmount(jsonObject, context, new OrderDataCallback() {
                @Override
                public void onSuccess(OrderData orderData1) {
                    orderData = orderData1;
                }
                
                @Override
                public void onError(String error) {
                
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    public interface MainView{
        void successful(int numberInCart);
        void failure( String message, ErrorType errorType );
    }
    
    public enum ErrorType{
        network
    }
}
