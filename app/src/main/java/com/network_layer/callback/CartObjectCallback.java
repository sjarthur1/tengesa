package com.network_layer.callback;

import com.objects.CartObject;

import java.util.List;

public interface CartObjectCallback {
    void onSuccess( List<CartObject> cartObject );
    void onError( String error );
}
