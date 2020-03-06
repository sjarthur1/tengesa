package com.presenter;

import android.content.Context;
import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.network_layer.AccountServiceLayer;
import com.network_layer.CartServiceLayer;
import com.network_layer.ReturnDoubleCallback;
import com.network_layer.callback.ReturnBooleanCallback;
import com.network_layer.callback.UserAddressListCallback;
import com.objects.UserAddresses;

import java.util.List;

public class AddressPresenter {
    
    private AddressView view;
    private Context context;
    public AddressPresenter( Context context, AddressView view ){
        this.context = context;
        this.view = view;
    }
    
    public void getUserAddress(){
        String userId = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
        if(userId == null){
            view.redirectToLogin();
        }else{
            AccountServiceLayer.getAddresses(userId, new UserAddressListCallback() {
                @Override
                public void onSuccess(List<UserAddresses> userAddresses) {
                    view.successful( userAddresses );
                }
    
                @Override
                public void onError(String error) {
                    view.failed( error );
                }
            });
        }
    
    }
    
    public void deleteAddress(String addressId, final int position ){
        AccountServiceLayer.deleteAddress(addressId, new ReturnBooleanCallback() {
            @Override
            public void onSuccess(Boolean response) {
                if( response )
                    view.successful( position );
            }
    
            @Override
            public void onError(String error) {
                view.failed( error );
            }
        });
    }
    
    public interface AddressView{
        void successful(List<UserAddresses> userAddresses);
        void successful(int position);
        void failed(String message);
        void redirectToLogin();
    }
}
