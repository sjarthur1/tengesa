package com.network_layer.callback;

public interface ReturnIntegerCallback {
    public void onSuccess( int response );
    public void onError( String error );
}
