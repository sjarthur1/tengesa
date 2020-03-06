package com.objects.list_objects;

import java.util.ArrayList;

public class Categories {
    private ArrayList<SideView> SideView;
    private ArrayList<MainView> MainView;
    
    public ArrayList<SideView> getSideView ()
    {
        return SideView;
    }
    
    public void setSideView (ArrayList<SideView> SideView)
    {
        this.SideView = SideView;
    }
    
    public ArrayList<MainView> getMainView ()
    {
        return MainView;
    }
    
    public void setMainView (ArrayList<MainView> MainView)
    {
        this.MainView = MainView;
    }
    
    @Override
    public String toString()
    {
        return "ClassPojo [SideView = "+SideView+", MainView = "+MainView+"]";
    }
    
    public class MainView
    {
        private String CategoryID;
        
        private String CategoryName;
        
        private String ImageURL;
        
        public String getCategoryID ()
        {
            return CategoryID;
        }
        
        public void setCategoryID (String CategoryID)
        {
            this.CategoryID = CategoryID;
        }
        
        public String getCategoryName ()
        {
            return CategoryName;
        }
        
        public void setCategoryName (String CategoryName)
        {
            this.CategoryName = CategoryName;
        }
        
        public String getImageURL ()
        {
            return ImageURL;
        }
        
        public void setImageURL (String ImageURL)
        {
            this.ImageURL = ImageURL;
        }
        
        @Override
        public String toString()
        {
            return "ClassPojo [CategoryID = "+CategoryID+", CategoryName = "+CategoryName+", ImageURL = "+ImageURL+"]";
        }
    }
    
    public class SideView {
        private String CategoryID;
        private String CategoryName;
        
        public String getCategoryID () {
            return CategoryID;
        }
        
        public void setCategoryID (String CategoryID)
        {
            this.CategoryID = CategoryID;
        }
        
        public String getCategoryName ()
        {
            return CategoryName;
        }
        
        public void setCategoryName (String CategoryName)
        {
            this.CategoryName = CategoryName;
        }
        
        @Override
        public String toString()
        {
            return "ClassPojo [CategoryID = "+CategoryID+", CategoryName = "+CategoryName+"]";
        }
    }
}
