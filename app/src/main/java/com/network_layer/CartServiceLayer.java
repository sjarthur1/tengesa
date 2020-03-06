package com.network_layer;

import com.google.gson.JsonObject;
import com.network_layer.callback.*;
import com.network_layer.pojo.AccountNetworkLayer;
import com.network_layer.pojo.CartNetworkLayer;
import com.objects.CartObject;
import com.objects.OrderData;
import com.objects.UserDetails;
import okhttp3.OkHttpClient;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.List;

public class CartServiceLayer {
    
    private static Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.tengesa.com:792/")
            .client(new OkHttpClient())
            .addConverterFactory( ScalarsConverterFactory.create() )
            .addConverterFactory( GsonConverterFactory.create() )
            .build();
    private static CartNetworkLayer cartNetworkLayer = retrofit.create(CartNetworkLayer.class);
    
    public static void saveCart( JSONObject jsonObject, final ReturnBooleanCallback returnBooleanCallback ){
        Call<Boolean> callPage = null;
        callPage = cartNetworkLayer.SaveCart( jsonObject.toString() );
        callPage.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()) {
                    returnBooleanCallback.onSuccess(response.body());
                }else{
                    returnBooleanCallback.onError(response.errorBody().toString());
                }
            }
            
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                returnBooleanCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void updateCart( String mongoId, int quantity,  final ReturnBooleanCallback returnStringCallback ){
        Call<Boolean> callPage = null;
        callPage = cartNetworkLayer.UpdateCart( mongoId, quantity );
        callPage.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful())
                    returnStringCallback.onSuccess(response.body());
                else
                    returnStringCallback.onSuccess( false );
            }
            
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                returnStringCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void removeCart( String mongoId, final ReturnStringCallback returnStringCallback ){
        Call<String> callPage = null;
        callPage = cartNetworkLayer.RemoveCart( mongoId );
        callPage.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                    returnStringCallback.onSuccess(response.body());
                else
                    returnStringCallback.onError( response.errorBody().toString() );
            }
            
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                returnStringCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void getFullCart( JSONObject jsonObject, final CartObjectCallback cartObjectCallback ){
        Call<List<CartObject>> callPage = null;
        callPage = cartNetworkLayer.GetFullCart(jsonObject.toString());
        callPage.enqueue(new retrofit2.Callback<List<CartObject>>() {
            @Override
            public void onResponse( Call<List<CartObject>> call, Response<List<CartObject>> response ) {
                if(response.isSuccessful())
                    cartObjectCallback.onSuccess( response.body() );
                else
                    cartObjectCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure( Call<List<CartObject>> call, Throwable t ) {
                cartObjectCallback.onError( t.getMessage() );
            }
        });
        
    }
    
    public static void getCartNo( JSONObject jsonObject, final ReturnIntegerCallback returnIntegerCallback ){
        Call<Integer> callPage = null;
        callPage = cartNetworkLayer.GetCartNo( jsonObject.toString() );
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
    
    public static void getTotalAmount(JSONObject jsonObject, final OrderDataCallback orderDataCallback ){
        Call<OrderData> callPage = null;
        callPage = cartNetworkLayer.GetTotalAmount( jsonObject.toString() );
        callPage.enqueue(new retrofit2.Callback<OrderData>() {
            @Override
            public void onResponse(Call<OrderData> call, Response<OrderData> response) {
                if(response.isSuccessful())
                    orderDataCallback.onSuccess(response.body());
                else
                    orderDataCallback.onError( response.message() );
                
            }
            
            @Override
            public void onFailure(Call<OrderData> call, Throwable t) {
                orderDataCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void getAdditionalShippingFee(String City, final ReturnDoubleCallback returnDoubleCallback){
        Call<Double> callPage = null;
        callPage = cartNetworkLayer.GetAdditionalShippingFee( City );
        callPage.enqueue(new Callback<Double>() {
            @Override
            public void onResponse(Call<Double> call, Response<Double> response) {
                if(response.isSuccessful() )
                    returnDoubleCallback.successful(response.body());
                else
                    returnDoubleCallback.failure( response.message() );
            }
    
            @Override
            public void onFailure(Call<Double> call, Throwable t) {
                returnDoubleCallback.failure( t.getMessage() );
        
            }
        });
    }
}
