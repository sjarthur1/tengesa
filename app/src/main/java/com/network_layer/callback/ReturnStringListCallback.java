package com.network_layer.callback;

import java.util.List;

public interface ReturnStringListCallback {
    public void onSuccess( List<String> response );
    public void onError( String error );
}
