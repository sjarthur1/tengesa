package com.network_layer;

import android.content.Context;

import com.mobile.tengesa.R;
import com.network_layer.callback.*;
import com.network_layer.pojo.AccountNetworkLayer;
import com.objects.UserAddresses;
import com.objects.UserDetails;
import com.objects.list_objects.Country;

import okhttp3.OkHttpClient;
import org.json.JSONObject;

import okio.Timeout;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AccountServiceLayer {
    
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
    private static AccountNetworkLayer accountNetworkLayer = retrofit.create(AccountNetworkLayer.class);
    
    public static void login(JSONObject jsonObject, final Context context, final UserDetailsCallBack userDetailsCallback){
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
               String error = t.getLocalizedMessage();
               if( error == null || error.equals( "timeout" ) ){
                   userDetailsCallback.onError( context.getString(R.string.network_error ) );
               }else if( error.contains( "Unable to resolve host" ) ){
                   userDetailsCallback.onError( context.getString(R.string.server_error ) );
               }else{
                   userDetailsCallback.onError( error );
               }
            }
        });
    }
    
    public static void register(JSONObject jsonObject, final Context context, final ReturnBooleanCallback returnBooleanCallback){
        Call<Boolean> callPage = null;
        callPage = accountNetworkLayer.Register( jsonObject.toString() );
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
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    returnBooleanCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    returnBooleanCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    returnBooleanCallback.onError( error );
                }
            }
        });
    }
    
    public static void getUserDetails(String userId, final Context context, final UserDetailsCallBack userDetailsCallBack){
        Call<UserDetails> callPage = null;
        callPage = accountNetworkLayer.GetUserDetails( userId );
        callPage.enqueue(new retrofit2.Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if( response.isSuccessful() )
                    userDetailsCallBack.onSuccess( response.body() );
                else
                    userDetailsCallBack.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    userDetailsCallBack.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    userDetailsCallBack.onError( context.getString(R.string.server_error ) );
                }else{
                    userDetailsCallBack.onError( error );
                }
            }
        });
    }
    
    public static void getUserAccount(String userId, final Context context, final UserDetailsCallBack userDetailsCallBack){
        Call<UserDetails> callPage = null;
        callPage = accountNetworkLayer.GetUserAccount( userId );
        callPage.enqueue(new retrofit2.Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if( response.isSuccessful() )
                    userDetailsCallBack.onSuccess( response.body() );
                else
                    userDetailsCallBack.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    userDetailsCallBack.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    userDetailsCallBack.onError( context.getString(R.string.server_error ) );
                }else{
                    userDetailsCallBack.onError( error );
                }
            }
        });
        
    }
    
    public static void getAddresses(String userId, final Context context, final UserAddressListCallback userAddressCallback){
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
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    userAddressCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    userAddressCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    userAddressCallback.onError( error );
                }
            }
        });
        
    }
    
    public static void getAddress(String addressId, final Context context, final UserAddressCallback userAddressCallback){
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
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    userAddressCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    userAddressCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    userAddressCallback.onError( error );
                }
            }
        });
        
    }
    
    public static void editPassword( JSONObject jsonObject, final Context context, final ReturnStringCallback returnStringCallback ){
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
    
    public static void editMobileNumber( JSONObject jsonObject, final Context context, final ReturnStringCallback returnStringCallback ){
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
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    returnStringCallback.onError( context.getString( R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    returnStringCallback.onError( context.getString( R.string.server_error ) );
                }else{
                    returnStringCallback.onError( error );
                }
            }
        });
        
    }
    
    public static void editPersonalInformation( JSONObject jsonObject, final Context context, final ReturnStringCallback returnStringCallback ){
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
    
    public static void editAddress( JSONObject jsonObject, final Context context, final ReturnStringCallback returnStringCallback ){
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
    
    public static void addAddress( JSONObject jsonObject, final Context context, final ReturnStringCallback returnStringCallback ){
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
    
    public static void deleteAddress( String AddressId, final Context context, final ReturnBooleanCallback returnBooleanCallback ){
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
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    returnBooleanCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    returnBooleanCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    returnBooleanCallback.onError( error );
                }
            }
        });
        
    }
    
    public static void forgotPassword( String email, final Context context, final ReturnBooleanCallback returnBooleanCallback ){
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
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    returnBooleanCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    returnBooleanCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    returnBooleanCallback.onError( error );
                }
            }
        });
        
    }
    
    public static void isEmailConfirmed( String email, final Context context, final ReturnBooleanCallback returnBooleanCallback ){
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
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    returnBooleanCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    returnBooleanCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    returnBooleanCallback.onError( error );
                }
            }
        });
        
    }
    
    public static void getCountryCodes(boolean IsDeliverableActive, final Context context, final ReturnCountriesCallback returnCountriesCallback ){
        Call<List<Country>> callPage = null;
        callPage = accountNetworkLayer.GetCountryCodes( IsDeliverableActive );
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
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    returnCountriesCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    returnCountriesCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    returnCountriesCallback.onError( error );
                }
            }
        });
        
    }
    
    public static void getCities(int CountryId, final Context context, final ReturnStringListCallback returnStringListCallback ){
        Call<List<String>> callPage = null;
        callPage = accountNetworkLayer.GetCities( CountryId );
        callPage.enqueue(new retrofit2.Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(response.isSuccessful())
                    returnStringListCallback.onSuccess( response.body() );
                else
                    returnStringListCallback.onError( response.message() );
            }
            
            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                String error = t.getLocalizedMessage();
                if( error == null || error.equals( "timeout" ) ){
                    returnStringListCallback.onError( context.getString(R.string.network_error ) );
                }else if( error.contains( "Unable to resolve host" ) ){
                    returnStringListCallback.onError( context.getString(R.string.server_error ) );
                }else{
                    returnStringListCallback.onError( error );
                }
            }
        });
        
    }
}
