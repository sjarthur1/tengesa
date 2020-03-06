package com.objects;

import java.io.Serializable;

public class Orders implements Serializable {
    private String _id;
    private String OrderID;
    private String OrderedDate;
    private String OrderUserID;
    private String OrderAmount;
    private String OrderAddressID;
    private ListUserOrderDetailsObject ListUserOrderDetails;
    private OrderShippingDetailsListObject  OrderShippingDetailsList;
    
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
    
    public String get_id() {
        return _id;
    }
    
    public void set_id(String _id) {
        this._id = _id;
    }
    
    public String getOrderUserID() {
        return OrderUserID;
    }
    
    public void setOrderUserID(String orderUserID) {
        OrderUserID = orderUserID;
    }
    
    public String getOrderAmount() {
        return OrderAmount;
    }
    
    public void setOrderAmount(String orderAmount) {
        OrderAmount = orderAmount;
    }
    
    public String getOrderAddressID() {
        return OrderAddressID;
    }
    
    public void setOrderAddressID(String orderAddressID) {
        OrderAddressID = orderAddressID;
    }
    
    public ListUserOrderDetailsObject getListUserOrderDetails() {
        return ListUserOrderDetails;
    }
    
    public void setListUserOrderDetails(ListUserOrderDetailsObject ListUserOrderDetails) {
        this.ListUserOrderDetails = ListUserOrderDetails;
    }
    
    public OrderShippingDetailsListObject getOrderShippingDetailsList() {
        return OrderShippingDetailsList;
    }
    
    public void setOrderShippingDetailsList(OrderShippingDetailsListObject OrderShippingDetailsList) {
        this.OrderShippingDetailsList = OrderShippingDetailsList;
    }
    
    public class ListUserOrderDetailsObject implements Serializable{
        private String OrderDetailsID, OrderShippingStatus, ImageThumbUrl, ProductTitle, SellerName, ProductID, Price, Quantity;
    
        public String getOrderDetailsID() {
            return OrderDetailsID;
        }
    
        public void setOrderDetailsID(String orderDetailsID) {
            OrderDetailsID = orderDetailsID;
        }
    
        public String getOrderShippingStatus() {
            return OrderShippingStatus;
        }
    
        public void setOrderShippingStatus(String orderShippingStatus) {
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
    
        public String getPrice() {
            return Price;
        }
    
        public void setPrice(String price) {
            Price = price;
        }
    
        public String getQuantity() {
            return Quantity;
        }
    
        public void setQuantity(String quantity) {
            Quantity = quantity;
        }
    }
    
    public class OrderShippingDetailsListObject implements Serializable{
        private String OrderShippingDetailsID;
        private String OrderDetailID;
        private String ShipperID;
        private String OrderStatus;
        private String TrackingNumber;
        private String ShippedDate;
        private String DeliveredDate;
    
        public String getOrderShippingDetailsID() {
            return OrderShippingDetailsID;
        }
    
        public void setOrderShippingDetailsID(String orderShippingDetailsID) {
            OrderShippingDetailsID = orderShippingDetailsID;
        }
    
        public String getOrderDetailID() {
            return OrderDetailID;
        }
    
        public void setOrderDetailID(String orderDetailID) {
            OrderDetailID = orderDetailID;
        }
    
        public String getShipperID() {
            return ShipperID;
        }
    
        public void setShipperID(String shipperID) {
            ShipperID = shipperID;
        }
    
        public String getOrderStatus() {
            return OrderStatus;
        }
    
        public void setOrderStatus(String orderStatus) {
            OrderStatus = orderStatus;
        }
    
        public String getTrackingNumber() {
            return TrackingNumber;
        }
    
        public void setTrackingNumber(String trackingNumber) {
            TrackingNumber = trackingNumber;
        }
    
        public String getShippedDate() {
            return ShippedDate;
        }
    
        public void setShippedDate(String shippedDate) {
            ShippedDate = shippedDate;
        }
    
        public String getDeliveredDate() {
            return DeliveredDate;
        }
    
        public void setDeliveredDate(String deliveredDate) {
            DeliveredDate = deliveredDate;
        }
    }
}
