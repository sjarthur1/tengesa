package com.constants;

import android.content.Context;
import android.widget.ImageView;

import com.mobile.tengesa.R;
import com.squareup.picasso.Picasso;

public class ProjectConfiguration {
    public static final String CONTENT_TYPE        = "Content-type";
    public static final String ACCEPT_LANGUAGE     = "Accept-Language";
    public static final String AUTHORIZATION       = "Authorization";
    public static final String ACCEPT              = "Accept";
    
    public static final String BASIC_BMLWCZ_IW_MTK = "Basic dGVuZ2VzYWRvdGNvbQ==";
    public static final String EN_US               = "en-US";
    public static final String APPLICATION_JSON    = "application/json";
    
    public static final String userId              = "UserId";
    public static final String userID              = "UserID";
    public static final String username            = "Username";
    public static final String password            = "Password";
    public static final String reEnterPassword     = "Password";
    public static final String oldPassword         = "OldPassword";
    public static final String confirmPassword     = "ConfirmPassword";
    public static final String productId           = "ProductID";
    public static final String quantity            = "Quantity";
    public static final String localCartProducts   = "localCartProducts";
    
    //the page types for error
    public static final String Email               = "Email";
    public static final String MobileNumber        = "MobileNumber";
    public static final String CountryCode         = "CountryCode";
    public static final String name                = "name";
    public static final String FullName            = "Fullname";
    public static final String mobile              = "mobile_number";
    public static final String country             = "country";
    public static final String CountryOfResidence  = "CountryOfResidence";
    public static final String city                = "city";
    public static final String area                = "area";
    public static final String street              = "street";
    public static final String locationType        = "locationType";
    public static final String address1            = "address1";
    public static final String birthDate           = "BirthDate";
    public static final String ADDRESS             = "Address";
    public static final String gender              = "Gender";
    public static final String banner_image        = "BANNER_IMAGE";
    
    public static final String _id                    = "_id";
    public static final String orderId                = "OrderId";
    public static final String OrderUserID            = "OrderUserID";
    public static final String orderAmount            = "OrderAmount";
    public static final String OrderAddressID         = "OrderAddressID";
    public static final String AdditionalShippingCost = "AdditionalShippingCost";
    public static final String OrderDateTime          = "AOrderDateTime";
    public static final String OrderDetailsList       = "OrderDetailsList";
    
    
    public static final String UserDetails        = "UserDetails";
    public static final String AccountDetails     = "AccountDetails";
    public static final String DD_MM_YYYY         = "dd/mm/yyyy";
    
    
    public static final String PAGE                = "PAGE";
    public static final String page_address        = "PAGE_ADDRESS";
    public static final String page_account        = "PAGE_ACCOUNT";
    public static final String page_account_detail = "PAGE_ACCOUNT_DETAIL";
    public static final String page_categories     = "PAGE_CATEGORIES";
    public static final String page_select_address = "PAGE_SELECT_ADDRESS";
    public static final String page_home           = "PAGE_HOME";
    public static final String page_my_cart        = "PAGE_MY_CART";
    public static final String page_login          = "PAGE_LOGIN";
    public static final String page_orders         = "PAGE_ORDERS";
    public static final String page_wish_list      = "PAGE_WISH_LIST";
    
    public static final String message        = "MESSAGE";
    public static final String error_message  = "ERROR_MESSAGE";
    
    public static void setLogo(ImageView imageView){
        Picasso.get().load( R.drawable.tengesadotcomwhite ).into(imageView);
    }
    
    public static void setLogoColored(ImageView imageView){
        Picasso.get().load( R.drawable.tengesa_dot_com_blue ).into(imageView);
    }
    
    public static void setAddressLogo(ImageView imageView){
        Picasso.get().load( R.drawable.top_address ).into(imageView);
    }
    
    public static void checkConnection(ImageView imageView){
        //Netw
    }
    
}
