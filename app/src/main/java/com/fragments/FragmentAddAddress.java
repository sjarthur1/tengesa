package com.fragments;

import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapters.CountriesListAdapter;
import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.UserAddresses;
import com.objects.list_objects.Country;
import com.presenter.AddAddressPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentAddAddress#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAddAddress extends Fragment implements AddAddressPresenter.AddAddressView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentAddAddress fragment;
    private static final String ARG_PARAM2 = "param2";
    
    private View view;
    private ImageView imageViewBack;
    private EditText editTextName, editTextMobile, editTextCity, editTextArea, editTextShippingNote, editTextNearestLandMark,
            editTextStreet, editTextLocationType, editTextAddress1, editTextAddress2, editTextCountryCode;
    private Spinner spinnerCountry;
    
    Button buttonSubmit;
    
    // TODO: Rename and change types of parameters
    private List<String> countryCodes;
    private List<Country> countryList;
    private String mParam2;
    AddAddressPresenter presenter;
    Context context;
    private Country country;
    private CountriesListAdapter codesAdapter;
    String loginId, countryCode;
    UserAddresses address;
    
    public FragmentAddAddress() {
        // Required empty public constructor
    }
    
    // Creating instance of a fragment
    public static FragmentAddAddress newInstance() {
        fragment = new FragmentAddAddress();
        return fragment;
    }
    
    public static FragmentAddAddress getInstance(){
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_address, container, false);
    
        imageViewBack           = view.findViewById( R.id.image_view_back );
        editTextName            = view.findViewById( R.id.edit_text_name );
        editTextMobile          = view.findViewById( R.id.edit_text_mobile );
        spinnerCountry          = view.findViewById( R.id.spinner_country );
        editTextCountryCode     = view.findViewById( R.id.edit_text_country_code );
        editTextCity            = view.findViewById( R.id.edit_text_city );
        editTextArea            = view.findViewById( R.id.edit_text_area );
        editTextStreet          = view.findViewById( R.id.edit_text_street );
        editTextLocationType    = view.findViewById( R.id.edit_text_location_type );
        editTextShippingNote    = view.findViewById( R.id.edit_text_shipping_note );
        editTextNearestLandMark = view.findViewById( R.id.edit_text_nearest_land_mark );
        editTextAddress1        = view.findViewById( R.id.edit_text_address1 );
        editTextAddress2        = view.findViewById( R.id.edit_text_address2 );
        
        buttonSubmit = view.findViewById( R.id.button_submit );
        
        imageViewBack.setOnClickListener( clickListener );
        buttonSubmit.setOnClickListener(clickListener);
        
        context = getContext();
        if( presenter == null )
            presenter = new AddAddressPresenter(context, this);
    
        Bundle bundle = getArguments();
        if(bundle != null) {
            address = bundle.containsKey("data") ? (UserAddresses) bundle.getSerializable("data") : null;
            if ( address != null )
                fillAddressData();
        }
    
        countryCodes = new ArrayList<>();
        countryList = new ArrayList<>();
        codesAdapter = new CountriesListAdapter( context, countryList );
        spinnerCountry.setAdapter( codesAdapter );
    
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                country = countryList.get(i);
                spinnerCountry.setBackgroundResource( R.drawable.toggle_left_off );
                editTextCountryCode.setText( country.getCode() );
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if( countryCode == null )
                    spinnerCountry.setBackgroundResource( R.drawable.toggle_left_off );
            }
        });
        
        loginId = PreferenceManagement.readString( context, ProjectConfiguration.userId, null );
        presenter.getCountryCodes();
        
        return view;
    }
    
    private void fillAddressData() {
        editTextName.setText(address.getFullname()+"");
        editTextMobile.setText(address.getMobile_Number()+"");
        //editTextCountry.setText(address.getCountry()+"");
        editTextCity.setText(address.getCity()+"");
        editTextArea.setText(address.getArea()+"");
        editTextStreet.setText(address.getStreetNameOrNo()+"");
        editTextLocationType.setText(address.getLocationType()+"");
        editTextShippingNote.setText(address.getShippingNote()+"");
        editTextNearestLandMark.setText(address.getNearestLandmark()+"");
        editTextAddress1.setText(address.getAddress1()+"");
        editTextAddress2.setText(address.getAddress2()+"");
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch ( view.getId() ) {
                case R.id.button_submit:
                    UserAddresses addresses = new UserAddresses();
                    try {
                        addresses.setUserID(loginId);
                        addresses.setFullname(editTextName.getText().toString());
                        addresses.setCountry(country.getCountry());
                        addresses.setMobile_Number(editTextMobile.getText().toString());
                        addresses.setCity(editTextCity.getText().toString());
                        addresses.setArea(editTextArea.getText().toString());
                        addresses.setStreetNameOrNo(editTextStreet.getText().toString());
                        addresses.setLocationType(editTextLocationType.getText().toString());
                        addresses.setNearestLandmark(editTextNearestLandMark.getText().toString());
                        addresses.setAddress1(editTextAddress1.getText().toString());
                        addresses.setAddress2(editTextAddress2.getText().toString());
                        addresses.setCountry_Code(country.getCode());
            
                        if (address == null)
                            presenter.submitAddress(addresses, true);
                        else {
                            addresses.setAddressID(address.getAddressID());
                            presenter.submitAddress(addresses, false);
                        }
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
            }
        }
    };
    
    @Override
    public void successful(UserAddresses addresses) {
    
    }
    
    @Override
    public void successful(String message) {
        Toast.makeText( context, message, Toast.LENGTH_LONG ).show();
        getActivity().onBackPressed();
    }
    
    @Override
    public void successful(List<Country> countryObject) {
        countryList.addAll( countryObject );
        spinnerCountry.post(new Runnable() {
            @Override
            public void run() {
                codesAdapter.notifyDataSetChanged();
            }
        });
        if(address != null) {
            country = new Country();
            country.setCode( address.getCountry_Code() );
            country.setCountry( address.getCountry() );
        }else
            country = countryList.get(0);
        
        editTextCountryCode.setText( country.getCode() );
        
    }
    
    @Override
    public void failure(String message, String page) {
    
    }
    
    @Override
    public void failure(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void error(String error, AddAddressPresenter.ErrorType errorType) {
    
    }
}
