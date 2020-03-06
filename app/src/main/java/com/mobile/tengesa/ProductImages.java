package com.mobile.tengesa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.squareup.picasso.Picasso;

public class ProductImages extends Fragment {
    private ImageView banner;
    private View view;
    private String path;
    private static ProductImages productImages;
    public static ProductImages newInstance(String path){
        productImages = new ProductImages();
        productImages.path = path;
        return productImages;
    }
    
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate( R.layout.product_images, container, false );
        
        banner = view.findViewById( R.id.image_view_slide );
    
        Picasso.get().load(path).into(banner);
        
        return view;
    }
    
    public void loadImages(String path){
    
    }
}
