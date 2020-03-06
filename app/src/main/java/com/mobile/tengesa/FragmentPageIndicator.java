package com.mobile.tengesa;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentPageIndicator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPageIndicator extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private View view;
    private static FragmentPageIndicator fragment;
    
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    
    public FragmentPageIndicator() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentPageIndicator.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPageIndicator newInstance() {
        fragment = new FragmentPageIndicator();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_page_indicator, container, false);
        
        return view;
    }
    
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    
    }
    
}
