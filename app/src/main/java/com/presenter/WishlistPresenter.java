package com.presenter;

import android.content.Context;

import com.constants.ActionOption;
import com.constants.ProjectConfiguration;
import com.helpers.ManageLocalProductIds;
import com.helpers.PreferenceManagement;
import com.mobile.tengesa.MainActivity;
import com.network_layer.CartServiceLayer;
import com.network_layer.WishListServiceLayer;
import com.network_layer.callback.ReturnBooleanCallback;
import com.network_layer.callback.ReturnStringCallback;
import com.network_layer.callback.WishListCallback;
import com.objects.WishListData;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WishlistPresenter {
    
    private Context context;
    private WishListView view;
    public List<WishListData> wishList;
    
    public WishlistPresenter(Context context, WishListView view) {
        this.context = context;
        this.view = view;
        wishList = new ArrayList<>();
    }
    
    public void getWishList(){
        String username = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
        WishListServiceLayer.getFullWishList(username, context, new WishListCallback() {
            @Override
            public void onSuccess( List<WishListData> wishListData ) {
                view.returnWishList( wishListData );
            }
    
            @Override
            public void onError( String error ) {
                view.failure( error );
            }
        });
    }
    
    public void removeWishListItem( String mongoId, final int position){
        WishListServiceLayer.removeWishList(mongoId, context, new ReturnStringCallback() {
            @Override
            public void onSuccess(String response) {
                view.successful( position );
            }
            
            @Override
            public void onError(String error) {
                view.failure( error );
            }
        });
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
                                view.successful("Successful", "has been added to cart, do you wish to continue to cart?", ActionOption.cart);
                                MainActivity.getInstance().getCart();
                            }else{
                                view.failure( title+" is already in the cart" );
                            }
                        }
                        
                        @Override
                        public void onError(String error) {
                            view.failure( error );
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                ManageLocalProductIds.editProductIdList(context, productId + "|" + quantity);
            }
        }else{
            view.failure("Select the quantity required to purchase.");
        }
    }
    
    public interface WishListView{
        void returnWishList(List<WishListData> wishListData);
        void successful(int position);
        void successful(String title, String message, ActionOption action);
        void successful();
        void failure(String message);
    }
}
