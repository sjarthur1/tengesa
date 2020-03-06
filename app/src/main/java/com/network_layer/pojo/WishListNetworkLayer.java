package com.network_layer.pojo;

import com.objects.WishListData;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

import static com.constants.ProjectConfiguration.*;
import static com.constants.ProjectConfiguration.ACCEPT_LANGUAGE;
import static com.constants.ProjectConfiguration.EN_US;

public interface WishListNetworkLayer {
    
    /*
     * Wishlist POJO data*/
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/WishList/SaveWishList")
    Call<String> SaveWishList( @Query("Username") String username, @Query("ProductId") String productId );
    
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/WishList/RemoveWishList")
    Call<String> RemoveWishList( @Query("MongoId") String MongoId );
    
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/WishList/GetFullWishList")
    Call<List<WishListData>> GetFullWishList(@Query("Username") String Username);
    
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/WishList/GetWishListNo")
    Call<Integer> GetWishListNo( @Query("Username") String Username );
}
