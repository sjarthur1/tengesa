package com.presenter;

import android.content.Context;
import com.constants.ProjectConfiguration;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.network_layer.AccountServiceLayer;
import com.network_layer.callback.ReturnCountriesCallback;
import com.network_layer.callback.ReturnStringCallback;
import com.network_layer.callback.ReturnStringListCallback;
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
            view.error( "Please provide the username", ErrorType.name );
        } else if(userAddresses.getMobile_Number() == null || userAddresses.getMobile_Number().equals("")){
            view.error( "Please provide the Mobile number", ErrorType.phoneNumber);
        } else if(userAddresses.getCountry() == null || userAddresses.getCountry().equals("")){
            view.error( "Please select the country where you reside", ErrorType.country );
        } else if(userAddresses.getCity() == null || userAddresses.getCity().equals("")){
            view.error( "Please provide the city of residence", ErrorType.city);
        } else if(userAddresses.getArea() == null || userAddresses.getArea().equals("")){
            view.error( "Please provide the area of residence", ErrorType.area);
        } else if(userAddresses.getStreetNameOrNo() == null || userAddresses.getStreetNameOrNo().equals("")){
            view.error( "Please provide the street of residence", ErrorType.street);
        } else if(userAddresses.getLocationType() == null || userAddresses.getLocationType().equals("")){
            view.error( "Please select the location type", ErrorType.locationType);
        } else if(userAddresses.getShippingNote() == null || userAddresses.getShippingNote().equals("")){
            view.error( "Please provide a shipping note", ErrorType.shippingNote);
        } else if(userAddresses.getAddress1() == null || userAddresses.getAddress1().equals("")){
            view.error( "Please provide the first Address", ErrorType.address1 );
        } else {
            if(newEntry)
                newAddress( userAddresses );
            else
                editAddress( userAddresses );
        }
    }
    
    private void newAddress( UserAddresses userAddresses ){
        try {
            view.showDialog();
            Gson gson = new Gson();
            String json = gson.toJson(userAddresses);
        
            JSONObject jsonObject = new JSONObject(json);
            AccountServiceLayer.addAddress(jsonObject, context, new ReturnStringCallback() {
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
            view.showDialog();
            Gson gson = new Gson();
            String json = gson.toJson(userAddresses);
            //JSONParser jsonParser = new JSONParser();
        
            JSONObject jsonObject = new JSONObject(json);
            AccountServiceLayer.editAddress(jsonObject, context, new ReturnStringCallback() {
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
        AccountServiceLayer.getCountryCodes( true, context, new ReturnCountriesCallback() {
            @Override
            public void onSuccess( List<Country> countryObject ) {
                view.successful( countryObject );
            }
            
            @Override
            public void onError(String error) {
                view.error( error, ErrorType.country );
            }
        });
    }
    
    public void getCities( int CountryId ){
        AccountServiceLayer.getCities(CountryId, context, new ReturnStringListCallback() {
            @Override
            public void onSuccess(List<String> response) {
                view.cities(response );
            }
    
            @Override
            public void onError(String error) {
                view.error( error, ErrorType.none );
            }
        });
    }
    
    public interface AddAddressView{
        void successful( UserAddresses addresses );
        void showDialog();
        void successful( String message );
        void successful( List<Country> countryObject );
        void cities( List<String> cities );
        void failure( String message );
        void error( String error, ErrorType errorType );
    }
    
    public enum ErrorType{
        name, email, password, password2, country, phoneNumber, shippingNote, city, locationType, area, street, address1, address2, none
    }
}
