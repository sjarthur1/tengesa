package com.fragments;

import android.content.Context;
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
import com.presenter.AccountFieldsPresenter;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentAccountFields#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAccountFields extends Fragment implements AccountFieldsPresenter.AccountFieldsView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentAccountFields fragment;
    private static final String ARG_PARAM2 = "param2";
    private Bundle bundle;
    private DialogFragment dialogFragment;
    public AccountFieldsPresenter presenter;
    private Context context;
    
    private View view;
    private ImageView imageViewEditMobile, imageViewEditPassword, imageViewBack, imageViewLogo;
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
        
        context = getContext();
    
        imageViewLogo         = view.findViewById( R.id.image_view_logo );
        imageViewBack         = view.findViewById( R.id.image_view_back );
        imageViewEditMobile   = view.findViewById( R.id.image_view_edit_mobile );
        imageViewEditPassword = view.findViewById( R.id.image_view_edit_password );
        textViewMobileNumber  = view.findViewById( R.id.text_view_mobile_number );
        textViewPassword      = view.findViewById( R.id.text_view_password );
        
        imageViewEditPassword.setOnClickListener( clickListener );
        imageViewEditMobile.setOnClickListener( clickListener );
        imageViewBack.setOnClickListener( clickListener );
        
        presenter = new AccountFieldsPresenter( context, this );
    
        Bundle bundle = getArguments();
        if( bundle != null )
            presenter.userDetails = (UserDetails) bundle.getSerializable(ProjectConfiguration.UserDetails);
            
        
        textViewPassword.setText("password");
        ProjectConfiguration.setLogo(imageViewLogo );
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        getAccountData();
        
    }
    public void getAccountData(){
        presenter.getAccountDetails();
    }
    
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch ( view.getId() ){
                case R.id.image_view_edit_mobile:
                    dialogFragment = FragmentEditPhoneNumber.newInstance();
                    bundle = new Bundle();
                    bundle.putSerializable(ProjectConfiguration.UserDetails, presenter.userDetails);
                    dialogFragment.setArguments( bundle );
                    dialogFragment.show( getFragmentManager(), "Edit Phone" );
                    break;
                case R.id.image_view_edit_password:
                    dialogFragment = FragmentEditPassword.newInstance();
                    bundle = new Bundle();
                    bundle.putSerializable(ProjectConfiguration.UserDetails, presenter.userDetails);
                    dialogFragment.setArguments( bundle );
                    dialogFragment.show( getFragmentManager(), "Edit Password" );
                    break;
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
            }
        }
    };
    
    
    @Override
    public void accountInformation(UserDetails userDetails) {
        if( presenter.userDetails != null ) {
            String phone = presenter.userDetails.getMobile_Number() != null ? presenter.userDetails.getMobile_Number().trim() : "";
            String countryCode = presenter.userDetails.getCountryCode() != null ? presenter.userDetails.getCountryCode().trim() : "";
            textViewMobileNumber.setText(countryCode+""+phone);
        }
    }
    
    @Override
    public void failure(String errorMessage) {
    
    }
}
