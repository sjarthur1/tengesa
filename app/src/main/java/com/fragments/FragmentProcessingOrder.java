package com.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.constants.ProjectConfiguration;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.presenter.StartFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProcessingOrder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProcessingOrder extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentProcessingOrder fragment;
    private View view;
    private TextView textViewError, textViewNext, textViewSuccessTitle, textViewSuccessMessage;
    private ImageView imageViewLogo;
    private LinearLayout linearLayoutSuccess, linearLayoutProgress;
    
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    
    public FragmentProcessingOrder() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProcessingOrder newInstance() {
        fragment = new FragmentProcessingOrder();
        return fragment;
    }
    
    public static FragmentProcessingOrder getInstance() {
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_processing_order, container, false);
        textViewError = view.findViewById( R.id.text_view_error );
        imageViewLogo       = view.findViewById( R.id.image_view_logo );
        textViewNext        = view.findViewById( R.id.text_view_next );
        linearLayoutSuccess = view.findViewById( R.id.linear_layout_success );
        linearLayoutProgress = view.findViewById( R.id.linear_layout_progress );
        textViewSuccessTitle = view.findViewById( R.id.text_view_success_title );
        textViewSuccessMessage = view.findViewById( R.id.text_view_success_message );
        
        ProjectConfiguration.setLogo( imageViewLogo );
        
        Bundle bundle = getArguments();
        String message = bundle.getString( ProjectConfiguration.message, null );
        if( message!= null ) {
            textViewError.setText(message);
            linearLayoutSuccess.setVisibility( View.GONE );
            linearLayoutProgress.setVisibility( View.VISIBLE );
        }
        
        textViewNext.setOnClickListener( onClickListener );
        
        return view;
    }
    
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            StartFragment.startFragment(getFragmentManager(), FragmentOrders.newInstance());
        }
    };
    
    public void goToPreviousPage(){
        MainActivity.getInstance().onBackPressed();
    }
    
    public void goToNextPage(String title, String message){
        textViewSuccessTitle.setText( title );
        textViewSuccessMessage.setText( message );
        linearLayoutSuccess.setVisibility( View.VISIBLE );
        linearLayoutProgress.setVisibility( View.GONE );
    }
}
