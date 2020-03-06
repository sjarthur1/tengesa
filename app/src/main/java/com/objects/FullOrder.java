package com.objects;

import java.util.ArrayList;
import java.util.List;

public class FullOrder {
    private String OrderID;
    private String OrderedDate;
    private ArrayList<ListUserOrderDetailsObject> ListUserOrderDetails = new ArrayList<>();
    
    public String getOrderID() {
        return OrderID;
    }
    
    public void setOrderID(String orderID) {
        OrderID = orderID;
    }
    
    public String getOrderedDate() {
        return OrderedDate;
    }
    
    public void setOrderedDate(String orderedDate) {
        OrderedDate = orderedDate;
    }
    
    public ArrayList<ListUserOrderDetailsObject> getListUserOrderDetails() {
        return ListUserOrderDetails;
    }
    
    public void setListUserOrderDetails(ArrayList<ListUserOrderDetailsObject> listUserOrderDetails) {
        this.ListUserOrderDetails = listUserOrderDetails;
    }
    
    public class ListUserOrderDetailsObject{
        private String OrderDetailsID, ImageThumbUrl, ProductTitle, SellerName, ProductID;
        private double Price;
        private int Quantity, OrderShippingStatus;
    
        public String getOrderDetailsID() {
            return OrderDetailsID;
        }
    
        public void setOrderDetailsID(String orderDetailsID) {
            OrderDetailsID = orderDetailsID;
        }
    
        public int getOrderShippingStatus() {
            return OrderShippingStatus;
        }
    
        public void setOrderShippingStatus(int orderShippingStatus) {
            OrderShippingStatus = orderShippingStatus;
        }
    
        public String getImageThumbUrl() {
            return ImageThumbUrl;
        }
    
        public void setImageThumbUrl(String imageThumbUrl) {
            ImageThumbUrl = imageThumbUrl;
        }
    
        public String getProductTitle() {
            return ProductTitle;
        }
    
        public void setProductTitle(String productTitle) {
            ProductTitle = productTitle;
        }
    
        public String getSellerName() {
            return SellerName;
        }
    
        public void setSellerName(String sellerName) {
            SellerName = sellerName;
        }
    
        public String getProductID() {
            return ProductID;
        }
    
        public void setProductID(String productID) {
            ProductID = productID;
        }
    
        public double getPrice() {
            return Price;
        }
    
        public void setPrice(double price) {
            Price = price;
        }
    
        public int getQuantity() {
            return Quantity;
        }
    
        public void setQuantity(int quantity) {
            Quantity = quantity;
        }
    }
}
