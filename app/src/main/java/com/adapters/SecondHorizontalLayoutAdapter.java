package com.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fragments.FragmentProduct;
import com.mobile.tengesa.R;
import com.objects.ProductData;
import com.presenter.StartFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SecondHorizontalLayoutAdapter extends RecyclerView.Adapter<SecondHorizontalLayoutAdapter.SecondHorizontalLayoutViewHolder> {
    private Context context;
    private List<ProductData> itemList;
    private FragmentManager fragmentManager;
    
    public SecondHorizontalLayoutAdapter(Context context, List<ProductData> itemList, FragmentManager fragmentManager){
        this.context = context;
        this.itemList = itemList;
        this.fragmentManager = fragmentManager;
    }
    
    @NonNull
    @Override
    public SecondHorizontalLayoutAdapter.SecondHorizontalLayoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SecondHorizontalLayoutViewHolder( LayoutInflater.from( context ).inflate( R.layout.layout_two_adapter_item, parent, false ) );
    }
    
    @Override
    public void onBindViewHolder(@NonNull SecondHorizontalLayoutAdapter.SecondHorizontalLayoutViewHolder holder, int position ) {
        final ProductData productData = itemList.get( position );
        String imageKey = productData.getImages().entrySet().iterator().next().getKey();
        String imageValue = productData.getImages().get(imageKey).toString();
        Picasso.get().load(imageValue).into(holder.imageViewItemImage);
        holder.textViewGadget.setText( itemList.get(position).getTitle().trim() );
        holder.textViewPrice.setText( itemList.get(position).getPrice().trim() );
    
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = FragmentProduct.newInstance();
                Bundle bundle = new Bundle();
                bundle.putSerializable("product", productData);
                fragment.setArguments(bundle);
                StartFragment.startFragment( fragmentManager, "Product Details", fragment );
            }
        };
        holder.imageViewItemImage.setOnClickListener(onClickListener);
        holder.textViewGadget.setOnClickListener(onClickListener);
        holder.textViewPrice.setOnClickListener(onClickListener);
    }
    
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    
    public class SecondHorizontalLayoutViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewItemImage, imageViewLike;
        TextView textViewGadget, textViewPrice;
        
        public SecondHorizontalLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItemImage = itemView.findViewById( R.id.image_view_item_image );
            imageViewLike      = itemView.findViewById( R.id.image_view_like );
            textViewGadget     = itemView.findViewById( R.id.text_view_item );
            textViewPrice      = itemView.findViewById( R.id.text_view_price );
        }
    }
}
