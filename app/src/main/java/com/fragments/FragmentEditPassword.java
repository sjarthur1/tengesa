package com.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.*;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.constants.ErrorField;
import com.constants.ProjectConfiguration;
import com.mobile.tengesa.R;
import com.objects.UserDetails;
import com.presenter.EditPasswordPresenter;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentEditPassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEditPassword extends DialogFragment implements EditPasswordPresenter.EditPasswordView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentEditPassword fragment;
    private static final String ARG_PARAM2 = "param2";
    private UserDetails userDetails;
    
    private View view;
    private EditText editTextOldPassword, editTextPassword, editTextConfirmPassword;
    private Button buttonSubmit;
    private TextView textViewClose, textViewError;
    private String oldPassword, password, confirmPassword, email;
    private Context context;
    private EditPasswordPresenter presenter;
    
    public FragmentEditPassword() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of this fragment.
     * @return A new instance of fragment FragmentEditPassword.
     */
    public static FragmentEditPassword newInstance() {
        fragment = new FragmentEditPassword();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_edit_password, container, false );
        
        context = getContext();
        
        editTextOldPassword = view.findViewById( R.id.edit_text_old_password );
        editTextPassword = view.findViewById( R.id.edit_text_password );
        editTextConfirmPassword = view.findViewById( R.id.edit_text_confirm_password );
        buttonSubmit = view.findViewById( R.id.button_submit );
        textViewError = view.findViewById( R.id.text_view_error );
        textViewClose = view.findViewById( R.id.text_view_close );
        
        buttonSubmit.setOnClickListener(clickListener);
        textViewClose.setOnClickListener(clickListener);
    
        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(R.drawable.dialog_background);
        window.setLayout(300, LinearLayout.LayoutParams.WRAP_CONTENT);
        
        if(presenter == null)
            presenter = new EditPasswordPresenter(context, this);
    
        Bundle bundle = getArguments();
        if( bundle != null ) {
            userDetails = bundle.containsKey(ProjectConfiguration.UserDetails) ?
                    (UserDetails) bundle.getSerializable(ProjectConfiguration.UserDetails) : null;
        }
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.text_view_close:
                    dismiss();
                    break;
                case R.id.button_submit:
                    oldPassword = editTextOldPassword.getText().toString();
                    password = editTextPassword.getText().toString();
                    confirmPassword = editTextConfirmPassword.getText().toString();
                    email = userDetails.getEmail();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put(ProjectConfiguration.oldPassword, oldPassword);
                        jsonObject.put(ProjectConfiguration.password, password);
                        jsonObject.put(ProjectConfiguration.Email, email);
                        jsonObject.put(ProjectConfiguration.confirmPassword, confirmPassword);
                        presenter.editPassword(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    
    @Override
    public void successful(String message) {
        Toast.makeText(context, message+"", Toast.LENGTH_LONG).show();
        dismiss();
    }
    
    @Override
    public void failure(String message, ErrorField field) {
        if(field.equals(ErrorField.oldPassword)){
            editTextOldPassword.setError(message+"");
            editTextOldPassword.requestFocus();
        }else if(field.equals(ErrorField.password)){
            editTextPassword.setError(message+"");
            editTextPassword.requestFocus();
        }else if(field.equals(ErrorField.confirmPassword)){
            editTextConfirmPassword.setError(message+"");
            editTextConfirmPassword.requestFocus();
        }else{
            textViewError.setText(message+"");
            textViewError.setVisibility(View.VISIBLE);
        }
    }
}
