package com.mobile.tengesa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.constants.ProjectConfiguration;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
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
        
        Bundle bundle = getArguments();
        
        if( bundle != null ) {
            String image = bundle.containsKey(ProjectConfiguration.banner_image) ? bundle.getString( ProjectConfiguration.banner_image ) : "";
            Picasso.get().load(image).resize(500, 300).networkPolicy(NetworkPolicy.NO_CACHE).memoryPolicy(MemoryPolicy.NO_CACHE).into(banner);
        }
        return view;
    }
}
