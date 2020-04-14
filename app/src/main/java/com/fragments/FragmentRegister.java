package com.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.widget.AdapterView;
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
import com.mobile.access_control.ActivityAccessControl;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.list_objects.Country;
import com.presenter.RegisterPresenter;
import com.presenter.StartFragment;

import java.util.ArrayList;
import java.util.List;


public class FragmentRegister extends Fragment implements RegisterPresenter.RegisterView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private View view;
    private static FragmentRegister fragment;
    
    private Spinner spinnerCountryCode;
    private TextInputEditText editTextName, editTextEmail, editTextPassword, editTextReEnterPassword, editTextPhoneNumber;
    private Button buttonRegister;
    private TextView textViewError;
    private ImageView imageViewBack, imageViewLogo, imageViewPassword;
    
    private String name, email, phoneNumber, password, reenterPassword, countryCode;
    private Context context;
    private List<Country> countryList;
    private List<String> countryCodes;
    private CountriesListAdapter codesAdapter;
    private ProgressDialog progressDialog;
    
    private RegisterPresenter presenter;
    
    public FragmentRegister() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentRegister.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentRegister newInstance() {
        fragment = new FragmentRegister();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_register, container, false);
        
        context = getContext();
        
        imageViewLogo = view.findViewById( R.id.image_view_logo );
        imageViewBack = view.findViewById( R.id.image_view_back );
        textViewError           = view.findViewById( R.id.text_view_error );
        editTextName            = view.findViewById( R.id.edit_text_name );
        editTextEmail           = view.findViewById( R.id.edit_text_email );
        editTextPhoneNumber     = view.findViewById( R.id.edit_text_phone_number );
        editTextPassword        = view.findViewById( R.id.edit_text_password );
        imageViewPassword       = view.findViewById( R.id.image_view_password );
        editTextReEnterPassword = view.findViewById( R.id.edit_text_re_enter_password );
    
        spinnerCountryCode = view.findViewById( R.id.spinner_country_code );
    
        buttonRegister = view.findViewById( R.id.button_register );
        
        presenter = new RegisterPresenter( context, this );
    
        ProjectConfiguration.setLogoColored( imageViewLogo );
        countryCodes = new ArrayList<>();
        countryList = new ArrayList<>();
        codesAdapter = new CountriesListAdapter( context, countryList );
        spinnerCountryCode.setAdapter( codesAdapter );
        
        presenter.getCountryCodes();
        spinnerCountryCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                countryCode = countryList.get(i).getCode();
                spinnerCountryCode.setBackgroundResource( R.drawable.edit_text_border );
            }
    
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                if( countryCode == null )
                    spinnerCountryCode.setBackgroundResource( R.drawable.edit_text_border );
            }
        });
        
        buttonRegister.setOnClickListener( clickListener );
        imageViewBack.setOnClickListener( clickListener );
        imageViewPassword.setOnClickListener( clickListener );
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        ActivityAccessControl.getInstance().hideSoftKeyboard( view );
    }
    
    private void togglePassword(){
        if( editTextPassword.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD ) {
            editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageViewPassword.setImageResource( R.drawable.visibility_off_icon );
        }else{
            editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageViewPassword.setImageResource( R.drawable.visibility_on_icon );
        }
        editTextPassword.setSelection( editTextPassword.getText().length() );
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch ( view.getId() ) {
                case R.id.button_register:
                    buttonRegister.setEnabled( false );
                    textViewError.setVisibility( View.GONE );
                    name = editTextName.getText().toString();
                    email = editTextEmail.getText().toString();
                    phoneNumber = editTextPhoneNumber.getText().toString();
                    password = editTextPassword.getText().toString();
                    reenterPassword = editTextReEnterPassword.getText().toString();
                    presenter.register(name, email, phoneNumber, password, reenterPassword, countryCode);
                    break;
                case R.id.image_view_back:
                    ActivityAccessControl.getInstance().onBackPressed();
                    break;
                case R.id.image_view_password:
                    togglePassword();
                    break;
            }
        }
    };
    
    private void dismissDialog(){
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
    
    @Override
    public void onStop() {
        super.onStop();
        dismissDialog();
    }
    
    @Override
    public void showDialog() {
        progressDialog = ProgressDialog.show(context, "Loading", "Please wait while processing.");
    }
    
    @Override
    public void error(String error, RegisterPresenter.ErrorType errorType ) {
        dismissDialog();
        buttonRegister.setEnabled( true );
        if( errorType.equals( RegisterPresenter.ErrorType.name ) ) {
            editTextName.setError(error);
            editTextName.requestFocus();
        }else if( errorType.equals( RegisterPresenter.ErrorType.email ) ){
            editTextEmail.setError(error);
            editTextEmail.requestFocus();
        }else if( errorType.equals( RegisterPresenter.ErrorType.password ) ){
            editTextPassword.setError(error);
            editTextPassword.requestFocus();
        }else if( errorType.equals( RegisterPresenter.ErrorType.password2 ) ){
            editTextReEnterPassword.setError(error);
            editTextReEnterPassword.requestFocus();
        }else if( errorType.equals( RegisterPresenter.ErrorType.countryCode ) || errorType.equals( RegisterPresenter.ErrorType.phoneNumber ) ){
            editTextEmail.setError(error);
            editTextEmail.requestFocus();
        }else{
            //Toast.makeText( context, error, Toast.LENGTH_LONG ).show();
            textViewError.setText( error );
            textViewError.setVisibility( View.VISIBLE );
        }
        
    }
    
    
    @Override
    public void successful( List<Country> countryObject ) {
        countryList.addAll( countryObject );
        countryCode = countryList.get(0).getCode();
        spinnerCountryCode.post(new Runnable() {
            @Override
            public void run() {
                codesAdapter.notifyDataSetChanged();
            }
        });
        dismissDialog();
        buttonRegister.setEnabled( true );
    }
    
    @Override
    public void successful(String message) {
        Toast.makeText( context, message, Toast.LENGTH_LONG ).show();
        StartFragment.startFragment(getFragmentManager(), "Login", FragmentLogin.newInstance());
        dismissDialog();
        buttonRegister.setEnabled( true );
    }
}
