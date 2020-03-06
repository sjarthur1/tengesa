package com.network_layer.pojo;

import com.objects.FullOrder;
import com.objects.HomeProductCategories;
import com.objects.Orders;
import com.objects.ProductData;
import com.objects.list_objects.Categories;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

import static com.constants.ProjectConfiguration.*;
import static com.constants.ProjectConfiguration.ACCEPT_LANGUAGE;
import static com.constants.ProjectConfiguration.EN_US;

public interface ProductNetworkLayer {
    
    /*
     *Product POJO Data*/
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Product/GetProductByProductID")
    Call<ProductData> GetProductByProductID( @Query("ProductID") String ProductID );
    
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Product/GetSearchProductResults")
    Call<List<ProductData>> GetSearchProductResults( @Query("TextSearch") String TextSearch );
    
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Product/GetProductsByCategoryID")
    Call<List<ProductData>> GetProductsByCategoryID( @Query("CategoryID") String CategoryID );
    
    //Get All Offers
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Product/GetProductsBySellerID")
    Call<List<ProductData>> GetProductsBySellerID( @Query("SellerID") String SellerID );
    
    //Get Categories for displaying Home Products
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Product/GetHomeProducts")
    Call<List<HomeProductCategories>> GetHomeProducts();
    
    //Get Product Categories
    @Headers({
            CONTENT_TYPE + ": " + APPLICATION_JSON,
            ACCEPT + ": " + APPLICATION_JSON,
            AUTHORIZATION + ": " + BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE + ": " + EN_US
    })
    @GET("api/Product/GetCategories")
    Call<Categories> GetCategories();
    
    //Get Product Categories
    @Headers({
            CONTENT_TYPE + ": " + APPLICATION_JSON,
            ACCEPT + ": " + APPLICATION_JSON,
            AUTHORIZATION + ": " + BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE + ": " + EN_US
    })
    @GET("api/Product/GetHomeBanners")
    Call<List<String>> GetHomeBanners();
    
    //Get Orders
    @Headers({
            CONTENT_TYPE + ": " + APPLICATION_JSON,
            ACCEPT + ": " + APPLICATION_JSON,
            AUTHORIZATION + ": " + BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE + ": " + EN_US
    })
    @POST("api/Order/PlaceOrder")
    Call<Orders> PlaceOrder( @Body String body );
    
    //Get Orders
    @Headers({
            CONTENT_TYPE + ": " + APPLICATION_JSON,
            ACCEPT + ": " + APPLICATION_JSON,
            AUTHORIZATION + ": " + BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE + ": " + EN_US
    })
    @GET("api/Order/GetOrders")
    Call<List<FullOrder>> GetOrders(@Query("UserId") String UserId );
    
}
