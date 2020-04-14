package com.presenter;

import android.content.Context;
import com.constants.ErrorField;
import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.network_layer.AccountServiceLayer;
import com.network_layer.callback.ReturnStringCallback;
import org.json.JSONObject;

public class EditPasswordPresenter {
    
    private Context context;
    private EditPasswordView view;
    public EditPasswordPresenter(Context context, EditPasswordView view) {
        this.context = context;
        this.view = view;
    }
    
    public void editPassword(JSONObject jsonObject){
        try {
            String oldPassword = jsonObject.getString(ProjectConfiguration.oldPassword);
            String password = jsonObject.getString(ProjectConfiguration.password );
            String confirmPassword = jsonObject.getString(ProjectConfiguration.confirmPassword);
            if( oldPassword == null || oldPassword.equals("") ){
                view.failure("Old password field should not be left blank", ErrorField.oldPassword);
            }else if( password == null || password.equals("") ){
                view.failure("Please provide your valid password", ErrorField.password);
            }else if( password.length() < 6 ){
                view.failure("Your password is too weak", ErrorField.password);
            }else if( !confirmPassword.equals(password) ){
                view.failure("Password miss-match", ErrorField.confirmPassword);
            }else {
                jsonObject.remove( ProjectConfiguration.confirmPassword );
                /*String userId = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
                jsonObject.put(ProjectConfiguration.userId, userId);*/
                AccountServiceLayer.editPassword(jsonObject, context, new ReturnStringCallback() {
                    @Override
                    public void onSuccess(String response) {
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
    
    public interface EditPasswordView{
        void successful(String message);
        void failure(String message, ErrorField field);
    }
}
