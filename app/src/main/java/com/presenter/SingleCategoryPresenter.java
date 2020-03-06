package com.presenter;

import android.util.Log;
import com.network_layer.ProductServiceLayer;
import com.network_layer.callback.ProductDataCallback;
import com.objects.ProductData;

import java.util.ArrayList;
import java.util.List;

public class SingleCategoryPresenter {
    
    private CategoryView view;
    public List<ProductData> productLists;
    public SingleCategoryPresenter(CategoryView view){
        this.view = view;
        productLists = new ArrayList<>();
    }
    
    public List<ProductData> getProductLists(){
        return productLists;
    }
    
    public void getCategoryById( String categoryId ){
        
        ProductServiceLayer.getProductsByCategoryID(categoryId, new ProductDataCallback() {
            @Override
            public void onSuccess(ProductData productData) {
            
            }
            
            @Override
            public void onSuccess( List<ProductData> productList ) {
                productLists = productList;
                view.updateInterface(productList);
            }
            
            @Override
            public void onError(String error) {
            
            }
        });
    }
    
    public void getSearchProductResults( String searchText ){
        ProductServiceLayer.getSearchProductResults(searchText, new ProductDataCallback() {
            @Override
            public void onSuccess(ProductData productData) {
        
            }
    
            @Override
            public void onSuccess(List<ProductData> productData) {
                productLists = productData;
                view.updateInterface( productData );
            }
    
            @Override
            public void onError(String error) {
        
            }
        });
    }
    public interface CategoryView{
        public void updateInterface( List<ProductData> productList );
    }
}
