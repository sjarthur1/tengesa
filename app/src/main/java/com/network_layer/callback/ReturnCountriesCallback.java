package com.network_layer.callback;

import com.objects.CartObject;
import com.objects.list_objects.Country;

import java.util.List;

public interface ReturnCountriesCallback {
    void onSuccess( List<Country> cartObject );
    void onError( String error );
}
