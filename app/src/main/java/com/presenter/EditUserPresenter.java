package com.presenter;

import android.content.Context;
import android.widget.Toast;
import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.network_layer.AccountServiceLayer;
import com.network_layer.callback.ReturnStringCallback;
import org.json.JSONException;
import org.json.JSONObject;

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
            AccountServiceLayer.editPersonalInformation(jsonObject, new ReturnStringCallback() {
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
    
    
    
    public interface EditUserView{
        void setDate(String date);
        void successful(String message);
    }
}
