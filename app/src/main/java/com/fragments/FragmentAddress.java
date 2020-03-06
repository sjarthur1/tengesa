package com.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.adapters.AddressAdapter;
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
 * Use the {@link FragmentAddress#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddress extends Fragment implements AddressPresenter.AddressView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private View view;
    private static FragmentAddress fragment;
    private List<UserAddresses> addressList;
    
    private ImageView imageViewBack;
    private TextView textViewTitle;
    private Button buttonAddAddress;
    private RecyclerView recyclerViewAddress;
    AddressPresenter presenter;
    private Context context;
    private AddressAdapter addressAdapter;
    
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentManager fragmentManager;
    
    public FragmentAddress() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentAddress.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAddress newInstance() {
        fragment = new FragmentAddress();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_address, container, false);
    
        imageViewBack       = view.findViewById( R.id.image_view_back );
        textViewTitle       = view.findViewById( R.id.text_view_title );
        buttonAddAddress    = view.findViewById( R.id.button_add_address );
        recyclerViewAddress = view.findViewById( R.id.recycler_view_address );
        
        
        context = getContext();
        fragmentManager = getFragmentManager();
        if(presenter == null)
            presenter = new AddressPresenter(context,this);
    
        imageViewBack.setOnClickListener( clickListener );
        buttonAddAddress.setOnClickListener(clickListener);
    
        addressList = new ArrayList<>();
    
        addressAdapter = new AddressAdapter( getContext(), addressList, getFragmentManager(), presenter );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getContext(), RecyclerView.VERTICAL, false );
        recyclerViewAddress.setLayoutManager( layoutManager );
        recyclerViewAddress.setAdapter( addressAdapter );
        
        presenter.getUserAddress();
        
        return view;
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
                case R.id.button_add_address:
                    Fragment fragmentNew = FragmentAddAddress.newInstance();
                    StartFragment.startFragment(fragmentManager, "Add Address", fragmentNew);
                    break;
            }
        }
    };
    
    
    @Override
    public void successful(List<UserAddresses> userAddresses) {
        if(userAddresses.size() > 0){
            buttonAddAddress.setEnabled(false);
            buttonAddAddress.setVisibility( View.GONE );
            addressList.addAll( userAddresses );
            recyclerViewAddress.post(new Runnable() {
                @Override
                public void run() {
                    addressAdapter.notifyDataSetChanged();
                }
            });
        }else{
            buttonAddAddress.setEnabled(true);
        }
    }
    
    @Override
    public void successful(int position) {
        if( position < addressList.size() ){
            addressList.remove( position );
            recyclerViewAddress.post(new Runnable() {
                @Override
                public void run() {
                    addressAdapter.notifyDataSetChanged();
                }
            });
            if(addressList.size() == 0) {
                buttonAddAddress.setVisibility( View.VISIBLE );
                buttonAddAddress.setEnabled(true);
            }
        }
    }
    
    @Override
    public void failed(String message) {
        buttonAddAddress.setEnabled(true);
    }
    
    @Override
    public void redirectToLogin() {
        //StartFragment.startFragment( getFragmentManager(), "Login", FragmentLogin.newInstance() );
        Intent intent = new Intent( context, ActivityAccessControl.class );
        startActivity( intent );
    }
    
    
}
