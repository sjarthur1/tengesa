package com.presenter;

import com.network_layer.ProductServiceLayer;
import com.network_layer.callback.ProductDataCallback;
import com.objects.ProductData;

import java.util.ArrayList;
import java.util.List;

public class HorizontalWithoutButtonPresenter {
    HorizontalWithoutButtonView view;
    public List<ProductData> currentProductList;
    
    public HorizontalWithoutButtonPresenter( HorizontalWithoutButtonView view ){
        this.view = view;
        currentProductList = new ArrayList<>();
    }
    
    public void onItemClicked(){
    
    }
    
    public void getCategoryById(String categoryId, final int round){
        ProductServiceLayer.getProductsByCategoryID(categoryId, new ProductDataCallback() {
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
    
    
    public interface HorizontalWithoutButtonView{
        void successful( List<ProductData> productList );
        void failure(String error);
    }
}
