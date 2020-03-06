package com.network_layer;

import com.network_layer.callback.ReturnIntegerCallback;
import com.network_layer.callback.ReturnStringCallback;
import com.network_layer.callback.WishListCallback;
import com.network_layer.pojo.CartNetworkLayer;
import com.network_layer.pojo.WishListNetworkLayer;
import com.objects.WishListData;
import okhttp3.OkHttpClient;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.List;

public class WishListServiceLayer {
    
    private static Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.tengesa.com:792/")
        .client(new OkHttpClient())
        .addConverterFactory( ScalarsConverterFactory.create() )
        .addConverterFactory( GsonConverterFactory.create() )
        .build();
    private static WishListNetworkLayer wishListNetworkLayer = retrofit.create(WishListNetworkLayer.class);
    
    public static void saveWishList(String username, String productId, final ReturnStringCallback returnStringCallback ){
        Call<String> callPage = null;
        callPage = wishListNetworkLayer.SaveWishList(username, productId);
        callPage.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                    returnStringCallback.onSuccess(response.body());
                else
                    returnStringCallback.onError(response.message());
            }
            
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                returnStringCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void removeWishList(String MongoId, final ReturnStringCallback returnStringCallback ){
        Call<String> callPage = null;
        callPage = wishListNetworkLayer.RemoveWishList( MongoId );
        callPage.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                    returnStringCallback.onSuccess(response.body());
                else
                    returnStringCallback.onError(response.message());
            }
            
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                returnStringCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void getFullWishList(String Username, final WishListCallback wishListCallback ){
        Call<List<WishListData>> callPage = null;
        callPage = wishListNetworkLayer.GetFullWishList( Username );
        callPage.enqueue(new retrofit2.Callback<List<WishListData>>() {
            @Override
            public void onResponse(Call<List<WishListData>> call, Response<List<WishListData>> response) {
                wishListCallback.onSuccess(response.body());
            }
            
            @Override
            public void onFailure(Call<List<WishListData>> call, Throwable t) {
                wishListCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void getWishListNo(String username, final ReturnIntegerCallback returnIntegerCallback ){
        Call<Integer> callPage = null;
        callPage = wishListNetworkLayer.GetWishListNo( username );
        callPage.enqueue(new retrofit2.Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                returnIntegerCallback.onSuccess(response.body());
            }
            
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                returnIntegerCallback.onError(t.getMessage());
            }
        });
        
    }
    
}
