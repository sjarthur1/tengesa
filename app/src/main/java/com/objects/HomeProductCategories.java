package com.objects;

public class HomeProductCategories {
    private String CategoryID;
    private String CategoryDescription;
    
    public HomeProductCategories( String CategoryID){
        this.CategoryID = getCategoryID();
    }
    
    public String getCategoryID() {
        return CategoryID;
    }
    
    public void setCategoryID(String categoryID) {
        CategoryID = categoryID;
    }
    
    public String getCategoryDescription() {
        return CategoryDescription;
    }
    
    public void setCategoryDescription(String categoryDescription) {
        CategoryDescription = categoryDescription;
    }
}
