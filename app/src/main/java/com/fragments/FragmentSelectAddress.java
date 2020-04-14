package com.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adapters.SelectAddressAdapter;
import com.constants.ProjectConfiguration;
import com.mobile.access_control.ActivityAccessControl;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.UserAddresses;
import com.presenter.AddressPresenter;
import com.presenter.StartFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentSelectAddress#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSelectAddress extends Fragment implements AddressPresenter.AddressView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private View view;
    private Fragment fragmentNew;
    private static FragmentSelectAddress fragment;
    private List<UserAddresses> addressList;
    private ImageView imageViewBack, imageViewLogo, imageViewAddress;
    
    private TextView textViewTitle;
    private Button buttonAddAddress;
    private RecyclerView recyclerViewAddress;
    AddressPresenter presenter;
    private Context context;
    private SelectAddressAdapter selectAddressAdapter;
    
    // TODO: Rename and change types of parameters
    FragmentManager fragmentManager;
    
    public FragmentSelectAddress() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentAddress.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSelectAddress newInstance() {
        fragment = new FragmentSelectAddress();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_select_address, container, false);
    
        imageViewLogo       = view.findViewById( R.id.image_view_logo );
        imageViewAddress    = view.findViewById( R.id.image_view_address );
        imageViewBack       = view.findViewById( R.id.image_view_back );
        textViewTitle       = view.findViewById( R.id.text_view_title );
        buttonAddAddress    = view.findViewById( R.id.button_add_address );
        recyclerViewAddress = view.findViewById( R.id.recycler_view_address );
        
        
        context = getContext();
        fragmentManager = getFragmentManager();
        if( presenter == null )
            presenter = new AddressPresenter(context,this);
    
        ProjectConfiguration.setLogo( imageViewLogo );
        ProjectConfiguration.setAddressLogo( imageViewAddress );
        
        buttonAddAddress.setOnClickListener( clickListener );
        imageViewBack.setOnClickListener( clickListener );
        
        addressList = new ArrayList<>();
        
        selectAddressAdapter = new SelectAddressAdapter( getContext(), addressList, getFragmentManager(), presenter );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getContext(), RecyclerView.VERTICAL, false );
        recyclerViewAddress.setLayoutManager( layoutManager );
        recyclerViewAddress.setAdapter( selectAddressAdapter );
        
        presenter.getUserAddress();
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        //MainActivity.getInstance().refreshOrderData();
        MainActivity.getInstance().clearBundle();
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch ( view.getId() ) {
                case R.id.button_add_address:
                    Fragment fragmentNew = FragmentAddAddress.newInstance();
                    StartFragment.startFragment( fragmentManager, "Add Address", fragmentNew );
                    break;
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
            }
        }
    };
    
    
    @Override
    public void successful(List<UserAddresses> userAddresses) {
        if(userAddresses.size() > 0){
            addressList.addAll( userAddresses );
            recyclerViewAddress.post(new Runnable() {
                @Override
                public void run() {
                    selectAddressAdapter.notifyDataSetChanged();
                }
            });
        }
    }
    
    @Override
    public void successful(int position) {
        if( position < addressList.size() ){
            addressList.remove( position );
            recyclerViewAddress.post(new Runnable() {
                @Override
                public void run() {
                    selectAddressAdapter.notifyDataSetChanged();
                }
            });
        }
    }
    
    @Override
    public void failed(String message) {
    
    }
    
    @Override
    public void redirectToLogin() {
        //StartFragment.startFragment( getFragmentManager(), "Login", FragmentLogin.newInstance() );
        Intent intent = new Intent( context, ActivityAccessControl.class );
        startActivity( intent );
    }
    
}
