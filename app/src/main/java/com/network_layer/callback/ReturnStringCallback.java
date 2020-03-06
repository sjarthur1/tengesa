package com.network_layer.callback;

import com.objects.ProductData;

public interface ReturnStringCallback {
    public void onSuccess( String response );
    public void onError( String error );
}
