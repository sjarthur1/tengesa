package com.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.adapters.CountriesListAdapter;
import com.constants.ErrorField;
import com.constants.ProjectConfiguration;
import com.mobile.tengesa.R;
import com.objects.UserDetails;
import com.objects.list_objects.Country;
import com.presenter.EditPhoneNumberPresenter;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentEditPhoneNumber#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditPhoneNumber extends DialogFragment implements EditPhoneNumberPresenter.EditPhoneNumberView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentEditPhoneNumber fragment;
    private static final String ARG_PARAM2 = "param2";
    private List<Country> countryList;
    private List<String> countryCodes;
    private String countryCode;
    private UserDetails userDetails;
    private EditPhoneNumberPresenter presenter;
    
    private View view;
    private Spinner spinnerCountryCode;
    private EditText editTextPhoneNumber, editTextPassword;
    private TextView textViewPhoneError, textViewError, textViewClose;
    private Button buttonSubmit;
    private Context context;
    private CountriesListAdapter codesAdapter;
    private String mParam2, phoneNumber, password, Email;
    private String phone;
    
    public FragmentEditPhoneNumber() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of this fragment.
     * @return A new instance of fragment FragmentEditPhoneNumber.
     */
    public static FragmentEditPhoneNumber newInstance() {
        fragment = new FragmentEditPhoneNumber();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_edit_phone_number, container, false );
    
        context = getContext();
        spinnerCountryCode  = view.findViewById( R.id.spinner_country_code );
        editTextPhoneNumber = view.findViewById( R.id.edit_text_phone_number );
        editTextPassword    = view.findViewById( R.id.edit_text_password );
        textViewPhoneError  = view.findViewById( R.id.text_view_phone_error );
        textViewError       = view.findViewById( R.id.text_view_error );
        textViewClose       = view.findViewById( R.id.text_view_close );
        buttonSubmit        = view.findViewById( R.id.button_submit );
        
        countryCodes = new ArrayList<>();
        countryList = new ArrayList<>();
        codesAdapter = new CountriesListAdapter( context, countryList );
        spinnerCountryCode.setAdapter( codesAdapter );
        
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource( R.drawable.dialog_background );
        window.setLayout( 300, LinearLayout.LayoutParams.WRAP_CONTENT );
    
        /*for(int index = 0; index < countryList.size(); index++){
            String[] countryItemList = countryList.get(index).split(",");
            countryCodes.add("+"+countryItemList[0]);
        }
    
        String[] countryItemList = countryList.get(0).split(",");*/
        
        if( presenter == null )
            presenter = new EditPhoneNumberPresenter( context, this );
    
        Bundle bundle = getArguments();
        if( bundle != null ) {
            userDetails = bundle.containsKey(ProjectConfiguration.UserDetails) ?
                    (UserDetails) bundle.getSerializable(ProjectConfiguration.UserDetails) : null;
            if(userDetails != null){
                editTextPhoneNumber.setText(userDetails.getMobileNumber());
            }
        }
        
        phone = userDetails.getMobileNumber() != null ? userDetails.getMobileNumber() : "";
        countryCode = userDetails.getCountryCode() != null ? userDetails.getCountryCode() : "";
        
        textViewClose.setOnClickListener( clickListener );
        buttonSubmit.setOnClickListener( clickListener );
        presenter.getCountryCodes();
        spinnerCountryCode.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countryCode = countryList.get(position).getCode();
                spinnerCountryCode.setBackgroundResource( R.drawable.toggle_left_off );
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if( countryCode == null )
                    spinnerCountryCode.setBackgroundResource( R.drawable.toggle_left_off );
            }
        });
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_submit:
                    phoneNumber = editTextPhoneNumber.getText().toString();
                    password = editTextPassword.getText().toString();
                    Email = userDetails.getEmail();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(ProjectConfiguration.MobileNumber, phoneNumber);
                        jsonObject.put(ProjectConfiguration.password, password);
                        jsonObject.put(ProjectConfiguration.Email, Email);
                        jsonObject.put(ProjectConfiguration.CountryCode, countryCode);
                        presenter.editMobileNumber(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case R.id.text_view_close:
                    dismiss();
                    break;
            }
        }
    };
    
    @Override
    public void successful(String message) {
        Toast.makeText(context, message+"", Toast.LENGTH_LONG).show();
        long phone = Long.parseLong(phoneNumber);
        FragmentAccountFields.getInstance().setMobile( countryCode, phone+"" );
        dismiss();
    }
    
    @Override
    public void failure(String message, ErrorField field) {
        if(field.equals(ErrorField.phoneNumber)){
            editTextPhoneNumber.setError(message+"");
            editTextPhoneNumber.requestFocus();
        }else if(field.equals(ErrorField.password)){
            editTextPassword.setError(message+"");
            editTextPassword.requestFocus();
        }else{
            textViewError.setText(message+"");
            textViewError.setVisibility(View.VISIBLE);
        }
    }
    
    @Override
    public void successful(List<Country> countryObject) {
        countryList.addAll( countryObject );
        
        for(int index = 0; index < countryList.size(); index++){
            countryCodes.add( countryList.get(index).getCode() );
        }
        
        if( countryCode != null ){
            int index = countryCodes.indexOf( countryCode );
            spinnerCountryCode.setSelection( index );
            editTextPhoneNumber.setText( phone+"" );
        }else
            countryCode = countryList.get(0).getCode();
        
        spinnerCountryCode.post(new Runnable() {
            @Override
            public void run() {
                codesAdapter.notifyDataSetChanged();
            }
        });
    }
}
