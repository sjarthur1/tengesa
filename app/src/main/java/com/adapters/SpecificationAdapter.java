package com.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mobile.tengesa.R;
import com.objects.Specifications;

import java.util.List;

public class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.SpecificationViewHolder> {
    
    private Context context;
    private List<Specifications> specificationsList;
    
    public SpecificationAdapter(Context context, List<Specifications> specificationsList) {
        this.context = context;
        this.specificationsList = specificationsList;
    }
    
    @NonNull
    @Override
    public SpecificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SpecificationViewHolder(LayoutInflater.from(context).inflate(R.layout.specifications, parent, false));
    }
    
    @Override
    public void onBindViewHolder(@NonNull SpecificationViewHolder holder, int position) {
        Specifications specifications = specificationsList.get(position);
        holder.textViewKey.setText(specifications.getKey());
        holder.textViewValue.setText(specifications.getValue());
    }
    
    @Override
    public int getItemCount() {
        return specificationsList.size();
    }
    
    class SpecificationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewKey, textViewValue;
    
        public SpecificationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewKey = itemView.findViewById( R.id.text_view_key );
            textViewValue = itemView.findViewById( R.id.text_view_value );
        }
    }
}
