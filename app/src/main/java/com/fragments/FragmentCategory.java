package com.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adapters.ItemListAdapter;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.ProductData;
import com.presenter.SingleCategoryPresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link FragmentCategory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCategory extends Fragment implements SingleCategoryPresenter.CategoryView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private View view;
    private static FragmentCategory fragment;
    private Activity activity;
    private Context context;
    
    private EditText editTextSearch;
    private ImageView imageViewSearch, buttonCancel, imageViewBack;
    private RecyclerView recyclerViewCategory;
    
    private SingleCategoryPresenter presenter;
    private List<ProductData> productList, searchContacts;
    private ItemListAdapter itemListAdapter;
    private String categoryId, searchText;
    
    public FragmentCategory() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentCategory.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentCategory newInstance() {
        fragment = new FragmentCategory();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false);
        
        context = getContext();
        activity = getActivity();
    
        imageViewBack        = view.findViewById( R.id.image_view_back );
        editTextSearch       = view.findViewById( R.id.edit_text_search );
        imageViewSearch      = view.findViewById( R.id.image_view_search );
        buttonCancel         = view.findViewById( R.id.button_cancel );
        recyclerViewCategory = view.findViewById( R.id.recycler_view_category );
        
        imageViewBack.setOnClickListener( onClickListener );
        imageViewSearch.setOnClickListener(onClickListener);
        buttonCancel.setOnClickListener(onClickListener);
        
        if(presenter == null)
            presenter = new SingleCategoryPresenter(this);
        
        Bundle bundle = getArguments();
            categoryId = bundle.getString("CategoryId", null);
            searchText = bundle.containsKey( "SEARCH_TEXT" ) ? bundle.getString( "SEARCH_TEXT" ) : null;
        
        if(productList == null )
            productList = new ArrayList<>();
    
        itemListAdapter = new ItemListAdapter(getContext(), productList, getFragmentManager());
        recyclerViewCategory.setLayoutManager( new GridLayoutManager(getContext(), 2) );
        recyclerViewCategory.setAdapter( itemListAdapter );
    
        //getCurrentProducts();
        if( presenter.productLists.size()== 0 && searchText == null )
            presenter.getCategoryById(categoryId);
        
        editTextSearch.addTextChangedListener( textWatcher );
        return view;
    }
    
    
    
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.image_view_search) {
                String searchText = editTextSearch.getText().toString();
                presenter.getSearchProductResults(searchText);
            } else if (view.getId() == R.id.button_cancel) {
                editTextSearch.setText("");
            } else if (view.getId() == R.id.button_cancel) {
                MainActivity.getInstance().onBackPressed();
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
        List<ProductData> products = presenter.productLists;
        searchContacts = new ArrayList<>();
        if( searchString.length() > 0 ){
            for(int counter = 0; counter < products.size(); counter++){
                ProductData productData = products.get(counter);
                if( productData.getTitle().toLowerCase().contains( searchString ) ){
                    searchContacts.add( productData );
                }
            }
        }
    }
    
    
    public void addListToAdapter(final String s){
        if(s.length() > 0) {
            itemListAdapter = new ItemListAdapter( getContext(), searchContacts, getFragmentManager() );
            buttonCancel.setVisibility( View.VISIBLE );
        }else{
            itemListAdapter = new ItemListAdapter( getContext(), productList, getFragmentManager() );
            buttonCancel.setVisibility( View.GONE );
        }
        refreshList();
    }
    
    public void refreshList(){
        recyclerViewCategory.post(new Runnable() {
            @Override
            public void run() {
                recyclerViewCategory.setAdapter( itemListAdapter );
                itemListAdapter.notifyDataSetChanged();
            }
        });
        
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if( searchText != null ){
            presenter.getSearchProductResults( searchText );
        }
    }
    
    private void getCurrentProducts(){
        productList = presenter.getProductLists();
        itemListAdapter = new ItemListAdapter(getContext(), productList, getFragmentManager());
        recyclerViewCategory.setAdapter( itemListAdapter );
    }
    
    @Override
    public void updateInterface(List<ProductData> productList) {
        this.productList.clear();
        this.productList = productList;
        recyclerViewCategory.post(new Runnable() {
            @Override
            public void run() {
                itemListAdapter.notifyDataSetChanged();
            }
        });
        itemListAdapter = new ItemListAdapter(getContext(), productList, getFragmentManager());
        recyclerViewCategory.setAdapter( itemListAdapter );
    }
}
