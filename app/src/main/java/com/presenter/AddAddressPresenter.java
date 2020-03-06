package com.presenter;

import android.content.Context;
import com.constants.ProjectConfiguration;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.network_layer.AccountServiceLayer;
import com.network_layer.callback.ReturnCountriesCallback;
import com.network_layer.callback.ReturnStringCallback;
import com.objects.UserAddresses;
import com.objects.list_objects.Country;

import org.json.JSONObject;

import java.util.List;

public class AddAddressPresenter {
    
    Context context;
    AddAddressView view;
    public AddAddressPresenter(Context context, AddAddressView view){
        this.context = context;
        this.view = view;
    }
    
    public void submitAddress(UserAddresses userAddresses, boolean newEntry){
        
        if( userAddresses.getFullname() == null || userAddresses.getFullname().equals("") ) {
            view.failure( "Please provide the username", ProjectConfiguration.name);
        } else if(userAddresses.getMobile_Number() == null || userAddresses.getMobile_Number().equals("")){
            view.failure( "Please provide the Mobile number", ProjectConfiguration.mobile);
        } else if(userAddresses.getCountry() == null || userAddresses.getCountry().equals("")){
            view.failure( "Please select the country where you reside", ProjectConfiguration.country );
        } else if(userAddresses.getCity() == null || userAddresses.getCity().equals("")){
            view.failure( "Please provide the city of residence", ProjectConfiguration.city);
        } else if(userAddresses.getArea() == null || userAddresses.getArea().equals("")){
            view.failure( "Please provide the area of residence", ProjectConfiguration.area);
        } else if(userAddresses.getStreetNameOrNo() == null || userAddresses.getStreetNameOrNo().equals("")){
            view.failure( "Please provide the street of residence", ProjectConfiguration.street);
        } else if(userAddresses.getLocationType() == null || userAddresses.getLocationType().equals("")){
            view.failure( "Please select the location type", ProjectConfiguration.locationType);
        } else if(userAddresses.getAddress1() == null || userAddresses.getAddress1().equals("")){
            view.failure( "Please provide the first Address", ProjectConfiguration.address1 );
        } else {
            if(newEntry)
                newAddress( userAddresses );
            else
                editAddress( userAddresses );
        }
    }
    
    private void newAddress( UserAddresses userAddresses ){
        try {
            Gson gson = new Gson();
            String json = gson.toJson(userAddresses);
        
            JSONObject jsonObject = new JSONObject(json);
            AccountServiceLayer.addAddress(jsonObject, new ReturnStringCallback() {
                @Override
                public void onSuccess(String response) {
                    view.successful( response );
                }
            
                @Override
                public void onError( String error ) {
                    view.failure( error );
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void editAddress(UserAddresses userAddresses){
        try {
            Gson gson = new Gson();
            String json = gson.toJson(userAddresses);
            //JSONParser jsonParser = new JSONParser();
        
            JSONObject jsonObject = new JSONObject(json);
            AccountServiceLayer.editAddress(jsonObject, new ReturnStringCallback() {
                @Override
                public void onSuccess(String response) {
                    view.successful( response );
                }
            
                @Override
                public void onError(String error) {
                    view.failure( error );
                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void getCountryCodes(){
        AccountServiceLayer.getCountryCodes(new ReturnCountriesCallback() {
            @Override
            public void onSuccess( List<Country> countryObject ) {
                view.successful( countryObject );
            }
            
            @Override
            public void onError(String error) {
                view.error( error, ErrorType.countryCode );
            }
        });
    }
    
    public interface AddAddressView{
        void successful( UserAddresses addresses );
        void successful( String message );
        void successful( List<Country> countryObject );
        void failure( String message, String page );
        void failure( String message );
        void error( String error, ErrorType errorType );
    }
    
    public enum ErrorType{
        name, email, password, password2, countryCode, phoneNumber, login
    }
}
