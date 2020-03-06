package com.network_layer.callback;

import com.objects.ProductData;

import java.util.List;

public interface ProductDataCallback {
    public void onSuccess(ProductData productData);
    public void onSuccess(List<ProductData> productData);
    public void onError(String error);
}
