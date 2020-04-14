package com.presenter;

import android.content.Context;

import com.network_layer.ProductServiceLayer;
import com.network_layer.callback.CategoriesCallback;
import com.network_layer.callback.ProductDataCallback;
import com.objects.HomeProductCategories;
import com.objects.ProductData;
import com.objects.list_objects.Categories;

import java.util.ArrayList;
import java.util.List;

public class HorizontalWithButtonPresenter {
    private Context context;
    HorizontalWithButtonView view;
    public List<ProductData> currentProductList;
    
    public HorizontalWithButtonPresenter( Context context, HorizontalWithButtonView view ){
        this.context = context;
        this.view = view;
        currentProductList = new ArrayList<>();
    }
    
    public void onItemClicked(){
    
    }
    
    public void getCategoryById(String categoryId, final int round){
        ProductServiceLayer.getProductsByCategoryID(categoryId, context, new ProductDataCallback() {
            @Override
            public void onSuccess(ProductData productData) {
            
            }
            
            @Override
            public void onSuccess( List<ProductData> productList ) {
                currentProductList = productList;
                view.successful( productList );
            }
            
            @Override
            public void onError(String error) {
            
            }
        });
    }
    
    
    public interface HorizontalWithButtonView{
        void successful( List<ProductData> productList );
        void failure(String error);
    }
}
