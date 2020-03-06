package com.network_layer.pojo;

import com.objects.CartObject;
import com.objects.OrderData;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

import static com.constants.ProjectConfiguration.*;

public interface CartNetworkLayer {
    /*
     * Cart API data*/
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @POST("api/Cart/SaveCart")
    Call<Boolean> SaveCart( @Body String body );
    
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Cart/UpdateCart")
    Call<Boolean> UpdateCart( @Query("MongoId") String MongoId, @Query("Quantity") int Quantity );
    
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Cart/RemoveCart")
    Call<String> RemoveCart( @Query("MongoId") String MongoId );
    
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @POST("api/Cart/GetFullCart")
    Call<List<CartObject>> GetFullCart( @Body String body );
    
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @POST("api/Cart/GetCartNo")
    Call<Integer> GetCartNo( @Body String body );
    
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @POST("api/Cart/GetTotalAmount")
    Call<OrderData> GetTotalAmount( @Body String body );
    
    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Product/GetAdditionalShippingFee")
    Call<Double> GetAdditionalShippingFee( @Query("City") String City );
}
