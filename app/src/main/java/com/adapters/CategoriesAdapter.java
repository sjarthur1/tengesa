package com.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fragments.FragmentCategory;
import com.mobile.tengesa.R;
import com.objects.list_objects.Categories;
import com.presenter.StartFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder> {
    
    public Context context;
    public ArrayList<Categories.MainView> categoryLists;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    
    public CategoriesAdapter(Context context, ArrayList<Categories.MainView> categoryList, FragmentManager fragmentManager) {
        this.context = context;
        this.categoryLists = categoryList;
        this.fragmentManager = fragmentManager;
    }
    
    @NonNull
    @Override
    public CategoriesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoriesHolder(LayoutInflater.from(context).inflate(R.layout.layout_three_adapter_item, parent, false ));
    }
    
    @Override
    public void onBindViewHolder(@NonNull CategoriesHolder holder, final int position) {
        Picasso.get().load(categoryLists.get(position).getImageURL()).into(holder.imageViewCategory);
        holder.textViewCategory.setText( categoryLists.get(position).getCategoryName() );
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = FragmentCategory.newInstance();
                Bundle bundle = new Bundle();
                bundle.putString( "CategoryId", categoryLists.get( position ).getCategoryID() );
                fragment.setArguments( bundle );
                StartFragment.startFragment( fragmentManager, "CategoryItems", fragment );
            }
        };
        holder.imageViewCategory.setOnClickListener( onClickListener );
        holder.linearLayoutMain.setOnClickListener( onClickListener );
    }
    
    @Override
    public int getItemCount() {
        return categoryLists.size();
    }
    
    public class CategoriesHolder extends RecyclerView.ViewHolder{
    
        private ImageView imageViewCategory;
        private TextView textViewCategory;
        private LinearLayout linearLayoutMain;
        
        public CategoriesHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCategory = itemView.findViewById( R.id.image_view_category );
            textViewCategory  = itemView.findViewById( R.id.text_view_category );
            linearLayoutMain  = itemView.findViewById( R.id.linear_layout_main );
        }
    }
}
