package com.fragments;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.constants.ProjectConfiguration;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.UserDetails;
import com.presenter.AccountDetailsPresenter;
import com.presenter.StartFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentAccountDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAccountDetails extends Fragment implements AccountDetailsPresenter.AccountDetailsView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private View view;
    private static FragmentAccountDetails fragment;
    private Fragment newFragment;
    
    private Button buttonPersonalInformation;
    private TextView textViewName, textViewBirthDate, textViewGender, textViewCountry,
            textViewEmail, textViewPassword, textViewPhoneNumber;
    private ImageView imageViewEdit, imageViewEditAccount, imageViewBack;
    
    private Context context;
    private String email, phoneNumber;
    public AccountDetailsPresenter presenter;
    
    public FragmentAccountDetails() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentAccountDetails.
     */
    public static FragmentAccountDetails newInstance() {
        fragment = new FragmentAccountDetails();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_account_details, container, false );
        
        context = getContext();
        if(presenter == null)
            presenter = new AccountDetailsPresenter(context, this);
    
        imageViewBack             = view.findViewById( R.id.image_view_back );
        buttonPersonalInformation = view.findViewById( R.id.button_personal_information );
        textViewName              = view.findViewById( R.id.text_view_name );
        textViewBirthDate         = view.findViewById( R.id.text_view_birth_date );
        textViewGender            = view.findViewById( R.id.text_view_gender );
        textViewCountry           = view.findViewById( R.id.text_view_country );
        imageViewEdit             = view.findViewById( R.id.image_view_edit );
        
        imageViewEditAccount      = view.findViewById( R.id.image_view_edit_account );
        textViewEmail             = view.findViewById( R.id.text_view_email );
        textViewPassword          = view.findViewById( R.id.text_view_password );
        textViewPhoneNumber       = view.findViewById( R.id.text_view_phone_number );
        
        imageViewEdit.setOnClickListener( clickListener );
        imageViewEditAccount.setOnClickListener( clickListener );
        imageViewBack.setOnClickListener( clickListener );
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        presenter.getPersonalDetails();
        presenter.getAccountDetails();
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_view_edit_account:
                    newFragment = FragmentAccountFields.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable( ProjectConfiguration.UserDetails, presenter.userData );
                    newFragment.setArguments( bundle );
                    StartFragment.startFragment(getFragmentManager(), "Edit User", newFragment);
                    break;
                case R.id.image_view_edit:
                    newFragment = FragmentEditUser.newInstance();
                    Bundle bundle1 = new Bundle();
                    bundle1.putSerializable( ProjectConfiguration.UserDetails, presenter.userData );
                    newFragment.setArguments( bundle1 );
                    StartFragment.startFragment(getFragmentManager(), "Edit User", newFragment);
                    break;
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
            }
        }
    };
    
    @Override
    public void personalInformation(UserDetails userDetails) {
        String fullName  = userDetails.getFullname() != null ? userDetails.getFullname() : "";
        String birthDate = userDetails.getBirthDate() != null ? userDetails.getBirthDate() : "";
        String gender    = userDetails.getGender() != null ? userDetails.getGender() : "";
        String country   = userDetails.getCountryOfResidence() != null ? userDetails.getCountryOfResidence() : "";
        textViewName.setText( fullName );
        textViewBirthDate.setText( birthDate );
        textViewGender.setText( gender );
        textViewCountry.setText( country );
    }
    
    @Override
    public void accountInformation(UserDetails userDetails) {
        email       = userDetails.getEmail() != null ? userDetails.getEmail() : "";
        phoneNumber = userDetails.getMobileNumber() != null ? userDetails.getMobileNumber() : "";
        textViewEmail.setText( email );
        textViewPhoneNumber.setText( phoneNumber );
        textViewPassword.setText("tengesauser");
    }
    
    @Override
    public void failure(String message) {
    
    }
}
