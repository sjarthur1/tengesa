package com.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.constants.ProjectConfiguration;
import com.google.android.material.textfield.TextInputEditText;
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
    
    private String email, password, page;
    private Context context;
    private Intent intent;
    
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_login, container, false);
        
        context = getContext();
        
        Bundle bundle = getArguments();
        if(bundle != null)
            page = bundle.getString( ProjectConfiguration.PAGE, null );
        
        editTextEmail = view.findViewById( R.id.edit_text_email );
        editTextPassword = view.findViewById( R.id.edit_text_password );
        buttonLogin = view.findViewById( R.id.button_login );
        textViewForgotPassword = view.findViewById( R.id.text_view_forgot_password );
        textViewRegister = view.findViewById( R.id.text_view_register );
        
        presenter = new LoginPresenter( context, this );
        
        buttonLogin.setOnClickListener( clickListener );
        textViewRegister.setOnClickListener( clickListener );
        return view;
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.button_login:
                    email = editTextEmail.getText().toString();
                    password = editTextPassword.getText().toString();
                    presenter.login(email, password);
                    break;
                case R.id.text_view_register:
                    
                    StartFragment.startFragment( getFragmentManager(), "Account", FragmentRegister.newInstance() );
                    break;
            }
            
            
        }
    };
    
    @Override
    public void usernameError(String error) {
        editTextEmail.setError( error );
        editTextEmail.requestFocus();
    }
    
    @Override
    public void passwordError(String error) {
        editTextPassword.setError( error );
        editTextPassword.requestFocus();
    }
    
    @Override
    public void loginError( String message ) {
        Toast.makeText( context, message, Toast.LENGTH_LONG ).show();
    }
    
    @Override
    public void successful() {
        intent = new Intent( context, MainActivity.class );
        if( page.equals( ProjectConfiguration.page_select_address ) ) {
            intent.putExtra( ProjectConfiguration.PAGE, ProjectConfiguration.page_select_address );
        }else
            intent.putExtra( ProjectConfiguration.PAGE, ProjectConfiguration.page_account );
        startActivity( intent );
    }
    
    
}
