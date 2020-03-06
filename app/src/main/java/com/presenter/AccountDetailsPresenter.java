package com.presenter;

import android.content.Context;
import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.network_layer.AccountServiceLayer;
import com.network_layer.callback.UserDetailsCallBack;
import com.objects.UserDetails;

public class AccountDetailsPresenter {
    public UserDetails userData, accountData;
    private Context context;
    private AccountDetailsView view;
    private String userId;
    public AccountDetailsPresenter(Context context, AccountDetailsView view){
        this.context = context;
        this.view = view;
        userId = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
    }
    
    public void getPersonalDetails(){
        AccountServiceLayer.getUserDetails(userId, new UserDetailsCallBack() {
            @Override
            public void onSuccess(UserDetails userDetails) {
                if(userDetails != null) {
                    userData = userDetails;
                    view.personalInformation(userDetails);
                }else
                    view.failure("No your information is not valid");
            }
    
            @Override
            public void onError(String error) {
                view.failure( error );
            }
        });
    }
    
    public void getAccountDetails(){
        AccountServiceLayer.getUserAccount(userId, new UserDetailsCallBack() {
            @Override
            public void onSuccess(UserDetails userDetails) {
                if(userDetails != null) {
                    accountData = userDetails;
                    view.accountInformation(userDetails);
                }else
                    view.failure("No your information is not valid");
            }
    
            @Override
            public void onError(String error) {
                view.failure( error );
            }
        });
    }
    
    
    public interface AccountDetailsView{
        void personalInformation( UserDetails userDetails );
        void accountInformation( UserDetails userDetails );
        void failure( String message );
    }
}
