package com.fragments;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.mobile.tengesa.R;
import com.presenter.StartFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentAccount#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAccount extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentAccount fragment;
    private View view;
    
    private TextView textViewName;
    private LinearLayout linearLayoutMyWishList, linearLayoutOrders, linearLayoutMyAccount, linearLayoutAddress, linearLayoutLogout;
    private Button buttonLogout;
    
    private Context context;
    
    public FragmentAccount() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentAccount.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAccount newInstance() {
        fragment = new FragmentAccount();
        return fragment;
    }
    
    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_account, container, false);
    
        context = getContext();
        
        textViewName       = view.findViewById( R.id.text_view_name );
        linearLayoutMyWishList = view.findViewById( R.id.linear_layout_my_wish_list );
        linearLayoutOrders     = view.findViewById( R.id.linear_layout_orders );
        linearLayoutMyAccount  = view.findViewById( R.id.linear_layout_my_account );
        linearLayoutAddress    = view.findViewById( R.id.linear_layout_address );
        linearLayoutLogout     = view.findViewById( R.id.linear_layout_logout );
        buttonLogout       = view.findViewById( R.id.button_Logout );
    
        linearLayoutMyWishList.setOnClickListener( clickListener );
        linearLayoutMyAccount.setOnClickListener( clickListener );
        linearLayoutAddress.setOnClickListener( clickListener );
        linearLayoutOrders.setOnClickListener( clickListener );
        linearLayoutLogout.setOnClickListener( clickListener );
        buttonLogout.setOnClickListener(clickListener);
        
        return view;
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.linear_layout_my_wish_list:
                    StartFragment.startFragment(getFragmentManager(), "Wish Listt", FragmentWishList.newInstance());
                    break;
                case R.id.linear_layout_orders:
                    //getFragmentManager().beginTransaction().replace(R.id.linear_layout_main, FragmentAccountDetails.newInstance()).commit();
                    StartFragment.startFragment(getFragmentManager(), "Orders", FragmentOrders.newInstance());
                    break;
                case R.id.linear_layout_my_account:
                    StartFragment.startFragment(getFragmentManager(), "My Account", FragmentAccountDetails.newInstance());
                    break;
                case R.id.linear_layout_address:
                    StartFragment.startFragment(getFragmentManager(), "Address", FragmentAddress.newInstance());
                    break;
                case R.id.linear_layout_logout:
                    PreferenceManagement.RemoveItem(context, ProjectConfiguration.userId);
                    StartFragment.startFragment(getFragmentManager(), "Address", FragmentLogin.newInstance());
                    break;
                /*case R.id.button_Logout:
                    PreferenceManagement.RemoveItem(context, ProjectConfiguration.userId);
                    StartFragment.startFragment(getFragmentManager(), "Address", FragmentLogin.newInstance());
                    break;*/
            }
        }
    };
    
    
}
