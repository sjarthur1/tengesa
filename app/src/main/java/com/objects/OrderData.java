package com.objects;

public class OrderData {
    private String SubTotal;
    private String ShippingAmount;
    private String TotalAmount;
    
    public String getSubTotal() {
        return SubTotal;
    }
    
    public void setSubTotal(String subTotal) {
        SubTotal = subTotal;
    }
    
    public String getShippingAmount() {
        return ShippingAmount;
    }
    
    public void setShippingAmount(String shippingAmount) {
        ShippingAmount = shippingAmount;
    }
    
    public String getTotalAmount() {
        return TotalAmount;
    }
    
    public void setTotalAmount(String totalAmount) {
        TotalAmount = totalAmount;
    }
}
