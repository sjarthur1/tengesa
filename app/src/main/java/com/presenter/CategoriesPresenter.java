package com.presenter;

import com.network_layer.ProductServiceLayer;
import com.network_layer.callback.CategoriesCallback;
import com.objects.HomeProductCategories;
import com.objects.list_objects.Categories;

import java.util.ArrayList;
import java.util.List;

public class CategoriesPresenter {
    
    private CategoriesView view;
    public ArrayList<Categories.MainView> category;
    public CategoriesPresenter(CategoriesView view){
        this.view = view;
        category = new ArrayList<>();
    }
    
    public ArrayList<Categories.MainView> getCurrentCategories(){
        return category;
    }
    
    public void getCategories(){
        ProductServiceLayer.getCategories( new CategoriesCallback() {
            @Override
            public void onSuccess( Categories categories ) {
                category = categories.getMainView();
                view.showCategories( categories );
            }
    
            @Override
            public void onSuccess(List<HomeProductCategories> HomeProductCategories) {
        
            }
    
            @Override
            public void onError( String error ) {
            
            }
        });
    }
    
    public interface CategoriesView{
        void showCategories(Categories categories);
    }
}
