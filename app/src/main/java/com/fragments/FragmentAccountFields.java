package com.fragments;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.constants.ProjectConfiguration;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.UserDetails;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentAccountFields#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAccountFields extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentAccountFields fragment;
    private static final String ARG_PARAM2 = "param2";
    private UserDetails userDetails;
    private Bundle bundle;
    private DialogFragment dialogFragment;
    
    private View view;
    private ImageView imageViewEditMobile, imageViewEditPassword, imageViewBack;
    private TextView textViewMobileNumber, textViewPassword;
    
    public FragmentAccountFields() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of this fragment .
     * @return A new instance of fragment FragmentAccountFields.
     */
    public static FragmentAccountFields newInstance() {
        fragment = new FragmentAccountFields();
        return fragment;
    }
    
    public static FragmentAccountFields getInstance(){
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_account_fields, container, false );
    
        imageViewBack         = view.findViewById( R.id.image_view_back );
        imageViewEditMobile   = view.findViewById( R.id.image_view_edit_mobile );
        imageViewEditPassword = view.findViewById( R.id.image_view_edit_password );
        textViewMobileNumber  = view.findViewById( R.id.text_view_mobile_number );
        textViewPassword      = view.findViewById( R.id.text_view_password );
        
        imageViewEditPassword.setOnClickListener( clickListener );
        imageViewEditMobile.setOnClickListener( clickListener );
        imageViewBack.setOnClickListener( clickListener );
    
        Bundle bundle = getArguments();
        if( bundle != null ){
            userDetails = (UserDetails) bundle.getSerializable(ProjectConfiguration.UserDetails);
            if( userDetails != null ) {
                String phone = userDetails.getMobileNumber() != null ? userDetails.getMobileNumber().trim() : "";
                String countryCode = userDetails.getCountryCode() != null ? userDetails.getCountryCode().trim() : "";
                textViewMobileNumber.setText(countryCode+""+phone);
            }
        }
        
        textViewPassword.setText("password");
        
        return view;
    }
    
    public void setMobile(String countryCode, String mobile){
        userDetails.setMobileNumber( mobile );
        userDetails.setCountryCode( countryCode );
        textViewMobileNumber.setText( countryCode+""+mobile );
    }
    
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch ( view.getId() ){
                case R.id.image_view_edit_mobile:
                    dialogFragment = FragmentEditPhoneNumber.newInstance();
                    bundle = new Bundle();
                    bundle.putSerializable(ProjectConfiguration.UserDetails, userDetails);
                    dialogFragment.setArguments( bundle );
                    dialogFragment.show( getFragmentManager(), "Edit Phone" );
                    break;
                case R.id.image_view_edit_password:
                    dialogFragment = FragmentEditPassword.newInstance();
                    bundle = new Bundle();
                    bundle.putSerializable(ProjectConfiguration.UserDetails, userDetails);
                    dialogFragment.setArguments( bundle );
                    dialogFragment.show( getFragmentManager(), "Edit Password" );
                    break;
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
            }
        }
    };
    
    
}
