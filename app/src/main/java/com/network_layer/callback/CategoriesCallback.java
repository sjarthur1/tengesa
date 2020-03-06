package com.network_layer.callback;

import com.objects.HomeProductCategories;
import com.objects.ProductData;
import com.objects.list_objects.Categories;

import java.util.List;

public interface CategoriesCallback {
    public void onSuccess(Categories categories);
    public void onSuccess(List<HomeProductCategories> HomeProductCategories);
    public void onError(String error);
}
