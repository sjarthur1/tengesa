package com.helpers;

import android.content.Context;
import com.constants.ProjectConfiguration;

public class ManageLocalProductIds {
    public static boolean editProductIdList(Context context, String productIdAndQuantity){
        String productIdString = PreferenceManagement.readString(context, ProjectConfiguration.productId, null);
        if (productIdString != null)
            productIdString = productIdString.trim();
    
        if (productIdString == null) {
            productIdString = productIdAndQuantity;
        } else if (productIdString.equals("")) {
            productIdString = productIdAndQuantity;
        } else {
            productIdString = productIdString + "#" + productIdAndQuantity;
        }
        PreferenceManagement.writeString( context, ProjectConfiguration.productId, productIdString );
        return true;
    }
}
