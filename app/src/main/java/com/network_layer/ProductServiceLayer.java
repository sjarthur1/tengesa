package com.network_layer;

import com.network_layer.callback.CategoriesCallback;
import com.network_layer.callback.ProductDataCallback;
import com.network_layer.callback.ReturnOrderCallback;
import com.network_layer.callback.ReturnOrderFullCallback;
import com.network_layer.callback.ReturnStringListCallback;
import com.network_layer.callback.UserDetailsCallBack;
import com.network_layer.pojo.AccountNetworkLayer;
import com.network_layer.pojo.ProductNetworkLayer;
import com.objects.FullOrder;
import com.objects.HomeProductCategories;
import com.objects.Orders;
import com.objects.ProductData;
import com.objects.UserDetails;
import com.objects.list_objects.Categories;

import org.json.JSONObject;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.List;

public class ProductServiceLayer {
    private static Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.tengesa.com:792/")
            .client(new OkHttpClient())
            .addConverterFactory( ScalarsConverterFactory.create() )
            .addConverterFactory( GsonConverterFactory.create() )
            .build();
    private static ProductNetworkLayer productNetworkLayer = retrofit.create(ProductNetworkLayer.class);
    
    public static void getProductByProductID( String ProductID, final ProductDataCallback productDataCallback ){
        Call<ProductData> callPage = null;
        callPage = productNetworkLayer.GetProductByProductID( ProductID );
        callPage.enqueue(new retrofit2.Callback<ProductData>() {
            @Override
            public void onResponse(Call<ProductData> call, Response<ProductData> response) {
                productDataCallback.onSuccess( response.body() );
            }
            
            @Override
            public void onFailure(Call<ProductData> call, Throwable t) {
                productDataCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void getSearchProductResults( String TextSearch, final ProductDataCallback productDataCallback ){
        Call<List<ProductData>> callPage = null;
        callPage = productNetworkLayer.GetSearchProductResults( TextSearch );
        callPage.enqueue(new retrofit2.Callback<List<ProductData>>() {
            @Override
            public void onResponse(Call<List<ProductData>> call, Response<List<ProductData>> response) {
                if(response.isSuccessful())
                    productDataCallback.onSuccess( response.body() );
                else
                    productDataCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<List<ProductData>> call, Throwable t) {
                productDataCallback.onError(t.getMessage());
            }
        });
    }
    
    public static void getProductsByCategoryID( String CategoryID, final ProductDataCallback productDataCallback ){
        Call<List<ProductData>> callPage = null;
        callPage = productNetworkLayer.GetProductsByCategoryID( CategoryID );
        callPage.enqueue(new retrofit2.Callback<List<ProductData>>() {
            @Override
            public void onResponse(Call<List<ProductData>> call, Response<List<ProductData>> response) {
                productDataCallback.onSuccess( response.body() );
            }
            
            @Override
            public void onFailure(Call<List<ProductData>> call, Throwable t) {
                productDataCallback.onError(t.getMessage());
            }
        });
    }
    
    public static void getProductsBySellerID( String SellerID, final ProductDataCallback productDataCallback ){
        Call<List<ProductData>> callPage = null;
        callPage = productNetworkLayer.GetProductsBySellerID( SellerID );
        callPage.enqueue(new retrofit2.Callback<List<ProductData>>() {
            @Override
            public void onResponse(Call<List<ProductData>> call, Response<List<ProductData>> response) {
                productDataCallback.onSuccess( response.body() );
            }
            
            @Override
            public void onFailure(Call<List<ProductData>> call, Throwable t) {
                productDataCallback.onError(t.getMessage());
            }
        });
    }
    
    public static void getCategories( final CategoriesCallback categoriesCallback ){
        Call<Categories> callPage = null;
        callPage = productNetworkLayer.GetCategories();
        callPage.enqueue(new retrofit2.Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if(response.isSuccessful()) {
                    Categories categories = response.body();
                    categoriesCallback.onSuccess(response.body());
                }else
                    categoriesCallback.onError(response.message());
            }
            
            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                categoriesCallback.onError(t.getMessage());
            }
        });
    }
    
    public static void getHomeProducts( final CategoriesCallback categoriesCallback ){
        Call<List<HomeProductCategories>> callPage = null;
        callPage = productNetworkLayer.GetHomeProducts();
        callPage.enqueue(new retrofit2.Callback<List<HomeProductCategories>>() {
            @Override
            public void onResponse(Call<List<HomeProductCategories>> call, Response<List<HomeProductCategories>> response) {
                if(response.isSuccessful())
                    categoriesCallback.onSuccess( response.body() );
                else
                    categoriesCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<List<HomeProductCategories>> call, Throwable t) {
                categoriesCallback.onError(t.getMessage());
            }
        });
    }
    
    public static void getHomeBanners( final ReturnStringListCallback stringListCallback ){
        Call<List<String>> callPage = null;
        callPage = productNetworkLayer.GetHomeBanners();
        callPage.enqueue(new retrofit2.Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful())
                    stringListCallback.onSuccess( response.body() );
                else
                    stringListCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                stringListCallback.onError(t.getMessage());
            }
        });
    }
    
    public static void placeOrder(JSONObject jsonObject, final ReturnOrderCallback returnOrderCallback ){
        Call<Orders> callPage = null;
        callPage = productNetworkLayer.PlaceOrder( jsonObject.toString() );
        callPage.enqueue(new retrofit2.Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                if(response.isSuccessful())
                    returnOrderCallback.successful( response.body() );
                else
                    returnOrderCallback.failure( response.message() );
            }
            
            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                returnOrderCallback.failure( t.getMessage() );
            }
        });
    }
    
    public static void getOrder(String userId, final ReturnOrderFullCallback returnOrderCallback ){
        Call<List<FullOrder>> callPage = null;
        callPage = productNetworkLayer.GetOrders( userId );
        callPage.enqueue(new retrofit2.Callback<List<FullOrder>>() {
            @Override
            public void onResponse(Call<List<FullOrder>> call, Response<List<FullOrder>> response) {
                if(response.isSuccessful())
                    returnOrderCallback.successful( response.body() );
                else
                    returnOrderCallback.failure( response.message() );
            }
            
            @Override
            public void onFailure(Call<List<FullOrder>> call, Throwable t) {
                returnOrderCallback.failure( t.getMessage() );
            }
        });
    }
}
