package com.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.constants.ProjectConfiguration;
import com.fragments.FragmentProduct;
import com.helpers.PreferenceManagement;
import com.mobile.tengesa.R;
import com.network_layer.WishListServiceLayer;
import com.objects.CartObject;
import com.presenter.MyCartPresenter;
import com.presenter.StartFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HorizontalListItemAdapter extends RecyclerView.Adapter<HorizontalListItemAdapter.HorizontalListItemViewHolder> {
    
    View view;
    private Context context;
    List<CartObject> cartList;
    FragmentManager fragmentManager;
    MyCartPresenter presenter;
    public HorizontalListItemAdapter(Context context, List<CartObject> cartList, MyCartPresenter presenter, FragmentManager fragmentManager) {
        this.context = context;
        this.cartList = cartList;
        this.fragmentManager = fragmentManager;
        this.presenter = presenter;
    }
    
    @NonNull
    @Override
    public HorizontalListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.horizontal_list_item, parent, false);
        return new HorizontalListItemViewHolder( view );
    }
    
    @Override
    public void onBindViewHolder(@NonNull final HorizontalListItemViewHolder holder, final int position) {
        final CartObject cartObject = cartList.get( position );
        holder.textViewItem.setText( cartObject.getTitle()+"" );
        holder.textViewPrice.setText( cartObject.getPrice()+"" );
        ArrayList<Integer> quantity = new ArrayList<>();
        int index = cartObject.getUserStock() > 0 ? 1 : 0;
        for(int count = index; count <= cartObject.getUserStock(); count++){
            quantity.add(count);
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, quantity);
        holder.spinnerQuantity.setAdapter(arrayAdapter);
        if(cartObject.getUserStock() >= cartObject.getQuantity())
            holder.spinnerQuantity.setSelection( cartObject.getQuantity() - 1 );
        Picasso.get().load(cartObject.getThumbnailProductImage()).into(holder.imageViewThumbnail);
        
        holder.spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    int item = (int) adapterView.getAdapter().getItem(i);
                    presenter.updateCart(position, item, cartObject.getMongoId());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
    
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            
            }
        });
        
        
    
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.image_view_cancel:
                        presenter.removeCartItem( cartObject.getProductID(), cartObject.getMongoId(), position );
                        break;
                    case R.id.button_add_to_wish_list:
                        presenter.saveToWishList( cartObject.getProductID() );
                        break;
                    default:
                        Fragment fragment = FragmentProduct.newInstance();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable( ProjectConfiguration.productId, cartObject.getProductID() );
                        fragment.setArguments(bundle);
                        StartFragment.startFragment(fragmentManager, "Product Details", fragment);
                        break;
                }
            }
        };
        
        holder.imageViewCancel.setOnClickListener( onClickListener );
        holder.buttonAddToWishList.setOnClickListener(onClickListener);
        holder.imageViewThumbnail.setOnClickListener( onClickListener );
        holder.textViewItem.setOnClickListener( onClickListener );
        holder.textViewPrice.setOnClickListener( onClickListener );
    }
    
    @Override
    public int getItemCount() {
        return cartList.size();
    }
    
    public class HorizontalListItemViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewThumbnail, imageViewCancel;
        TextView textViewItem, textViewPrice;
        Spinner spinnerQuantity;
        Button buttonAddToWishList;
        public HorizontalListItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewThumbnail = itemView.findViewById( R.id.image_view_thumbnail );
            imageViewCancel = itemView.findViewById( R.id.image_view_cancel );
            textViewItem = itemView.findViewById( R.id.text_view_item );
            textViewPrice = itemView.findViewById( R.id.text_view_price );
            spinnerQuantity = itemView.findViewById( R.id.spinner_quantity );
            buttonAddToWishList = itemView.findViewById( R.id.button_add_to_wish_list );
        }
    }
}
