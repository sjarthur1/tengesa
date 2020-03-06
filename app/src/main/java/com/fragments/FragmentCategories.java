package com.fragments;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.adapters.CategoriesAdapter;
import com.mobile.tengesa.R;
import com.objects.list_objects.Categories;
import com.presenter.CategoriesPresenter;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentCategories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCategories extends Fragment implements CategoriesPresenter.CategoriesView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentCategories fragment;
    private View view;
    
    private TextView textViewTitle;
    private EditText editTextSearch;
    private ImageView imageViewSearch;
    private RecyclerView recyclerViewCategories;
    
    private CategoriesPresenter presenter;
    private ArrayList<Categories.MainView> categoryLists;
    private CategoriesAdapter categoriesAdapter;
    
    public FragmentCategories() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentCategories.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCategories newInstance() {
        fragment = new FragmentCategories();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_categories, container, false);
    
        textViewTitle = view.findViewById( R.id.text_view_title );
        editTextSearch = view.findViewById( R.id.edit_text_search );
        imageViewSearch = view.findViewById( R.id.image_view_search );
        recyclerViewCategories = view.findViewById( R.id.recycler_view_categories );
        
        if(presenter == null)
            presenter = new CategoriesPresenter(this);
    
        if(categoryLists == null)
            categoryLists = new ArrayList<>();
    
        categoriesAdapter = new CategoriesAdapter( getContext(), categoryLists, getActivity().getSupportFragmentManager() );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewCategories.setLayoutManager(layoutManager);
        recyclerViewCategories.setAdapter( categoriesAdapter );
    
    
        //getCurrentProducts();
        if(presenter.category.size() == 0)
            presenter.getCategories();
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
    }
    
    private void getCurrentProducts(){
        categoryLists = presenter.getCurrentCategories();
    
        categoriesAdapter = new CategoriesAdapter(getContext(), categoryLists, getFragmentManager());
        recyclerViewCategories.setAdapter( categoriesAdapter );
    }
    
    @Override
    public void showCategories(Categories categories) {
        categoryLists = categories.getMainView();
        categoriesAdapter = new CategoriesAdapter(getContext(), categoryLists, getFragmentManager());
        recyclerViewCategories.setAdapter( categoriesAdapter );
    }
}
