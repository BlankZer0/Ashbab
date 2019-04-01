package com.ashbab.ashbabapp.data.model;

/**
 * The Java Bean class of a product
 */
public class Product
{
    private String productID;
    private String productName;
    private float productPrice;
    private String imageUrl;
    private String model3dUrl;
    private String category;
    private String description;

    public Product()
    {
    }

    /**
     * This constructor is used for getting the data on the Home UI Cards
     */
    public Product(String productName, float productPrice, String imageUrl)
    {
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageUrl = imageUrl;
    }

    /**
     * This constructor is used to get the data for the Product Details Activity
     */
    public Product(String productID, String productName, float productPrice, String imageUrl, String model3dUrl, String category, String description)
    {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageUrl = imageUrl;
        this.model3dUrl = model3dUrl;
        this.category = category;
        this.description = description;
    }

    public String getProductID()
    {
        return productID;
    }

    public String getProductName()
    {
        return productName;
    }

    public float getProductPrice()
    {
        return productPrice;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public String getModel3dUrl()
    {
        return model3dUrl;
    }

    public String getCategory()
    {
        return category;
    }

    public String getDescription()
    {
        return description;
    }
}
