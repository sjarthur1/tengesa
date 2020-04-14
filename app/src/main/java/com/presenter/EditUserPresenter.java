package com.presenter;

import android.content.Context;
import android.widget.Toast;

import com.constants.ErrorField;
import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.network_layer.AccountServiceLayer;
import com.network_layer.callback.ReturnCountriesCallback;
import com.network_layer.callback.ReturnStringCallback;
import com.objects.list_objects.Country;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class EditUserPresenter {
    
    private Context context;
    private EditUserView view;
    private String userId;
    public EditUserPresenter(Context context, EditUserView view){
        this.context = context;
        this.view = view;
        userId = PreferenceManagement.readString( context, ProjectConfiguration.userId, null );
    }
    
    public void editPersonalData(JSONObject jsonObject){
        try {
            String name = jsonObject.getString(ProjectConfiguration.FullName);
            String birthDate = jsonObject.getString(ProjectConfiguration.birthDate);
            String country = jsonObject.getString(ProjectConfiguration.CountryOfResidence);
            
            jsonObject.put( ProjectConfiguration.userID, userId );
            AccountServiceLayer.editPersonalInformation(jsonObject, context, new ReturnStringCallback() {
                @Override
                public void onSuccess(String response) {
                    view.successful(response);
                }
        
                @Override
                public void onError(String error) {
            
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    public void getCountryCodes(){
        AccountServiceLayer.getCountryCodes( true, context, new ReturnCountriesCallback() {
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
    
    
    
    public interface EditUserView{
        void setDate(String date);
        void successful(List<Country> countryObject);
        void successful(String message);
        void failure(String message, ErrorField field);
    }
}
