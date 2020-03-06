package com.network_layer.callback;

public interface ReturnBooleanCallback {
    public void onSuccess(Boolean response);
    public void onError(String error);
}
