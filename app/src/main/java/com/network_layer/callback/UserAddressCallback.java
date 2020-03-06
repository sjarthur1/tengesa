package com.network_layer.callback;

import com.objects.UserAddresses;

public interface UserAddressCallback {
    public void onSuccess( UserAddresses userAddresses );
    public void onError( String error );
}
