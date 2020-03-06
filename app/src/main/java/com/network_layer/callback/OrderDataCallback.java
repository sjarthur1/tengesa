package com.network_layer.callback;

import com.objects.OrderData;
import com.objects.UserDetails;

public interface OrderDataCallback {
    public void onSuccess(OrderData orderData);
    public void onError(String error);
}
