package com.network_layer;

import com.network_layer.callback.*;
import com.network_layer.pojo.AccountNetworkLayer;
import com.objects.UserAddresses;
import com.objects.UserDetails;
import com.objects.list_objects.Country;

import okhttp3.OkHttpClient;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.List;

public class AccountServiceLayer {
    private static Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.tengesa.com:792/")
            .client(new OkHttpClient())
            .addConverterFactory( ScalarsConverterFactory.create() )
            .addConverterFactory( GsonConverterFactory.create() )
            .build();
    private static AccountNetworkLayer accountNetworkLayer = retrofit.create(AccountNetworkLayer.class);
    
    public static void login(JSONObject jsonObject, final UserDetailsCallBack userDetailsCallback){
        Call<UserDetails> callPage = null;
        callPage = accountNetworkLayer.Login(jsonObject.toString());
        callPage.enqueue(new retrofit2.Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if(response.isSuccessful())
                    userDetailsCallback.onSuccess(response.body());
                else
                    userDetailsCallback.onError(response.message());
            }
            
            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                userDetailsCallback.onError(t.getMessage());
            }
        });
    }
    
    public static void register(JSONObject jsonObject, final ReturnBooleanCallback returnBooleanCallback){
        Call<Boolean> callPage = null;
        callPage = accountNetworkLayer.Register(jsonObject.toString());
        callPage.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                returnBooleanCallback.onSuccess( response.body() );
            }
            
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                returnBooleanCallback.onError(t.getMessage());
            }
        });
    }
    
    public static void getUserDetails(String userId, final UserDetailsCallBack userDetailsCallBack){
        Call<UserDetails> callPage = null;
        callPage = accountNetworkLayer.GetUserDetails( userId );
        callPage.enqueue(new retrofit2.Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                userDetailsCallBack.onSuccess( response.body() );
            }
            
            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                userDetailsCallBack.onError(t.getMessage());
            }
        });
    }
    
    public static void getUserAccount(String userId, final UserDetailsCallBack userDetailsCallBack){
        Call<UserDetails> callPage = null;
        callPage = accountNetworkLayer.GetUserAccount( userId );
        callPage.enqueue(new retrofit2.Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                userDetailsCallBack.onSuccess( response.body() );
            }
            
            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                userDetailsCallBack.onError(t.getMessage());
            }
        });
        
    }
    
    public static void getAddresses(String userId, final UserAddressListCallback userAddressCallback){
        Call<List<UserAddresses>> callPage = null;
        callPage = accountNetworkLayer.GetAddresses( userId );
        callPage.enqueue(new retrofit2.Callback<List<UserAddresses>>() {
            @Override
            public void onResponse(Call<List<UserAddresses>> call, Response<List<UserAddresses>> response) {
                if(response.isSuccessful())
                    userAddressCallback.onSuccess( response.body() );
                else
                    userAddressCallback.onError(response.message());
            }
            
            @Override
            public void onFailure(Call<List<UserAddresses>> call, Throwable t) {
                userAddressCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void getAddress(String addressId, final UserAddressCallback userAddressCallback){
        Call<UserAddresses> callPage = null;
        callPage = accountNetworkLayer.GetAddress( addressId );
        callPage.enqueue(new retrofit2.Callback<UserAddresses>() {
            @Override
            public void onResponse(Call<UserAddresses> call, Response<UserAddresses> response) {
                if(response.isSuccessful())
                    userAddressCallback.onSuccess( response.body() );
                else
                    userAddressCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<UserAddresses> call, Throwable t) {
                userAddressCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void editPassword( JSONObject jsonObject, final ReturnStringCallback returnStringCallback ){
        Call<String> callPage = null;
        callPage = accountNetworkLayer.EditPassword( jsonObject.toString() );
        callPage.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                    returnStringCallback.onSuccess( response.body() );
                else
                    returnStringCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                returnStringCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void editMobileNumber( JSONObject jsonObject, final ReturnStringCallback returnStringCallback ){
        Call<String> callPage = null;
        callPage = accountNetworkLayer.EditMobileNumber( jsonObject.toString() );
        callPage.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                    returnStringCallback.onSuccess( response.body() );
                else
                    returnStringCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                returnStringCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void editPersonalInformation( JSONObject jsonObject, final ReturnStringCallback returnStringCallback ){
        Call<String> callPage = null;
        callPage = accountNetworkLayer.EditPersonalInformation( jsonObject.toString() );
        callPage.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                    returnStringCallback.onSuccess( response.body() );
                else
                    returnStringCallback.onError(response.message());
            }
            
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                returnStringCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void editAddress( JSONObject jsonObject, final ReturnStringCallback returnStringCallback ){
        Call<String> callPage = null;
        callPage = accountNetworkLayer.EditAddress( jsonObject.toString() );
        callPage.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                    returnStringCallback.onSuccess( response.body() );
                else
                    returnStringCallback.onError(response.errorBody().toString());
            }
            
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                returnStringCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void addAddress( JSONObject jsonObject, final ReturnStringCallback returnStringCallback ){
        Call<String> callPage = null;
        callPage = accountNetworkLayer.AddAddress( jsonObject.toString() );
        callPage.enqueue(new retrofit2.Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful())
                    returnStringCallback.onSuccess( response.body() );
                else
                    returnStringCallback.onError(response.message());
            }
            
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                returnStringCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void deleteAddress( String AddressId, final ReturnBooleanCallback returnBooleanCallback ){
        Call<Boolean> callPage = null;
        callPage = accountNetworkLayer.DeleteAddress( AddressId );
        callPage.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful())
                    returnBooleanCallback.onSuccess( response.body() );
                else
                    returnBooleanCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                returnBooleanCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void forgotPassword( String email, final ReturnBooleanCallback returnBooleanCallback ){
        Call<Boolean> callPage = null;
        callPage = accountNetworkLayer.ForgotPassword( email );
        callPage.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful())
                    returnBooleanCallback.onSuccess( response.body() );
                else
                    returnBooleanCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                returnBooleanCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void isEmailConfirmed( String email, final ReturnBooleanCallback returnBooleanCallback ){
        Call<Boolean> callPage = null;
        callPage = accountNetworkLayer.IsEmailConfirmed( email );
        callPage.enqueue(new retrofit2.Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful())
                    returnBooleanCallback.onSuccess( response.body() );
                else
                    returnBooleanCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                returnBooleanCallback.onError(t.getMessage());
            }
        });
        
    }
    
    public static void getCountryCodes( final ReturnCountriesCallback returnCountriesCallback ){
        Call<List<Country>> callPage = null;
        callPage = accountNetworkLayer.GetCountryCodes();
        callPage.enqueue(new retrofit2.Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if(response.isSuccessful())
                    returnCountriesCallback.onSuccess( response.body() );
                else
                    returnCountriesCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                returnCountriesCallback.onError(t.getMessage());
            }
        });
        
    }
}
