package com.network_layer.callback;

import com.objects.FullOrder;

import java.util.List;

public interface ReturnOrderFullCallback {
    void successful(List<FullOrder> fullOrder );
    void failure( String errorMessage );
}
