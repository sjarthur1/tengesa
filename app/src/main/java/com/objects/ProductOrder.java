package com.objects;

public class ProductOrder {
    private String OrderDetailID = null;
    private String OrderID = null;
    private String ProductID;
    private String SellerID;
    private double UnitPrice;
    private float Quantity;
    private double TotalPrice;
    private double ShippingPrice;
    
    public ProductOrder( String productID, String sellerID, double unitPrice, float quantity, double totalPrice, double shippingPrice) {
        ProductID = productID;
        SellerID = sellerID;
        UnitPrice = unitPrice;
        Quantity = quantity;
        TotalPrice = totalPrice;
        ShippingPrice = shippingPrice;
    }
    
    // Getter Methods
    
    public String getOrderDetailID() {
        return OrderDetailID;
    }
    
    public String getOrderID() {
        return OrderID;
    }
    
    public String getProductID() {
        return ProductID;
    }
    
    public String getSellerID() {
        return SellerID;
    }
    
    public double getUnitPrice() {
        return UnitPrice;
    }
    
    public float getQuantity() {
        return Quantity;
    }
    
    public double getTotalPrice() {
        return TotalPrice;
    }
    
    public double getShippingPrice() {
        return ShippingPrice;
    }
    
    // Setter Methods
    
    public void setOrderDetailID(String OrderDetailID) {
        this.OrderDetailID = OrderDetailID;
    }
    
    public void setOrderID(String OrderID) {
        this.OrderID = OrderID;
    }
    
    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }
    
    public void setSellerID(String SellerID) {
        this.SellerID = SellerID;
    }
    
    public void setUnitPrice(double UnitPrice) {
        this.UnitPrice = UnitPrice;
    }
    
    public void setQuantity(float Quantity) {
        this.Quantity = Quantity;
    }
    
    public void setTotalPrice(double TotalPrice) {
        this.TotalPrice = TotalPrice;
    }
    
    public void setShippingPrice(double ShippingPrice) {
        this.ShippingPrice = ShippingPrice;
    }
}
