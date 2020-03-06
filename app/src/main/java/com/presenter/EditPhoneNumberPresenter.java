package com.presenter;

import android.content.Context;
import com.constants.ErrorField;
import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.network_layer.AccountServiceLayer;
import com.network_layer.callback.ReturnCountriesCallback;
import com.network_layer.callback.ReturnStringCallback;
import com.objects.list_objects.Country;

import org.json.JSONObject;

import java.util.List;

public class EditPhoneNumberPresenter {
    
    private Context context;
    private EditPhoneNumberView view;
    
    public EditPhoneNumberPresenter(Context context, EditPhoneNumberView view) {
        this.context = context;
        this.view = view;
    }
    
    public void editMobileNumber(JSONObject jsonObject){
        try {
            String mobile = jsonObject.getString(ProjectConfiguration.MobileNumber);
            String password = jsonObject.getString(ProjectConfiguration.password );
            String countryCode = jsonObject.getString(ProjectConfiguration.CountryCode);
            if( mobile == null || mobile.equals("") ){
                view.failure("Mobile number should not be left blank", ErrorField.phoneNumber);
            }else if( mobile.length() < 8 ){
                view.failure("Please provide a valid mobile", ErrorField.phoneNumber);
            }else if( countryCode == null || countryCode.equals("") ){
                view.failure("Please select the country code of your country", ErrorField.phoneNumber);
            }else if( password == null || password.equals("") ){
                view.failure("Please provide your valid password", ErrorField.password);
            }else {
                jsonObject.remove(ProjectConfiguration.MobileNumber);
                long phone = Long.parseLong( mobile );
                String userId = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
                jsonObject.put( ProjectConfiguration.MobileNumber, phone );
                jsonObject.put(ProjectConfiguration.userId, userId);
                AccountServiceLayer.editMobileNumber(jsonObject, new ReturnStringCallback() {
                    @Override
                    public void onSuccess(String response) {
                        if(response.contains("Wrong"))
                            view.failure(response, ErrorField.general);
                        else if(response.contains("failed"))
                            view.failure(response, ErrorField.general);
                        else if(response.contains("error"))
                            view.failure(response, ErrorField.general);
                        else
                            view.successful(response);
                    }
        
                    @Override
                    public void onError(String error) {
                        view.failure(error, ErrorField.general);
                    }
                });
            }
        }catch (Exception e){
            view.failure("Please fill the required fields", ErrorField.general);
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
                view.failure( error, ErrorField.general );
            }
        });
    }
    
    public interface EditPhoneNumberView{
        void successful(String message);
        void failure(String message, ErrorField field);
        void successful(List<Country> countryObject);
    }
}
