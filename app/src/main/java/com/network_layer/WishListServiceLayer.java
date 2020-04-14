package com.network_layer;

import android.content.Context;

import com.mobile.tengesa.R;
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
import java.util.concurrent.TimeUnit;

public class WishListServiceLayer {
    
    private static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
        .connectTimeout( 60, TimeUnit.SECONDS )
        .readTimeout( 60, TimeUnit.SECONDS )
        .writeTimeout( 60, TimeUnit.SECONDS )
        .build();
    private static Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.tengesa.com:792/")
        .client( okHttpClient )
        .addConverterFactory( ScalarsConverterFactory.create() )
        .addConverterFactory( GsonConverterFactory.create() )
        .build();
    private static WishListNetworkLayer wishListNetworkLayer = retrofit.create(WishListNetworkLayer.class);
    
    public static void saveWishList(String username, String productId, final Context context, final ReturnStringCallback returnStringCallback ){
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
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    returnStringCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    returnStringCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    returnStringCallback.onError( error );
                }
            }
        });
        
    }
    
    public static void removeWishList(String MongoId, final Context context, final ReturnStringCallback returnStringCallback ){
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
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    returnStringCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    returnStringCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    returnStringCallback.onError( error );
                }
            }
        });
        
    }
    
    public static void getFullWishList(String Username, final Context context, final WishListCallback wishListCallback ){
        Call<List<WishListData>> callPage = null;
        callPage = wishListNetworkLayer.GetFullWishList( Username );
        callPage.enqueue(new retrofit2.Callback<List<WishListData>>() {
            @Override
            public void onResponse(Call<List<WishListData>> call, Response<List<WishListData>> response) {
                if( response.isSuccessful() )
                    wishListCallback.onSuccess( response.body() );
                else
                    wishListCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<List<WishListData>> call, Throwable t) {
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    wishListCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    wishListCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    wishListCallback.onError( error );
                }
            }
        });
        
    }
    
    public static void getWishListNo(String username, final Context context, final ReturnIntegerCallback returnIntegerCallback ){
        Call<Integer> callPage = null;
        callPage = wishListNetworkLayer.GetWishListNo( username );
        callPage.enqueue(new retrofit2.Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if( response.isSuccessful() )
                    returnIntegerCallback.onSuccess(response.body());
                else
                    returnIntegerCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    returnIntegerCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    returnIntegerCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    returnIntegerCallback.onError( error );
                }
            }
        });
        
    }
    
}
