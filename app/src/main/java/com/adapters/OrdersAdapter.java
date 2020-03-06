package com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.tengesa.R;
import com.objects.FullOrder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    private Context context;
    private ArrayList<FullOrder.ListUserOrderDetailsObject> orderList;
    
    public OrdersAdapter(Context context, ArrayList<FullOrder.ListUserOrderDetailsObject> orderList) {
        this.context = context;
        this.orderList = orderList;
    }
    
    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrdersViewHolder( LayoutInflater.from(context).inflate( R.layout.order_item, parent, false ) );
    }
    
    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        FullOrder.ListUserOrderDetailsObject orderedObject = orderList.get(position);
        Picasso.get().load(orderedObject.getImageThumbUrl()).into(holder.imageViewThumbnail);
        holder.textViewItem.setText( orderedObject.getProductTitle() );
        
        holder.textViewShipmentId.setText( context.getString( R.string.shipment_id )+" "+orderedObject.getOrderDetailsID() );
        holder.textViewPrice.setText( "$"+orderedObject.getPrice() );
        String unitString = orderedObject.getQuantity() > 1 ? context.getString(R.string.units ) : context.getString(R.string.unit );
        holder.textViewQuantity.setText( orderedObject.getQuantity()+" "+unitString );
        holder.textViewSoldBy.setText( orderedObject.getSellerName()+"" );
        
        if( orderedObject.getOrderShippingStatus() == 0 ){
            holder.imageViewStatusOrdered.setImageResource( R.drawable.icon_status_active_background );
            holder.imageViewStatusProcessing.setImageResource( R.drawable.icon_status_pending );
            holder.imageViewStatusShipped.setImageResource( R.drawable.icon_status_pending );
            holder.imageViewStatusDelivered.setImageResource( R.drawable.icon_status_pending );
        }else if( orderedObject.getOrderShippingStatus() == 1 ){
            holder.imageViewStatusOrdered.setImageResource( R.drawable.icon_status_active_background );
            holder.imageViewStatusProcessing.setImageResource( R.drawable.icon_status_active_background );
            holder.imageViewStatusShipped.setImageResource( R.drawable.icon_status_pending );
            holder.imageViewStatusDelivered.setImageResource( R.drawable.icon_status_pending );
        }else if( orderedObject.getOrderShippingStatus() == 2 ){
            holder.imageViewStatusOrdered.setImageResource( R.drawable.icon_status_active_background );
            holder.imageViewStatusProcessing.setImageResource( R.drawable.icon_status_active_background );
            holder.imageViewStatusShipped.setImageResource( R.drawable.icon_status_active_background );
            holder.imageViewStatusDelivered.setImageResource( R.drawable.icon_status_pending );
        }else if( orderedObject.getOrderShippingStatus() == 3 ){
            holder.imageViewStatusOrdered.setImageResource( R.drawable.icon_status_active_background );
            holder.imageViewStatusProcessing.setImageResource( R.drawable.icon_status_active_background );
            holder.imageViewStatusShipped.setImageResource( R.drawable.icon_status_active_background );
            holder.imageViewStatusDelivered.setImageResource( R.drawable.icon_status_active_background );
        }else{
            holder.imageViewStatusOrdered.setImageResource( R.drawable.icon_status_pending );
            holder.imageViewStatusProcessing.setImageResource( R.drawable.icon_status_pending );
            holder.imageViewStatusShipped.setImageResource( R.drawable.icon_status_pending );
            holder.imageViewStatusDelivered.setImageResource( R.drawable.icon_status_pending );
        }
    }
    
    @Override
    public int getItemCount() {
        return orderList.size();
    }
    
    
    class OrdersViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewItem, textViewShipmentId, textViewPrice, textViewQuantity, textViewSoldBy;
        private ImageView imageViewThumbnail, imageViewStatusOrdered, imageViewStatusProcessing, imageViewStatusShipped, imageViewStatusDelivered;
        
        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewItem              = itemView.findViewById( R.id.text_view_item );
            textViewShipmentId        = itemView.findViewById( R.id.text_view_shipment_id );
            textViewPrice             = itemView.findViewById( R.id.text_view_price );
            textViewQuantity          = itemView.findViewById( R.id.text_view_quantity );
            textViewSoldBy            = itemView.findViewById( R.id.text_view_sold_by );
            imageViewThumbnail        = itemView.findViewById( R.id.image_view_thumbnail );
            imageViewStatusOrdered    = itemView.findViewById( R.id.image_view_status_ordered );
            imageViewStatusProcessing = itemView.findViewById( R.id.image_view_status_processing );
            imageViewStatusShipped    = itemView.findViewById( R.id.image_view_status_shipped );
            imageViewStatusDelivered  = itemView.findViewById( R.id.image_view_status_delivered );
        }
    }
}
