package com.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fragments.FragmentAddAddress;
import com.mobile.tengesa.R;
import com.objects.UserAddresses;
import com.presenter.AddressPresenter;
import com.presenter.StartFragment;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    
    private Context context;
    private List<UserAddresses> addressList;
    private FragmentManager fragmentManager;
    private Fragment fragment;
    private AddressPresenter presenter;
    public AddressAdapter(Context context, List<UserAddresses> addressList, FragmentManager fragmentManager, AddressPresenter presenter) {
        this.context = context;
        this.addressList = addressList;
        this.fragmentManager = fragmentManager;
        this.presenter = presenter;
    }
    
    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressViewHolder( LayoutInflater.from(context).inflate( R.layout.layout_single_address, parent, false ) );
    }
    
    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, final int position) {
        final UserAddresses addressObject = addressList.get( position );
        holder.textViewBuilding.setText( addressObject.getAddress1() );
        holder.textViewStreet.setText( addressObject.getStreetNameOrNo() );
        holder.textViewCity.setText( addressObject.getCity() );
        holder.textViewCountry.setText( addressObject.getCountry() );
        if(position == addressList.size()-1){
            holder.buttonAddAddress.setVisibility(View.VISIBLE);
        }else{
            holder.buttonAddAddress.setVisibility(View.GONE);
        }
    
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.image_view_edit:
                        fragment = FragmentAddAddress.newInstance();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("data", addressObject);
                        fragment.setArguments( bundle );
                        StartFragment.startFragment(fragmentManager, "Add Address", fragment);
                        break;
                    case R.id.button_add_address:
                        fragment = FragmentAddAddress.newInstance();
                        StartFragment.startFragment(fragmentManager, "Add Address", fragment);
                        break;
                    case R.id.image_view_cancel:
                        presenter.deleteAddress( addressObject.getAddressID(), position );
                        break;
                }
            }
        };
        
        holder.buttonAddAddress.setOnClickListener( onClickListener );
        holder.imageViewEdit.setOnClickListener( onClickListener );
        holder.imageViewCancel.setOnClickListener( onClickListener );
    }
    
    @Override
    public int getItemCount() {
        return addressList.size();
    }
    
    class AddressViewHolder extends RecyclerView.ViewHolder{
        TextView textViewBuilding, textViewStreet, textViewCity, textViewCountry;
        Button buttonAddAddress;
        ImageView imageViewEdit, imageViewCancel;
    
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBuilding = itemView.findViewById( R.id.text_view_building );
            textViewStreet = itemView.findViewById( R.id.text_view_street );
            textViewCity = itemView.findViewById( R.id.text_view_city );
            textViewCountry = itemView.findViewById( R.id.text_view_country );
            buttonAddAddress = itemView.findViewById( R.id.button_add_address );
            imageViewEdit = itemView.findViewById( R.id.image_view_edit );
            imageViewCancel = itemView.findViewById( R.id.image_view_cancel );
        }
    }
}
