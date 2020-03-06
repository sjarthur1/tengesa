package com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.tengesa.R;

import java.util.ArrayList;

public class PagerAdapter extends RecyclerView.Adapter<PagerAdapter.PagerAdapterViewHolder> {
    private Context context;
    private ArrayList<Boolean> condition;
    public PagerAdapter(Context context, ArrayList<Boolean> condition) {
        this.context = context;
        this.condition = condition;
    }
    
    @NonNull
    @Override
    public PagerAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PagerAdapterViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_page_indicator, parent, false));
    }
    
    @Override
    public void onBindViewHolder(@NonNull PagerAdapterViewHolder holder, int position) {
        boolean cond = condition.get(position);
        if(cond == true){
            holder.imageViewIndicator.setImageResource(R.drawable.icon_active);
        }else{
            holder.imageViewIndicator.setImageResource(R.drawable.icon_inactive);
        }
    }
    
    @Override
    public int getItemCount() {
        return condition.size();
    }
    
    public class PagerAdapterViewHolder extends RecyclerView.ViewHolder{
        ImageView imageViewIndicator;
        public PagerAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewIndicator = itemView.findViewById( R.id.image_view_indicator );
        }
    }
}
