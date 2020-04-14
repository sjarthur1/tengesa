package com.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapters.CountriesListAdapter;
import com.constants.ProjectConfiguration;
import com.google.android.material.textfield.TextInputEditText;
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
    private ImageView imageViewBack, imageViewLogo;
    private TextInputEditText editTextName, editTextMobile, editTextArea, editTextShippingNote, editTextNearestLandMark,
            editTextStreet, editTextAddress1, editTextAddress2;//, editTextCountryCode; editTextCity,
    private TextView textViewTitle;
    private Spinner spinnerCountry, spinnerCity, spinnerLocationType;
    
    private Button buttonSubmit;
    
    // TODO: Rename and change types of parameters
    private List<String> countryCodes, cities, locationType;
    private List<Country> countryList;
    AddAddressPresenter presenter;
    Context context;
    private Country country;
    private CountriesListAdapter codesAdapter;
    private ArrayAdapter<String> cityAdapter, locationAdapter;
    private ProgressDialog progressDialog;
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
    
        imageViewLogo           = view.findViewById( R.id.image_view_logo );
        imageViewBack           = view.findViewById( R.id.image_view_back );
        textViewTitle           = view.findViewById( R.id.text_view_title );
        editTextName            = view.findViewById( R.id.edit_text_name );
        editTextMobile          = view.findViewById( R.id.edit_text_mobile );
        spinnerCountry          = view.findViewById( R.id.spinner_country );
        spinnerCity             = view.findViewById( R.id.spinner_city );
        //editTextCountryCode     = view.findViewById( R.id.edit_text_country_code );
        //editTextCity            = view.findViewById( R.id.edit_text_city );
        editTextArea            = view.findViewById( R.id.edit_text_area );
        editTextStreet          = view.findViewById( R.id.edit_text_street );
        spinnerLocationType     = view.findViewById( R.id.spinner_location_type );
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
        
        cities       = new ArrayList<>();
        countryCodes = new ArrayList<>();
        countryList  = new ArrayList<>();
        codesAdapter = new CountriesListAdapter( context, countryList );
        spinnerCountry.setAdapter( codesAdapter );
    
        cityAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, cities);
        spinnerCity.setAdapter( cityAdapter );
    
        locationType = new ArrayList<>();
        locationType.add("Home");
        locationType.add("Work");
        locationAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, locationType);
        spinnerLocationType.setAdapter( locationAdapter );
    
        spinnerCountry.setOnItemSelectedListener( onItemSelectedListener );
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                address.setCity( cities.get(i) );
            }
    
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
        
            }
        });
        spinnerLocationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                address.setLocationType( locationType.get(i) );
            }
    
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
        
            }
        });
    
        Bundle bundle = getArguments();
        address = bundle != null && bundle.containsKey("data") ? (UserAddresses) bundle.getSerializable("data") : null;
        if ( address != null ) {
            textViewTitle.setText( context.getText( R.string.title_edit_address ) );
            fillAddressData();
        }else {
            address = new UserAddresses();
            textViewTitle.setText(context.getText(R.string.title_add_address));
            spinnerLocationType.setSelection(0);
        }
        
        loginId = PreferenceManagement.readString( context, ProjectConfiguration.userId, null );
        presenter.getCountryCodes();
        
        ProjectConfiguration.setLogo(imageViewLogo);
        
        return view;
    }
    
    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            country = countryList.get(i);
            spinnerCountry.setBackgroundResource( R.drawable.edit_text_border );
            presenter.getCities( country.getId() );
        }
    
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        
        }
    };
    
    private void fillAddressData() {
        String fullName = address.getFullname() != null ? address.getFullname().trim() : "";
        String mobileMumber = address.getMobile_Number() != null ? address.getMobile_Number().trim() : "";
        String area = address.getArea() != null ? address.getArea().trim() : "";
        String streetNameOrNo = address.getStreetNameOrNo() != null ? address.getStreetNameOrNo().trim() : "";
        String locationType = address.getLocationType() != null ? address.getLocationType().trim() : "";
        String shippingNote = address.getShippingNote() != null ? address.getShippingNote().trim() : "";
        String nearestLandMark = address.getNearestLandmark() != null ? address.getNearestLandmark().trim() : "";
        String addressOne = address.getAddress1() != null ? address.getAddress1().trim() : "";
        String addressTwo = address.getAddress2() != null ? address.getAddress2().trim() : "";
        
        editTextName.setText( fullName );
        editTextMobile.setText( mobileMumber );
        editTextArea.setText( area );
        editTextStreet.setText( streetNameOrNo );
        editTextShippingNote.setText( shippingNote );
        editTextNearestLandMark.setText( nearestLandMark );
        editTextAddress1.setText( addressOne );
        editTextAddress2.setText( addressTwo );
        
        int locationIndex = this.locationType.indexOf( locationType );
        spinnerLocationType.setSelection( locationIndex );
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch ( view.getId() ) {
                case R.id.button_submit:
                    buttonSubmit.setEnabled(  false );
                    try {
                        address.setUserID( loginId );
                        address.setFullname( editTextName.getText().toString() );
                        address.setCountry( country.getCountry() );
                        address.setMobile_Number( editTextMobile.getText().toString() );
                        address.setArea( editTextArea.getText().toString() );
                        address.setStreetNameOrNo( editTextStreet.getText().toString() );
                        address.setNearestLandmark( editTextNearestLandMark.getText().toString() );
                        address.setShippingNote( editTextShippingNote.getText().toString() );
                        address.setAddress1( editTextAddress1.getText().toString() );
                        address.setAddress2( editTextAddress2.getText().toString() );
                        address.setCountry_Code( country.getCode() );
            
                        boolean isNewAddress = ( address.getAddressID() == null ) ? true : false;
                        presenter.submitAddress( address, isNewAddress );
                        
                    } catch (Exception e) {
                        buttonSubmit.setEnabled(  true );
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                    break;
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
            }
        }
    };
    
    private void dismissDialog(){
        buttonSubmit.setEnabled( true );
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
    
    @Override
    public void successful(UserAddresses addresses) {
        buttonSubmit.setEnabled( true );
    }
    
    @Override
    public void showDialog() {
        progressDialog = ProgressDialog.show(context, "Loading", "Please wait while processing.");
    }
    
    @Override
    public void successful(String message) {
        Toast.makeText( context, message, Toast.LENGTH_LONG ).show();
        buttonSubmit.setEnabled( true );
        dismissDialog();
        getActivity().onBackPressed();
    }
    
    @Override
    public void successful(List<Country> countryObject) {
        countryList.clear();
        countryList.addAll( countryObject );
        spinnerCountry.post(new Runnable() {
            @Override
            public void run() {
                codesAdapter.notifyDataSetChanged();
            }
        });
        
        if(address != null) {
            for(Country country : countryObject){
                if(country.getCode().equals(address.getCountry_Code())){
                    this.country = country;
                    break;
                }
            }
            if(country == null)
                country = countryList.get(0);
        }else
            country = countryList.get(0);
        
        //editTextCountryCode.setText( country.getCode() );
        presenter.getCities( country.getId() );
        
    }
    
    @Override
    public void cities(List<String> cities) {
        this.cities.addAll(cities);
        spinnerCity.post(new Runnable() {
            @Override
            public void run() {
                cityAdapter.notifyDataSetChanged();
            }
        });
        
        if(  address != null && cities.contains( address.getCity() )){
            int index = cities.indexOf( address.getCity() );
            spinnerCity.setSelection( index );
            address.setCity( cities.get( index ) );
        }else {
            spinnerCity.setSelection( 0 );
            address.setCity( cities.get( 0 ) );
        }
    }
    
    @Override
    public void failure(String message) {
        buttonSubmit.setEnabled( true );
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void error(String error, AddAddressPresenter.ErrorType errorType) {
        buttonSubmit.setEnabled( true );
        dismissDialog();
        
        if(errorType.equals(AddAddressPresenter.ErrorType.name)){
            editTextName.setError( error );
            editTextName.requestFocus();
        }
        if(errorType.equals(AddAddressPresenter.ErrorType.phoneNumber)){
            editTextMobile.setError( error );
            editTextMobile.requestFocus();
        }
        if(errorType.equals(AddAddressPresenter.ErrorType.area)){
            editTextArea.setError( error );
            editTextArea.requestFocus();
        }
        if(errorType.equals(AddAddressPresenter.ErrorType.street)){
            editTextStreet.setError( error );
            editTextStreet.requestFocus();
        }
        if(errorType.equals(AddAddressPresenter.ErrorType.shippingNote)){
            editTextShippingNote.setError( error );
            editTextShippingNote.requestFocus();
        }
        /*if(errorType.equals(AddAddressPresenter.ErrorType.locationType)){
            spinnerLocationType.setError( error );
            editTextShippingNote.requestFocus();
        }*/
        if( errorType.equals( AddAddressPresenter.ErrorType.address1 ) ){
            editTextAddress1.setError( error );
            editTextAddress1.requestFocus();
        }
        
    }
}
