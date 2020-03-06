package com.presenter;

import android.content.Context;
import com.constants.ProjectConfiguration;
import com.helpers.ManageLocalProductIds;
import com.helpers.PreferenceManagement;
import com.network_layer.CartServiceLayer;
import com.network_layer.WishListServiceLayer;
import com.network_layer.callback.CartObjectCallback;
import com.network_layer.callback.OrderDataCallback;
import com.network_layer.callback.ReturnBooleanCallback;
import com.network_layer.callback.ReturnStringCallback;
import com.objects.CartObject;
import com.objects.OrderData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyCartPresenter {
    
    private MyCartView view;
    private Context context;
    public List<CartObject> cartList;
    public MyCartPresenter(Context context, MyCartView view){
        this.context = context;
        this.view = view;
        cartList = new ArrayList<>();
    }
    
    public void getFullCart(String username){
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
            CartServiceLayer.getFullCart(jsonObject, new CartObjectCallback() {
                @Override
                public void onSuccess(List<CartObject> cartObject) {
                    cartList = cartObject;
                    view.successful(cartObject);
                    getTotalAmount( arrayList );
                }
        
                @Override
                public void onError(String error) {
                    view.failed( error );
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    public void removeCartItem(final String productId, String mongoId, final int position){
        final ArrayList arrayList = new ArrayList();
        final String username = PreferenceManagement.readString( context, ProjectConfiguration.userId, null);
        final String[] productIdString = {PreferenceManagement.readString(context, ProjectConfiguration.productId, null)};
        if(productIdString[0] != null) {
            productIdString[0] = productIdString[0].trim();
            String[] cartItems = productIdString[0].split("#");
            for(int index = 0; index < cartItems.length; index++){
                arrayList.add(cartItems[index].trim());
            }
        }
        CartServiceLayer.removeCart(mongoId, new ReturnStringCallback() {
            @Override
            public void onSuccess(String response) {
                view.successful( position );
                getFullCart( username );
                getTotalAmount( arrayList );
            }
    
            @Override
            public void onError(String error) {
                if(arrayList.size() > 0){
                    PreferenceManagement.RemoveItem( context, ProjectConfiguration.productId );
                    for(int index = 0; index < arrayList.size(); index++){
                        String productIdAndQuantity = arrayList.get(index).toString().trim();
                        String storedProductId =productIdAndQuantity.substring(0, productIdAndQuantity.indexOf("|"));
                        if(storedProductId.equals( productId)){
                            view.successful( position );
                            arrayList.remove(index );
                        }else{
                            ManageLocalProductIds.editProductIdList(context, productIdAndQuantity);
                        }
                    }
                    getFullCart( username );
                    getTotalAmount( arrayList );
                }
                //view.failed( error );
            }
        });
    }
    
    public void updateCart(final int position, final int quantity, String mongoId){
        CartServiceLayer.updateCart(mongoId, quantity, new ReturnBooleanCallback() {
            @Override
            public void onSuccess(Boolean response) {
                if(response == true){
                    final ArrayList arrayList = new ArrayList();
                    String productIdString = PreferenceManagement.readString( context, ProjectConfiguration.productId, null);
                    if(productIdString != null) {
                        productIdString = productIdString.trim();
                        String[] cartItems = productIdString.split("#");
                        for(int index = 0; index < cartItems.length; index++){
                            arrayList.add(cartItems[index].trim());
                        }
                    }
                    getTotalAmount(arrayList);
                }else{
                    replaceArray( position, quantity );
                }
            }
    
            @Override
            public void onError(String error) {
        
            }
        });
    }
    
    public void saveToWishList( String productId ){
        String username = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
        
        WishListServiceLayer.saveWishList(username, productId, new ReturnStringCallback() {
            @Override
            public void onSuccess(String response) {
                view.successful();
            }
    
            @Override
            public void onError(String error) {
                view.failed( error );
            }
        });
    }
    
    public void replaceArray(int position, int quantity){
        
        if(position < cartList.size())
            cartList.get( position ).setQuantity( quantity );
        ArrayList arrayList = new ArrayList();
        String productIdString = PreferenceManagement.readString( context, ProjectConfiguration.productId, null);
        if(productIdString != null) {
            productIdString = productIdString.trim();
            String[] cartItems = productIdString.split("#");
            for(int index = 0; index < cartItems.length; index++){
                String prodId = cartItems[index].trim();
                prodId = prodId.substring(0, prodId.indexOf("|"));
                arrayList.add(prodId);
            }
        }
        
        //view.calculateTotal(position, quantity);
        PreferenceManagement.RemoveItem( context, ProjectConfiguration.productId );
        ArrayList<String> productList = new ArrayList<>();
        for(int index = 0; index < cartList.size(); index++){
            String productId = cartList.get(index).getProductID();
            int qty       = cartList.get(index).getQuantity();
            if( arrayList.contains( productId ) ) {
                String productIdAndQuantity = productId + "|" + qty;
                productList.add(productIdAndQuantity);
                ManageLocalProductIds.editProductIdList(context, productIdAndQuantity);
            }
        }
        
        getTotalAmount( productList );
        
    }
    
    public void getTotalAmount(ArrayList<String> productList){
        String username = PreferenceManagement.readString( context, ProjectConfiguration.userId, null);
        JSONArray jsonArray = new JSONArray(productList);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ProjectConfiguration.username, username);
            jsonObject.put(ProjectConfiguration.localCartProducts, jsonArray);
            CartServiceLayer.getTotalAmount(jsonObject, new OrderDataCallback() {
                @Override
                public void onSuccess(OrderData orderData) {
                    view.showOrderTotals(orderData);
                }
            
                @Override
                public void onError(String error) {
                
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    
    public interface MyCartView{
        void successful(List<CartObject> cartObject);
        void successful();
        void successful(int position);
        void failed(String message);
        void calculateTotal( int position, int quantity );
        void showOrderTotals( OrderData orderData );
    }
}
