package com.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.mobile.tengesa.R;
import com.objects.HomeProductCategories;
import com.objects.ProductData;
import com.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeAdapterHolder> {
    private Context context;
    private List<HomeProductCategories> arrayList;
    private HomePresenter presenter;
    private FragmentManager fragmentManager;
    public HomeAdapter(Context context, List<HomeProductCategories> arrayList, FragmentManager fragmentManager, HomePresenter presenter){
        this.context = context;
        this.arrayList = arrayList;
        this.presenter = presenter;
        this.fragmentManager = fragmentManager;
    }
    
    @NonNull
    @Override
    public HomeAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == 0){
            view = LayoutInflater.from( context ).inflate(R.layout.fragment_home_banner, parent, false);
        }else{
            view = LayoutInflater.from( context ).inflate(R.layout.fragment_horizontal_with_button, parent, false);
        }
        return new HomeAdapterHolder( view );
    }
    
    @Override
    public void onBindViewHolder(@NonNull HomeAdapterHolder holder, int position) {
        HomeProductCategories homeProductCategory = arrayList.get(position);
    
        try {
            if ( homeProductCategory.getCategoryDescription() != null) {
                holder.textViewCategory.setText(homeProductCategory.getCategoryDescription());
            }
        }catch ( Exception e ){
            e.printStackTrace();
        }
        if(position == 0) {
            try {
                List<String> bannerList = presenter.homeBannerList;
                MainBannerAdapter mainBannerAdapter = new MainBannerAdapter( fragmentManager, bannerList );
                holder.viewPagerBanner.setCurrentItem(0);
                holder.viewPagerBanner.setOffscreenPageLimit(2);
                holder.viewPagerBanner.setAdapter( mainBannerAdapter );
            }catch( Exception exc ){
                Log.e("Printing", exc.getLocalizedMessage());
            }
        } else {
            try {
                List<ProductData> items = presenter.currentProductList.get( position - 1 );
                FirstLayoutAdapter layoutAdapter = new FirstLayoutAdapter(context, items, fragmentManager);
                holder.recyclerViewList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
                holder.recyclerViewList.setAdapter(layoutAdapter);
            }catch (Exception e){
                e.printStackTrace();
            }
        }/* else {
            List<ProductData> items = presenter.currentProductList.get(position-1);
            SecondHorizontalLayoutAdapter secondHorizontalLayoutAdapter = new SecondHorizontalLayoutAdapter(context, items, fragmentManager);
            holder.recyclerViewList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
            holder.recyclerViewList.setAdapter( secondHorizontalLayoutAdapter );
        }*/
        
    }
    
    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    
    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        return position;
    }
    
    public class HomeAdapterHolder extends RecyclerView.ViewHolder{
        public RecyclerView recyclerViewList;
        public TextView textViewCategory, textViewSeeAll;
        ViewPager viewPagerBanner;
        
        public HomeAdapterHolder(@NonNull View itemView) {
            super(itemView);
            recyclerViewList = itemView.findViewById( R.id.recycler_view_list );
            textViewCategory = itemView.findViewById( R.id.text_view_category );
            textViewSeeAll   = itemView.findViewById( R.id.text_view_see_all );
            viewPagerBanner  = itemView.findViewById( R.id.view_pager_banner );
        }
    }
}
