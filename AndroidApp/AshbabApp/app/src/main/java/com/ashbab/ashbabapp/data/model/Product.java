package com.ashbab.ashbabapp.data.model;

public class Product
{
    private int productID;
    private String productName;
    private int productPrice;
    private int imageUrl;

    public Product(String productName, int productPrice, int imageUrl)
    {
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageUrl = imageUrl;
    }

    public int getProductID()
    {
        return productID;
    }

    public void setProductID(int productID)
    {
        this.productID = productID;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public int getProductPrice()
    {
        return productPrice;
    }

    public void setProductPrice(int productPrice)
    {
        this.productPrice = productPrice;
    }

    public int getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl)
    {
        this.imageUrl = imageUrl;
    }
}
