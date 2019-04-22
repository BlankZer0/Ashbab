package com.ashbab.ashbabapp.data.model;

public class Order
{
    private String orderID;
    private String orderDate;
    private String productID;
    private String productName;
    private float productPrice;
    private String buyerName;
    private String buyerEmail;
    private String buyerAddress;
    private String orderFlag;

    public Order() {}

    public Order(String orderDate, String productID, String productName, float productPrice, String buyerName, String buyerEmail, String buyerAddress, String orderFlag)
    {
        this.orderDate = orderDate;
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.buyerAddress = buyerAddress;
        this.orderFlag = orderFlag;
    }

    public Order(String orderID, String orderDate, String productID, String productName, float productPrice, String buyerName, String buyerEmail, String buyerAddress, String orderFlag)
    {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.buyerAddress = buyerAddress;
        this.orderFlag = orderFlag;
    }

    public String getOrderID() { return orderID; }

    public String getOrderDate() { return orderDate; }

    public String getProductID() { return productID; }

    public String getProductName() { return productName; }

    public float getProductPrice() { return productPrice; }

    public String getBuyerName() { return buyerName; }

    public String getBuyerEmail() { return buyerEmail; }

    public String getBuyerAddress() { return buyerAddress; }

    public String getOrderFlag() { return orderFlag; }

    public void setOrderID(String orderID) { this.orderID = orderID; }

    public void setOrderDate(String orderDate) { this.orderDate = orderDate; }

    public void setProductID(String productID) { this.productID = productID; }

    public void setProductName(String productName) { this.productName = productName; }

    public void setProductPrice(float productPrice) { this.productPrice = productPrice; }

    public void setBuyerName(String buyerName) { this.buyerName = buyerName; }

    public void setBuyerEmail(String buyerEmail) { this.buyerEmail = buyerEmail; }

    public void setBuyerAddress(String buyerAddress) { this.buyerAddress = buyerAddress; }

    public void setOrderFlag(String orderFlag) { this.orderFlag = orderFlag; }
}
