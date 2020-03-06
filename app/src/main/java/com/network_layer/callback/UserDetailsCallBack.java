package com.network_layer.callback;

import com.objects.ProductData;
import com.objects.UserDetails;

public interface UserDetailsCallBack {
    void onSuccess(UserDetails userDetails);
    void onError(String error);
}
