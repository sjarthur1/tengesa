package com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.tengesa.R;
import com.objects.CartObject;
import com.objects.WishListData;
import com.presenter.MyCartPresenter;
import com.presenter.WishlistPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class WishListItemAdapter extends RecyclerView.Adapter<WishListItemAdapter.WishListItemViewHolder> {
    View view;
    private Context context;
    List<WishListData> wishList;
    FragmentManager fragmentManager;
    WishlistPresenter presenter;
    
    public WishListItemAdapter(Context context, List<WishListData> wishList, FragmentManager fragmentManager, WishlistPresenter presenter) {
        this.context = context;
        this.wishList = wishList;
        this.fragmentManager = fragmentManager;
        this.presenter = presenter;
    }
    
    @NonNull
    @Override
    public WishListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WishListItemViewHolder(LayoutInflater.from(context).inflate(R.layout.wishlist_item, parent, false));
    }
    
    @Override
    public void onBindViewHolder(@NonNull WishListItemViewHolder holder, final int position) {
        final WishListData listData = wishList.get( position );
        holder.textViewItem.setText( listData.getTitle() );
        holder.textViewPrice.setText( listData.getPrice()+"" );
        Picasso.get().load(listData.getThumbnailProductImage()).into(holder.imageViewThumbnail);
    
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.image_view_cancel:
                        presenter.removeWishListItem( listData.getMongoId(), position );
                        break;
                    case R.id.button_add_to_cart:
                        presenter.addToCart( listData.getProductID(), 1+"", listData.getTitle() );
                        break;
                }
            }
        };
    
        holder.imageViewCancel.setOnClickListener( onClickListener );
        holder.buttonAddToCart.setOnClickListener(onClickListener);
    }
    
    @Override
    public int getItemCount() {
        return wishList.size();
    }
    
    
    public class WishListItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewThumbnail, imageViewCancel;
        TextView textViewItem, textViewPrice;
        Button buttonAddToCart;
        public WishListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewThumbnail = itemView.findViewById( R.id.image_view_thumbnail );
            imageViewCancel = itemView.findViewById( R.id.image_view_cancel );
            textViewItem = itemView.findViewById( R.id.text_view_item );
            textViewPrice = itemView.findViewById( R.id.text_view_price );
            buttonAddToCart = itemView.findViewById( R.id.button_add_to_cart );
        }
    }
}
