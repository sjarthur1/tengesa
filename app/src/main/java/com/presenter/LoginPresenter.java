package com.presenter;

import android.content.Context;
import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.network_layer.AccountServiceLayer;
import com.network_layer.callback.UserDetailsCallBack;
import com.objects.UserDetails;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginPresenter {
    
    private LoginView view;
    private Context context;
    
    public LoginPresenter(Context context, LoginView view){
        this.context = context;
        this.view = view;
    }
    
    public void login(String email, final String password){
        if(email.length() == 0) {
            view.usernameError("Email is a mandatory field.");
        }else if(password.length() == 0) {
            view.passwordError("Provide password please.");
        }else{
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Email", email);
                jsonObject.put("Password", password);
                AccountServiceLayer.login(jsonObject, new UserDetailsCallBack() {
                    @Override
                    public void onSuccess(UserDetails userDetails) {
                        if(userDetails.getUserID() != null){
                            String userId = userDetails.getUserID();
                            PreferenceManagement.writeString( context, ProjectConfiguration.userId, userId);
                            view.successful();
                        }else{
                            view.passwordError("Wrong username or password");
                        }
                    }
                
                    @Override
                    public void onError(String error) {
                        view.loginError( error );
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    
    public interface LoginView{
        void usernameError(String error);
        void passwordError(String error);
        void loginError(String message);
        void successful();
    }
}
