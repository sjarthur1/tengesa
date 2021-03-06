package com.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.constants.ActionOption;
import com.constants.ProjectConfiguration;
import com.fragments.FragmentWishList;
import com.helpers.ManageLocalProductIds;
import com.helpers.PreferenceManagement;
import com.fragments.FragmentMyCart;
import com.fragments.FragmentProduct;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.network_layer.CartServiceLayer;
import com.network_layer.WishListServiceLayer;
import com.network_layer.callback.ReturnBooleanCallback;
import com.network_layer.callback.ReturnStringCallback;
import com.objects.ProductData;
import com.presenter.StartFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {
    
    private Context context;
    private List<ProductData> itemList;
    private FragmentManager fragmentManager;
    private Fragment newFragment;
    private AlertDialog alertDialog;
    
    public ItemListAdapter(Context context, List<ProductData> itemList, FragmentManager fragmentManager) {
        this.context = context;
        this.itemList = itemList;
        this.fragmentManager = fragmentManager;
    }
    
    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemListViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false ));
    }
    
    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        final ProductData productData = itemList.get( position );
        String imageKey = productData.getImagesThumb().entrySet().iterator().next().getKey();
        String imageValue = productData.getImagesThumb().get(imageKey).toString();
        Picasso.get().load(imageValue).into(holder.imageViewItemImage);
        holder.textViewGadget.setText( itemList.get(position).getTitle().trim() );
        holder.textViewPrice.setText( itemList.get(position).getPrice().trim() );
        holder.textViewCurrency.setText( productData.getCurrency().trim() );
    
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_add_to_cart:
                        addToCart(productData.getProductID(), "1", productData.getTitle());
                        break;
                    case R.id.image_view_like:
                        saveToWishList(productData.getProductID(), productData.getTitle());
                        break;
                    default:
                        Fragment fragment = FragmentProduct.newInstance();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("product", productData);
                        fragment.setArguments(bundle);
                        StartFragment.startFragment(fragmentManager, "Product Details", fragment);
                }
            }
        };
        holder.imageViewItemImage.setOnClickListener(onClickListener);
        holder.textViewGadget.setOnClickListener( onClickListener );
        holder.textViewPrice.setOnClickListener( onClickListener );
        holder.buttonAddToCart.setOnClickListener( onClickListener );
        holder.imageViewLike.setOnClickListener( onClickListener );
    }
    
    @Override
    public int getItemCount() {
        return itemList.size();
    }
    
    public class ItemListViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewItemImage, imageViewLike;
        TextView textViewGadget, textViewPrice, textViewCurrency;
        Button buttonAddToCart;
    
        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItemImage = itemView.findViewById( R.id.image_view_item_image );
            imageViewLike      = itemView.findViewById( R.id.image_view_like );
            textViewGadget     = itemView.findViewById( R.id.text_view_gadget );
            textViewPrice      = itemView.findViewById( R.id.text_view_price );
            textViewCurrency   = itemView.findViewById( R.id.text_view_currency );
            buttonAddToCart    = itemView.findViewById( R.id.button_add_to_cart );
        }
    }
    
    
    public void saveToWishList(String productId, final String product ){
        String username = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
        if( username != null ) {
            WishListServiceLayer.saveWishList(username, productId, context, new ReturnStringCallback() {
                @Override
                public void onSuccess(String response) {
                    launchDialog("Successful", product + " has been added to wishlist, do you wish to continue to wishlist?", ActionOption.wishlist);
                }
                
                @Override
                public void onError(String error) {
                    //view.failed( error );
                }
            });
        }else{
            Toast.makeText( context, "First login and save product to wishlist", Toast.LENGTH_LONG ).show();
        }
    }
    
    
    public void addToCart(String productId, String quantity, final String product ){
        int total = Integer.parseInt(quantity);
        if( total > 0) {
            String username = PreferenceManagement.readString(context, ProjectConfiguration.userId, null);
            if (username != null) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put( ProjectConfiguration.username,  username  );
                    jsonObject.put( ProjectConfiguration.productId, productId );
                    jsonObject.put( ProjectConfiguration.quantity,  quantity  );
                    CartServiceLayer.saveCart(jsonObject, context, new ReturnBooleanCallback() {
                        @Override
                        public void onSuccess(Boolean response) {
                            if( response ) {
                                launchDialog("Successful", product + " has been added to cart, do you wish to continue to cart?", ActionOption.cart);
                                MainActivity.getInstance().getCart();
                            }else{
                                Toast.makeText( context, product+" is already in the cart", Toast.LENGTH_LONG ).show();
                            }
                        }
    
                        @Override
                        public void onError(String error) {
                            Toast.makeText( context, error, Toast.LENGTH_LONG ).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                ManageLocalProductIds.editProductIdList( context, productId + "|" + quantity );
                MainActivity.getInstance().getCart();
                launchDialog( "Successful", product+" has been added to cart, do you wish to continue to cart?", ActionOption.cart );
            }
        }else{
            //view.failure("Select the quantity required to purchase.");
        }
    }
    
    public void launchDialog(String title, String message, final ActionOption action) {
        String buttonText = "Go to Wish List";
        if(action.equals(ActionOption.cart)){
            buttonText = "Go to Cart";
        }
        
        AlertDialog.Builder builder = new AlertDialog.Builder( context );
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false)
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(action.equals(ActionOption.cart)){
                            newFragment = FragmentMyCart.newInstance();
                            StartFragment.startFragment(fragmentManager, "Cart", newFragment);
                        }else if(action.equals(ActionOption.wishlist)){
                            newFragment = FragmentWishList.newInstance();
                            StartFragment.startFragment(fragmentManager, "Cart", newFragment);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();
        
        alertDialog.getWindow().setGravity(Gravity.TOP);
    }
}
