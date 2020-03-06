package com.network_layer.callback;

import com.objects.OrderData;
import com.objects.WishListData;

import java.util.List;

public interface WishListCallback {
    public void onSuccess(List<WishListData> wishListData);
    public void onError(String error);
}
