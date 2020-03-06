package com.network_layer;

public interface ReturnDoubleCallback {
    void successful( double amount );
    void failure( String message );
}
