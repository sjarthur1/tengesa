package com.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.constants.ProjectConfiguration;
import com.google.android.material.textfield.TextInputEditText;
import com.mobile.access_control.ActivityAccessControl;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.presenter.LoginPresenter;
import com.presenter.StartFragment;


/**
 * Login fragment for the whole application
 */
public class FragmentLogin extends Fragment implements LoginPresenter.LoginView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static FragmentLogin fragment;
    private View view;
    private LoginPresenter presenter;
    private TextInputEditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgotPassword, textViewRegister;
    private ImageView imageViewLogo, imageViewPassword;
    
    private String email, password, page;
    private Context context;
    private Activity activity;
    private Intent intent;
    private ProgressDialog progressDialog;
    
    public FragmentLogin() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogin newInstance() {
        fragment = new FragmentLogin();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        
        }
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        
        context = getContext();
        activity = getActivity();
        
        Bundle bundle = getArguments();
        if(bundle != null)
            page = bundle.getString( ProjectConfiguration.PAGE, null );
        
        imageViewLogo          = view.findViewById( R.id.image_view_logo );
        editTextEmail          = view.findViewById( R.id.edit_text_email );
        editTextPassword       = view.findViewById( R.id.edit_text_password );
        imageViewPassword      = view.findViewById( R.id.image_view_password );
        buttonLogin            = view.findViewById( R.id.button_login );
        textViewForgotPassword = view.findViewById( R.id.text_view_forgot_password );
        textViewRegister = view.findViewById( R.id.text_view_register );
        
        presenter = new LoginPresenter( context, this );
        
        buttonLogin.setOnClickListener( clickListener );
        textViewRegister.setOnClickListener( clickListener );
        imageViewPassword.setOnClickListener( clickListener );
        
        ProjectConfiguration.setLogoColored( imageViewLogo );
        return view;
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button_login:
                    buttonLogin.setEnabled( false );
                    email = editTextEmail.getText().toString();
                    password = editTextPassword.getText().toString();
                    presenter.login(email, password);
                    break;
                case R.id.text_view_register:
                    StartFragment.startFragment( getFragmentManager(), ProjectConfiguration.page_login, FragmentRegister.newInstance() );
                    break;
                case R.id.image_view_password:
                    togglePassword();
                    break;
            }
            
            
        }
    };
    
    @Override
    public void onResume() {
        super.onResume();
        MainActivity.getInstance();
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
    
    private void dismissDialog(){
        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }
    
    @Override
    public void usernameError(String error) {
        editTextEmail.setError( error );
        editTextEmail.requestFocus();
    }
    
    @Override
    public void showDialog() {
        progressDialog = ProgressDialog.show(context, "Loading", "Please wait while processing.");
    }
    
    @Override
    public void passwordError(String error) {
        editTextPassword.setError( error );
        editTextPassword.requestFocus();
        dismissDialog();
        buttonLogin.setEnabled(true);
    }
    
    @Override
    public void loginError( String message ) {
        Toast.makeText( context, message, Toast.LENGTH_LONG ).show();
        dismissDialog();
        buttonLogin.setEnabled(true);
    }
    
    @Override
    public void successful() {
        intent = new Intent( context, MainActivity.class );
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        if( page != null && page.equals( ProjectConfiguration.page_select_address ) ) {
            intent.putExtra( ProjectConfiguration.PAGE, ProjectConfiguration.page_select_address );
        }else
            intent.putExtra( ProjectConfiguration.PAGE, ProjectConfiguration.page_account );
        context.startActivity( intent );
        dismissDialog();
        buttonLogin.setEnabled(true);
        activity.finish();
    }
    
    
}
