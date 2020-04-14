package com.objects;

public class CartObject {
    private String MongoId, Username, ProductID, ThumbnailProductImage, Title, Brand, Description, SoldBy;
    private int Quantity;
    private double Price;
    private double SubTotal;
    private double ShippingFee;
    private double TotalAmount;
    private int UserStock;
    
    
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
    
    public String getDescription() {
        return Description;
    }
    
    public void setDescription(String description) {
        Description = description;
    }
    
    public String getSoldBy() {
        return SoldBy;
    }
    
    public void setSoldBy(String soldBy) {
        SoldBy = soldBy;
    }
    
    public int getQuantity() {
        return Quantity;
    }
    
    public void setQuantity(int quantity) {
        Quantity = quantity;
    }
    
    public double getPrice() {
        return Price;
    }
    
    public void setPrice(double price) {
        Price = price;
    }
    
    public double getSubTotal() {
        return SubTotal;
    }
    
    public void setSubTotal(double subTotal) {
        SubTotal = subTotal;
    }
    
    public double getShippingFee() {
        return ShippingFee;
    }
    
    public void setShippingAmount(double shippingFee) {
        ShippingFee = shippingFee;
    }
    
    public double getTotalAmount() {
        return TotalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }
    
    public int getUserStock() {
        return UserStock;
    }
    
    public void setUserStock(int userStock) {
        UserStock = userStock;
    }
}
