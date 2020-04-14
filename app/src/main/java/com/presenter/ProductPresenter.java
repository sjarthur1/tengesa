package com.presenter;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.constants.ActionOption;
import com.constants.ProjectConfiguration;
import com.helpers.ManageLocalProductIds;
import com.helpers.PreferenceManagement;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.network_layer.CartServiceLayer;
import com.network_layer.ProductServiceLayer;
import com.network_layer.WishListServiceLayer;
import com.network_layer.callback.ProductDataCallback;
import com.network_layer.callback.ReturnBooleanCallback;
import com.network_layer.callback.ReturnStringCallback;
import com.objects.ProductData;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductPresenter {
    private ProductView view;
    private Context context;
    public List<ProductData> currentProductList;
    public ProductPresenter(Context context, ProductView view){
        this.context = context;
        this.view = view;
        currentProductList = new ArrayList<>();
    }
    
    public void addToCart(String productId, String quantity, final String title ){
        int total = Integer.parseInt(quantity);
        if( total > 0) {
            String username = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
            if (username != null) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put( ProjectConfiguration.username,  username  );
                    jsonObject.put( ProjectConfiguration.productId, productId );
                    jsonObject.put( ProjectConfiguration.quantity,  quantity  );
                    CartServiceLayer.saveCart(jsonObject, context, new ReturnBooleanCallback() {
                        @Override
                        public void onSuccess(Boolean response) {
                            if( response ) {
                                view.successful("Successful", title+" "+context.getString( R.string.cart_added_message), ActionOption.cart);
                                MainActivity.getInstance().getCart();
                            }else{
                                view.failed( title+" is already in the cart" );
                            }
                        }
                
                        @Override
                        public void onError(String error) {
                            view.failed( error );
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                boolean stored = ManageLocalProductIds.editProductIdList( context, productId + "|" + quantity );
                if( stored ){
                    view.successful( "Successful", title+" "+context.getString( R.string.cart_added_message), ActionOption.cart );
                    MainActivity.getInstance().getCart();
                }
            }
        }else{
            view.failed("Select the quantity required to purchase.");
        }
    }
    
    public void getProductById( String productId ){
        ProductServiceLayer.getProductByProductID(productId, context, new ProductDataCallback() {
            @Override
            public void onSuccess(ProductData productData) {
                view.successful( productData );
            }
    
            @Override
            public void onSuccess(List<ProductData> productData) {
        
            }
    
            @Override
            public void onError(String error) {
        
            }
        });
    }
    
    public void getRelatedProducts( String categoryId, String productId ){
        ProductServiceLayer.getRelatedProducts( categoryId, productId, context, new ProductDataCallback() {
            @Override
            public void onSuccess(ProductData productData) {
            
            }
            
            @Override
            public void onSuccess( List<ProductData> productList ) {
                currentProductList = productList;
                view.successful( productList );
            }
            
            @Override
            public void onError(String error) {
            
            }
        });
    }
    
    public void saveToWishList( String productId, final String title ){
        String username = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
        WishListServiceLayer.saveWishList(username, productId, context, new ReturnStringCallback() {
            @Override
            public void onSuccess(String response) {
                view.successful("Successful", title+" "+context.getString( R.string.cart_added_message), ActionOption.wishlist);
            }
            
            @Override
            public void onError(String error) {
                view.failed( error );
            }
        });
    }
    
    
    
    public interface ProductView{
        void successful( ProductData productList );
        void successful( List<ProductData> productList );
        void successful(String title, String message, ActionOption action);
        void failed(String error);
        void failed();
    }
}
