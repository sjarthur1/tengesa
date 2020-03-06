package com.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.constants.ActionOption;
import com.constants.ProjectConfiguration;
import com.helpers.ManageLocalProductIds;
import com.helpers.PreferenceManagement;
import com.network_layer.CartServiceLayer;
import com.network_layer.ProductServiceLayer;
import com.network_layer.callback.CartObjectCallback;
import com.network_layer.callback.CategoriesCallback;
import com.network_layer.callback.ProductDataCallback;
import com.network_layer.callback.ReturnStringCallback;
import com.network_layer.callback.ReturnStringListCallback;
import com.objects.CartObject;
import com.objects.HomeProductCategories;
import com.objects.ProductData;
import com.objects.list_objects.Categories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePresenter {
    private Context context;
    HomeInterface view;
    public List<HomeProductCategories> homeProductCategory, currentCategories;
    public ArrayList<List<ProductData>> currentProductList;
    public List<String> homeBannerList;
    
    public HomePresenter(Context context, HomeInterface view){
        this.context = context;
        this.view = view;
        homeProductCategory = new ArrayList<>();
        currentCategories = new ArrayList<>();
        currentProductList = new ArrayList<>();
        homeBannerList = new ArrayList<>();
    }
    
    public void onItemClicked(){
    
    }
    
    public void getHomeBanners(){
        ProductServiceLayer.getHomeBanners(new ReturnStringListCallback() {
            @Override
            public void onSuccess(List<String> response) {
                homeProductCategory.clear();
                currentProductList.clear();
                currentCategories.clear();
                homeBannerList.clear();
                homeBannerList = response;
                view.bannersReturned(response);
                
                getCategories();
            }
    
            @Override
            public void onError(String error) {
                view.failure( error );
            }
        });
    }
    
    public void getCategories(){
        
        ProductServiceLayer.getHomeProducts(new CategoriesCallback() {
            @Override
            public void onSuccess(Categories categories) {
        
            }
    
            @Override
            public void onSuccess(List<HomeProductCategories> homeProductCategories) {
                homeProductCategory.addAll( homeProductCategories );
                for (int round = 0; round < homeProductCategories.size(); round++){
                    currentCategories.add(homeProductCategories.get(round));
                    getCategoryById( homeProductCategories.get(round).getCategoryID(), round );
                }
                //view.successful( homeProductCategories );
            }
    
            @Override
            public void onError(String error) {
                view.failure( error );
            }
        });
    }
    
    public void getCategoryById(String categoryId, final int round){
        ProductServiceLayer.getProductsByCategoryID(categoryId, new ProductDataCallback() {
            @Override
            public void onSuccess(ProductData productData) {
            
            }
            
            @Override
            public void onSuccess( List<ProductData> productList ) {
                currentProductList.add( productList );
                view.successful( productList, round );
            }
            
            @Override
            public void onError(String error) {
            
            }
        });
    }
    
    
    
    public interface HomeInterface{
        void successful( List<HomeProductCategories> categories );
        void bannersReturned( List<String> banners );
        void successful( List<ProductData> categories, int round );
        void failure( String message );
    }
}
