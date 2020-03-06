package com.objects;

import android.media.Image;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductData implements Serializable {
    private String Status;
    
    private String CategoryID;
    
    private String UploadedDate;
    
    private String Warranty;
    
    private String Description;
    
    private String Currency;
    
    private Map<String, Object> Images = new HashMap<>();
    
    private String Title;
    
    private String ProductID;
    
    private Map<String, Object> Specification = new HashMap<>();
    
    private String Brand;
    
    private String EAN;
    
    private String Price;
    
    private Map<String, Object> ImagesThumb = new HashMap<>();
    
    private String SKU;
    
    private String Stock;
    
    public String getStatus ()
    {
        return Status;
    }
    
    public void setStatus (String Status)
    {
        this.Status = Status;
    }
    
    public String getCategoryID ()
    {
        return CategoryID;
    }
    
    public void setCategoryID (String CategoryID)
    {
        this.CategoryID = CategoryID;
    }
    
    public String getUploadedDate ()
    {
        return UploadedDate;
    }
    
    public void setUploadedDate (String UploadedDate)
    {
        this.UploadedDate = UploadedDate;
    }
    
    public String getWarranty ()
    {
        return Warranty;
    }
    
    public void setWarranty (String Warranty)
    {
        this.Warranty = Warranty;
    }
    
    public String getDescription ()
    {
        return Description;
    }
    
    public void setDescription (String Description)
    {
        this.Description = Description;
    }
    
    public String getCurrency() {
        return Currency;
    }
    
    public void setCurrency(String currency) {
        Currency = currency;
    }
    
    public Map<String, Object> getImages ()
    {
        return Images;
    }
    
    public void setImages (String key, String value)
    {
        Images.put( key, value );
    }
    
    public String getTitle ()
    {
        return Title;
    }
    
    public void setTitle (String Title)
    {
        this.Title = Title;
    }
    
    public String getProductID ()
    {
        return ProductID;
    }
    
    public void setProductID (String ProductID)
    {
        this.ProductID = ProductID;
    }
    
    public Map<String, Object> getSpecification ()
    {
        return Specification;
    }
    
    public void setSpecification (String key, String value)
    {
        Specification.put(key, value);
    }
    
    public String getBrand ()
    {
        return Brand;
    }
    
    public void setBrand (String Brand)
    {
        this.Brand = Brand;
    }
    
    public String getEAN ()
    {
        return EAN;
    }
    
    public void setEAN (String EAN)
    {
        this.EAN = EAN;
    }
    
    public String getPrice ()
    {
        return Price;
    }
    
    public void setPrice (String Price)
    {
        this.Price = Price;
    }
    
    public Map<String, Object> getImagesThumb ()
    {
        return ImagesThumb;
    }
    
    public void setImagesThumb (String key, String value)
    {
        ImagesThumb.put(key, value);
    }
    
    public String getSKU ()
    {
        return SKU;
    }
    
    public void setSKU (String SKU)
    {
        this.SKU = SKU;
    }
    
    public String getStock ()
    {
        return Stock;
    }
    
    public void setStock (String Stock)
    {
        this.Stock = Stock;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [Status = "+Status+", CategoryID = "+CategoryID+", UploadedDate = "+UploadedDate+", Warranty = "+Warranty+", Description = "+Description+", Images = "+Images+", Title = "+Title+", ProductID = "+ProductID+", Specification = "+Specification+", Brand = "+Brand+", EAN = "+EAN+", Price = "+Price+", ImagesThumb = "+ImagesThumb+", SKU = "+SKU+", Stock = "+Stock+"]";
    }
    
}
