package com.network_layer.callback;

import com.objects.UserAddresses;

import java.util.ArrayList;
import java.util.List;

public interface UserAddressListCallback {
    public void onSuccess(List<UserAddresses> userAddresses );
    public void onError( String error );
}
