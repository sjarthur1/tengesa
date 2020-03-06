package com.objects;

import retrofit2.http.Query;

public class WishListData {
    private String MongoId, Username, ProductID, ThumbnailProductImage, Title, Brand, Price;
    
    public String getMongoId() {
        return MongoId;
    }
    
    public void setMongoId(String mongoId) {
        MongoId = mongoId;
    }
    
    public String getUsername() {
        return Username;
    }
    
    public void setUsername(String username) {
        Username = username;
    }
    
    public String getProductID() {
        return ProductID;
    }
    
    public void setProductID(String productID) {
        ProductID = productID;
    }
    
    public String getThumbnailProductImage() {
        return ThumbnailProductImage;
    }
    
    public void setThumbnailProductImage(String thumbnailProductImage) {
        ThumbnailProductImage = thumbnailProductImage;
    }
    
    public String getTitle() {
        return Title;
    }
    
    public void setTitle(String title) {
        Title = title;
    }
    
    public String getBrand() {
        return Brand;
    }
    
    public void setBrand(String brand) {
        Brand = brand;
    }
    
    public String getPrice() {
        return Price;
    }
    
    public void setPrice(String price) {
        Price = price;
    }
}
