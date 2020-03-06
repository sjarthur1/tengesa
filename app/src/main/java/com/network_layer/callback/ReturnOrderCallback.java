package com.network_layer.callback;

import com.objects.Orders;

public interface ReturnOrderCallback {
    void successful( Orders orders );
    void failure( String errorMessage );
}
