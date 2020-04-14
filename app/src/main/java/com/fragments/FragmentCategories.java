package com.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.adapters.ItemListAdapter;
import com.constants.InputValidator;
import com.constants.ProjectConfiguration;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.ProductData;
import com.objects.list_objects.Categories;
import com.presenter.CategoriesPresenter;
import com.presenter.StartFragment;

import java.util.ArrayList;
import java.util.List;


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
    private ImageView imageViewSearch, imageViewLogo;
    private RecyclerView recyclerViewCategories;
    
    private CategoriesPresenter presenter;
    private ArrayList<Categories.MainView> categoryLists, searchCategoryLists;
    private CategoriesAdapter categoriesAdapter;
    private Context context;
    private String searchText;
    
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
        
        context = getContext();
    
        imageViewLogo = view.findViewById( R.id.image_view_logo );
        textViewTitle = view.findViewById( R.id.text_view_title );
        editTextSearch = view.findViewById( R.id.edit_text_search );
        imageViewSearch = view.findViewById( R.id.image_view_search );
        recyclerViewCategories = view.findViewById( R.id.recycler_view_categories );
        
        if(presenter == null)
            presenter = new CategoriesPresenter( context, this );
    
        if(categoryLists == null)
            categoryLists = new ArrayList<>();
    
        categoriesAdapter = new CategoriesAdapter( getContext(), categoryLists, getActivity().getSupportFragmentManager() );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewCategories.setLayoutManager(layoutManager);
        recyclerViewCategories.setAdapter( categoriesAdapter );
    
    
        //getCurrentProducts();
        if(presenter.category.size() == 0)
            presenter.getCategories();
        
        editTextSearch.addTextChangedListener( textWatcher );
        imageViewSearch.setOnClickListener( clickListener );
    
        ProjectConfiguration.setLogo( imageViewLogo );
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        
    }
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch ( view.getId() ) {
                case R.id.image_view_search:
                    String searchText = editTextSearch.getText().toString();
                    Fragment category = FragmentCategory.newInstance();
                    Bundle bundle = new Bundle();
                    bundle.putString("SEARCH_TEXT", searchText);
                    category.setArguments(bundle);
                    StartFragment.startFragment( getFragmentManager(), "CategoryItems", category );
                    break;
            }
        }
    };
    
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        
        }
        
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() > 0){
                filterSearch( s.toString() );
            }
        }
        
        @Override
        public void afterTextChanged(Editable s) {
            addListToAdapter(s.toString());
        }
    };
    
    private void filterSearch( String searchString ){
        searchCategoryLists = new ArrayList<>();
        searchText = InputValidator.validateText( searchString );
        if( searchText.length() > 0 ){
            for(int counter = 0; counter < categoryLists.size(); counter++){
                Categories.MainView categoryItem = categoryLists.get(counter);
                if( categoryItem.getCategoryName().toLowerCase().contains( searchText ) ){
                    searchCategoryLists.add( categoryItem );
                }
            }
        }
    }
    
    
    public void addListToAdapter(final String s){
        searchText = InputValidator.validateText( s );
        if(searchText.length() > 0) {
            categoriesAdapter = new CategoriesAdapter( getContext(), searchCategoryLists, getFragmentManager() );
            //buttonCancel.setVisibility( View.VISIBLE );
        }else{
            categoriesAdapter = new CategoriesAdapter( getContext(), categoryLists, getFragmentManager() );
            //buttonCancel.setVisibility( View.GONE );
        }
        refreshList();
    }
    
    public void refreshList(){
        recyclerViewCategories.post(new Runnable() {
            @Override
            public void run() {
                recyclerViewCategories.setAdapter( categoriesAdapter );
                categoriesAdapter.notifyDataSetChanged();
            }
        });
        
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
