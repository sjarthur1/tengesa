package com.fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adapters.CountriesListAdapter;
import com.constants.ErrorField;
import com.constants.ProjectConfiguration;
import com.google.android.material.textfield.TextInputEditText;
import com.helpers.DateConverter;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.UserDetails;
import com.objects.list_objects.Country;
import com.presenter.EditUserPresenter;
import org.joda.time.DateTime;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentEditUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditUser extends Fragment implements EditUserPresenter.EditUserView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private DatePickerDialog datePickerDialog;
    private static FragmentEditUser fragment;
    private static final String ARG_PARAM2 = "param2";
    private String dateFormat;
    private Calendar calendar;
    private Context context;
    public EditUserPresenter presenter;
    
    private View view;
    private UserDetails userDetails;
    private ImageView imageViewBack, imageViewLogo;
    private TextInputEditText editTextName, editTextBirthDate;
    private Spinner spinnerCountry;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale;
    private Button buttonSubmit;
    private List<Country> countryList;
    private List<String> countryTitles;
    private CountriesListAdapter codesAdapter;
    private Country country;
    
    public FragmentEditUser() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FragmentEditUser.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEditUser newInstance() {
        fragment = new FragmentEditUser();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit_user, container, false);
        context = getContext();
    
        imageViewLogo     = view.findViewById( R.id.image_view_logo );
        imageViewBack     = view.findViewById( R.id.image_view_back );
        editTextName      = view.findViewById( R.id.edit_text_name );
        spinnerCountry    = view.findViewById( R.id.spinner_country );
        editTextBirthDate = view.findViewById( R.id.edit_text_birth_date );
        radioGroupGender  = view.findViewById( R.id.radio_group_gender );
        radioButtonMale   = view.findViewById( R.id.radio_button_male );
        radioButtonFemale = view.findViewById( R.id.radio_button_female );
        buttonSubmit      = view.findViewById( R.id.button_submit );
        
        Bundle bundle = getArguments();
        if( bundle != null ){
            userDetails = (UserDetails) bundle.getSerializable(ProjectConfiguration.UserDetails);
        }
        
        if( presenter == null )
            presenter = new EditUserPresenter(context, this);
        
        countryList   = new ArrayList<>();
        countryTitles = new ArrayList<>();
        codesAdapter  = new CountriesListAdapter( context, countryList );
        spinnerCountry.setAdapter( codesAdapter );
        
        imageViewBack.setOnClickListener( clickListener );
        editTextBirthDate.setOnClickListener( clickListener );
        buttonSubmit.setOnClickListener( clickListener );
        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch( radioGroup.getCheckedRadioButtonId() ){
                    case R.id.radio_button_male:
                        userDetails.setGender("Male");
                        break;
                    case R.id.radio_button_female:
                        userDetails.setGender("Female");
                        break;
                }
            }
        });
    
    
        presenter.getCountryCodes();
        spinnerCountry.setOnItemSelectedListener( new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userDetails.setCountryOfResidence( countryList.get(position).getCountry() );
                spinnerCountry.setBackgroundResource( R.drawable.edit_text_border );
            }
        
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if( country == null )
                    spinnerCountry.setBackgroundResource( R.drawable.edit_text_border );
            }
        });
        
        calendar = Calendar.getInstance();
    
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //calendar.
                int monthSelected = datePickerDialog.getDatePicker().getMonth() + 1;
        
                editTextBirthDate.setText(datePickerDialog.getDatePicker().getDayOfMonth() + "/" + monthSelected + "/" + datePickerDialog.getDatePicker().getYear());
                editTextBirthDate.setEnabled(true);
                editTextBirthDate.setError(null);
            }
        };
        
        ProjectConfiguration.setLogo( imageViewLogo );
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        fillForm();
    }
    
    private void fillForm(){
        if( userDetails != null ){
            String fullName  = userDetails.getFullname() != null ? userDetails.getFullname() : "";
            String birthDate = userDetails.getBirthDate() != null ? userDetails.getBirthDate() : "";
            String gender    = userDetails.getGender() != null ? userDetails.getGender() : "";
            String email     = userDetails.getEmail() != null ? userDetails.getEmail() : "";
            String country   = userDetails.getCountryOfResidence() != null ? userDetails.getCountryOfResidence() : "";
            
            editTextName.setText( fullName );
            editTextBirthDate.setText( birthDate );
            if(gender.equalsIgnoreCase("Male"))
                radioButtonMale.setChecked(true);
            else if(gender.equalsIgnoreCase("Female"))
                radioButtonFemale.setChecked(true);
            
        }
    }
    
    DialogInterface.OnCancelListener onCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            editTextBirthDate.setEnabled(true);
        }
    };
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.edit_text_birth_date:
                    editTextBirthDate.setEnabled( false );
                    datePickerDialog = new DatePickerDialog( getContext(), android.R.style.Theme_Holo_Light_Dialog, dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    long startPickDate = new DateTime().minusYears(13).getMillis();
                    datePickerDialog.getDatePicker().setMaxDate( startPickDate );
                    datePickerDialog.setOnCancelListener(onCancelListener);
                    datePickerDialog.setCancelable(false);
                    datePickerDialog.show();
                    dateFormat = ProjectConfiguration.DD_MM_YYYY;
                    hideSoftKeyboard();
                    editTextBirthDate.setEnabled( true );
                    break;
                case R.id.button_submit:
                    getPersonalData();
                    break;
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
            }
        }
    };
    
    private void getPersonalData(){
        String name = editTextName.getText() != null ? editTextName.getText().toString() : "";
        String birthDate = editTextBirthDate.getText() != null ? editTextBirthDate.getText().toString() : "";
        String countryTitle = userDetails.getCountryOfResidence() != null ? userDetails.getCountryOfResidence() : "";
        
        birthDate = DateConverter.formatDate(birthDate, "dd/MM/yyyy", "yyyy-MM-dd");
    
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put( ProjectConfiguration.FullName, name );
            jsonObject.put( ProjectConfiguration.birthDate, birthDate );
            jsonObject.put( ProjectConfiguration.gender, userDetails.getGender() );
            jsonObject.put( ProjectConfiguration.CountryOfResidence, countryTitle );
            presenter.editPersonalData( jsonObject );
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void setDate(String date) {
    
    }
    
    @Override
    public void successful(List<Country> countryObject) {
        countryList.addAll( countryObject );
    
        for(int index = 0; index < countryList.size(); index++){
            countryTitles.add( countryList.get(index).getCountry() );
        }
    
        spinnerCountry.post(new Runnable() {
            @Override
            public void run() {
                codesAdapter.notifyDataSetChanged();
            }
        });
    
        if( userDetails.getCountryOfResidence() != null ){
            int index = countryTitles.indexOf( userDetails.getCountryOfResidence() );
            spinnerCountry.setSelection( index );
        }else
            userDetails.setCountryOfResidence( countryList.get(0).getCountry() );
    }
    
    @Override
    public void successful(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        getActivity().onBackPressed();
    }
    
    @Override
    public void failure(String message, ErrorField field) {
    
    }
    
    
    private void hideSoftKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getActivity().getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View( context );
        }
        inputMethodManager.hideSoftInputFromWindow( view.getWindowToken(), 0 );
    }
}
