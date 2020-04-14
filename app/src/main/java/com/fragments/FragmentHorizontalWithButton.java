package com.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import com.adapters.FirstLayoutAdapter;
import com.mobile.tengesa.R;
import com.objects.ProductData;
import com.presenter.HorizontalWithButtonPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentHorizontalWithButton#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHorizontalWithButton extends Fragment implements HorizontalWithButtonPresenter.HorizontalWithButtonView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentHorizontalWithButton fragment;
    private static final String ARG_PARAM2 = "param2";
    private List<ProductData> items;
    private Context context;
    
    // TODO: Rename and change types of parameters
    private View view;
    private RecyclerView recyclerViewFirstList;
    private HorizontalWithButtonPresenter presenter;
    FirstLayoutAdapter firstLayoutAdapter;
    
    public FragmentHorizontalWithButton() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHorizontalWithButton newInstance() {
        fragment = new FragmentHorizontalWithButton();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_horizontal_with_button, container, false);
    
        context = getContext();
        recyclerViewFirstList  = view.findViewById( R.id.recycler_view_list );
        
        if(presenter == null)
            presenter = new HorizontalWithButtonPresenter( context, this);
    
        items = new ArrayList<>();
    
        /*firstLayoutAdapter = new FirstLayoutAdapter(getContext(), items, getFragmentManager());
        recyclerViewFirstList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewFirstList.setAdapter( firstLayoutAdapter );*/
        
        //presenter.getCategoryById();
        
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
    public void successful(List<ProductData> productList) {
        items = productList;
        firstLayoutAdapter = new FirstLayoutAdapter(getContext(), items, getFragmentManager());
        recyclerViewFirstList.setAdapter( firstLayoutAdapter );
    }
    
    @Override
    public void failure(String error) {
    
    }
}
