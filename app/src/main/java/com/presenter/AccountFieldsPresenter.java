package com.presenter;

import android.content.Context;
import android.widget.ProgressBar;

import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.network_layer.AccountServiceLayer;
import com.network_layer.callback.UserDetailsCallBack;
import com.objects.UserDetails;

public class AccountFieldsPresenter {
    private Context context;
    private AccountFieldsView view;
    public UserDetails userDetails;
    private String userId;
    
    public AccountFieldsPresenter(Context context, AccountFieldsView view) {
        this.context = context;
        this.view = view;
        userId = PreferenceManagement.readString( context, ProjectConfiguration.userId, null);
    }
    
    public void getAccountDetails(){
        AccountServiceLayer.getUserAccount(userId, context, new UserDetailsCallBack() {
            @Override
            public void onSuccess(UserDetails userDetails1) {
                if(userDetails1 != null) {
                    userDetails = userDetails1;
                    view.accountInformation( userDetails);
                }else
                    view.failure("No your information is not valid");
            }
            
            @Override
            public void onError(String error) {
                view.failure( error );
            }
        });
    }
    public interface AccountFieldsView{
        void accountInformation(UserDetails userDetails);
        void failure( String errorMessage );
    }
}
