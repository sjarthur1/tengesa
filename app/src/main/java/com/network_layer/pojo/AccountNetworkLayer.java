package com.network_layer.pojo;

import com.objects.*;
import com.objects.list_objects.Country;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

import static com.constants.ProjectConfiguration.*;

public interface AccountNetworkLayer {
    
    @Headers({
            CONTENT_TYPE + ": " + APPLICATION_JSON,
            ACCEPT + ": " + APPLICATION_JSON,
            AUTHORIZATION + ": " + BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE + ": " + EN_US
    })
    @POST("api/Account/Login")
    Call<UserDetails> Login(@Body String body);

    @Headers({
            CONTENT_TYPE + ": " + APPLICATION_JSON,
            ACCEPT + ": " + APPLICATION_JSON,
            AUTHORIZATION + ": " + BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE + ": " + EN_US
    } )
    @POST("api/Account/Register")
    Call<Boolean> Register(@Body String body);

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Account/GetUserDetails")
    Call<UserDetails> GetUserDetails(@Query("UserId") String UserId);

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Account/GetUserAccount")
    Call<UserDetails> GetUserAccount( @Query("UserId") String UserId );

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Account/GetAddresses")
    Call<List<UserAddresses>> GetAddresses( @Query("UserId") String UserId );

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Account/GetAddress")
    Call<UserAddresses> GetAddress( @Query("AddressID") String AddressID );

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @POST("api/Account/EditPassword")
    Call<String> EditPassword(@Body String body);

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @POST("api/Account/EditMobileNumber")
    Call<String> EditMobileNumber(@Body String body);

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @POST("api/Account/EditPersonalInformation")
    Call<String> EditPersonalInformation(@Body String body);

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @POST("api/Account/EditAddress")
    Call<String> EditAddress(@Body String body);

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @POST("api/Account/AddAddress")
    Call<String> AddAddress(@Body String body);

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Account/DeleteAddress")
    Call<Boolean> DeleteAddress( @Query("AddressId") String AddressId );

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/User/ForgotPassword")
    Call<Boolean> ForgotPassword( @Query("Email") String Email );

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Account/IsEmailConfirmed")
    Call<Boolean> IsEmailConfirmed( @Query("Email") String Email );

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Account/GetCountryCodes")
    Call<List<Country>> GetCountryCodes(@Query("IsDeliverableActive") boolean IsDeliverableActive);

    @Headers({
            CONTENT_TYPE+": "+ APPLICATION_JSON,
            ACCEPT+": "+ APPLICATION_JSON,
            AUTHORIZATION+": "+ BASIC_BMLWCZ_IW_MTK,
            ACCEPT_LANGUAGE+": "+ EN_US
    })
    @GET("api/Account/GetCities")
    Call<List<String>> GetCities(@Query("CountryId") int CountryId);
    
    
   
    
    

}
