package com.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobile.tengesa.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentHomeBanner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHomeBanner extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentHomeBanner fragment;
    private static final String ARG_PARAM2 = "param2";
    
    
    public FragmentHomeBanner() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentHomeBanner.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHomeBanner newInstance() {
        fragment = new FragmentHomeBanner();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_banner, container, false);
    }
    
    
}
