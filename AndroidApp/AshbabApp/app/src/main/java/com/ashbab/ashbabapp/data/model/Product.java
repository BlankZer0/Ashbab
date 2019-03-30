package com.ashbab.ashbabapp.data.model;

public class Product
{
    private int productID;
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

    public Product(int productID, String productName, float productPrice, String imageUrl, String model3dUrl, String category, String description)
    {
        this.productID = productID;
        this.productName = productName;
        this.productPrice = productPrice;
        this.imageUrl = imageUrl;
        this.model3dUrl = model3dUrl;
        this.category = category;
        this.description = description;
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

    public float getProductPrice()
    {
        return productPrice;
    }

    public void setProductPrice(float productPrice)
    {
        this.productPrice = productPrice;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getModel3dUrl()
    {
        return model3dUrl;
    }

    public void setModel3dUrl(String model3dUrl)
    {
        this.model3dUrl = model3dUrl;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
