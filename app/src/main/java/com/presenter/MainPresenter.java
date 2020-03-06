package com.presenter;

import android.content.Context;

import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.network_layer.CartServiceLayer;
import com.network_layer.callback.CartObjectCallback;
import com.network_layer.callback.ReturnIntegerCallback;
import com.objects.CartObject;
import com.objects.OrderData;
import com.objects.ProductOrder;

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
    
    public MainPresenter( Context context, MainView view ) {
        this.context = context;
        this.view = view;
        cartList = new ArrayList<>();
        productList = new ArrayList<>();
    }
    
    public void setCartList(List<ProductOrder> productList){
        this.productList = productList;
    }
    
    public List<ProductOrder> getCartList(){
        return productList;
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
            CartServiceLayer.getCartNo( jsonObject, new ReturnIntegerCallback() {
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
    
    public interface MainView{
        void successful(int numberInCart);
    }
}
