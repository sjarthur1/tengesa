package com.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adapters.SecondHorizontalLayoutAdapter;
import com.mobile.tengesa.R;
import com.objects.ProductData;
import com.presenter.HorizontalWithoutButtonPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentHorizontalWithoutButton#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHorizontalWithoutButton extends Fragment implements HorizontalWithoutButtonPresenter.HorizontalWithoutButtonView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentHorizontalWithoutButton fragment;
    private static final String ARG_PARAM2 = "param2";
    private Context context;
    
    // TODO: Rename and change types of parameters
    private View view;
    private List<ProductData> items;
    SecondHorizontalLayoutAdapter secondHorizontalLayoutAdapter;
    private RecyclerView recyclerViewSecondList;
    private HorizontalWithoutButtonPresenter presenter;
    
    public FragmentHorizontalWithoutButton() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentHorizontalWithoutButton.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHorizontalWithoutButton newInstance() {
        fragment = new FragmentHorizontalWithoutButton();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_horizontal_without_button, container, false);
        
        context = getContext();
        if(presenter == null)
            presenter = new HorizontalWithoutButtonPresenter( context, this);
    
        recyclerViewSecondList = view.findViewById( R.id.recycler_view_list );
        items = new ArrayList<>();
    
        secondHorizontalLayoutAdapter = new SecondHorizontalLayoutAdapter(getContext(), items, getFragmentManager());
        recyclerViewSecondList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewSecondList.setAdapter( secondHorizontalLayoutAdapter );
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        loadImages();
    }
    
    private void loadImages(){
        items = presenter.currentProductList;
        secondHorizontalLayoutAdapter = new SecondHorizontalLayoutAdapter( getContext(), items, getFragmentManager() );
        recyclerViewSecondList.setAdapter( secondHorizontalLayoutAdapter );
    }
    
    @Override
    public void successful(List<ProductData> productList) {
        items = productList;
        secondHorizontalLayoutAdapter = new SecondHorizontalLayoutAdapter(getContext(), items, getFragmentManager());
        recyclerViewSecondList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewSecondList.setAdapter( secondHorizontalLayoutAdapter );
    }
    
    @Override
    public void failure(String error) {
    
    }
}
