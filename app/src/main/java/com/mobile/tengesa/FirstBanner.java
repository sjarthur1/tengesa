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

public class FirstBanner extends Fragment {
    private ImageView banner;
    private View view;
    private static FirstBanner firstBanner;
    public static FirstBanner newInstance(){
        firstBanner = new FirstBanner();
        return firstBanner;
        
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate( R.layout.first_banner, container, false );
        
        banner = view.findViewById( R.id.image_view_slide );
        
        Picasso.get().load("android.resource://com.mobile.tengesa/drawable/laptops").resize(500, 300).into(banner);
        
        return view;
    }
}
