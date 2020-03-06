package com.presenter;

import android.content.Context;

import com.constants.ProjectConfiguration;
import com.network_layer.AccountServiceLayer;
import com.network_layer.callback.ReturnBooleanCallback;
import com.network_layer.callback.ReturnCountriesCallback;
import com.objects.list_objects.Country;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RegisterPresenter {
    
    private RegisterView view;
    private Context context;
    public RegisterPresenter(Context context, RegisterView view){
        this.context = context;
        this.view = view;
    }
    
    public void register(String fullName, String email, String mobileNumber, String password, String reenterPassword, String countryCode){
        if( fullName.length() == 0 ) {
            view.error( "Provide Name Please", ErrorType.name );
        }else if( email.length() == 0 ) {
            view.error( "Email is a mandatory field.", ErrorType.email );
        }else if( mobileNumber.length() == 0 ) {
            view.error( "Phone Number is a mandatory.", ErrorType.phoneNumber );
        }else if( password.length() == 0 ) {
            view.error( "Provide password please.", ErrorType.password );
        }else if( reenterPassword.length() == 0 ) {
            view.error( "Please reenter the first password.", ErrorType.password2 );
        }else if( !reenterPassword.equals( password ) ) {
            view.error( "Password does not match the first password.", ErrorType.password2 );
        }else{
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put( ProjectConfiguration.FullName, fullName );
                jsonObject.put( ProjectConfiguration.Email, email );
                jsonObject.put( ProjectConfiguration.MobileNumber, mobileNumber );
                jsonObject.put( ProjectConfiguration.password, password );
                jsonObject.put( ProjectConfiguration.reEnterPassword, reenterPassword );
                jsonObject.put( ProjectConfiguration.CountryCode, countryCode );
                AccountServiceLayer.register(jsonObject, new ReturnBooleanCallback() {
                    @Override
                    public void onSuccess(Boolean response) {
                        if(response == true){
                            view.successful("Registration successful");
                        }else{
                            view.error( "Failed to register you", ErrorType.login );
                        }
                    }
                
                    @Override
                    public void onError(String error) {
                        view.error( error+"", ErrorType.login );
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void getCountryCodes(){
        AccountServiceLayer.getCountryCodes(new ReturnCountriesCallback() {
            @Override
            public void onSuccess(List<Country> countryObject) {
                view.successful( countryObject );
            }
    
            @Override
            public void onError(String error) {
                view.error( error, ErrorType.countryCode );
            }
        });
    }
    
    
    
    
    public interface RegisterView{
        void error( String error, ErrorType errorType );
        void successful( List<Country> countryObject );
        void successful( String message );
    }
    
    public enum ErrorType{
        name, email, password, password2, countryCode, phoneNumber, login
    }
}
