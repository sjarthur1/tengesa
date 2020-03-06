package com.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.*;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import com.adapters.HomeAdapter;
import com.adapters.MainBannerAdapter;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.HomeProductCategories;
import com.objects.ProductData;
import com.presenter.HomePresenter;
import com.presenter.StartFragment;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHome} interface
 * to handle interaction events.
 * Use the {@link FragmentHome#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHome extends Fragment implements HomePresenter.HomeInterface {

    private static FragmentHome fragmentHome;
    private View view;
    private HomePresenter presenter;
    private Context context;
    
    
    private Fragment fragment;
    private EditText editTextSearch;
    private ImageView imageViewSearch;
    private ViewPager viewPagerBanner;
    private TextView textViewError;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout linearLayoutTryAgain;
    private RelativeLayout relativeLayoutReload;
    private RecyclerView recyclerViewCategories;
    private LinearLayout.LayoutParams params;
    private ProgressBar progressBarMain;
    private Button buttonTryAgain;
    
    List<HomeProductCategories> currentCategories;
    private MainBannerAdapter mainBannerAdapter;
    private int scrollValue = 0;
    private HomeAdapter homeAdapter;
    //private int height, scroll=230;
    private boolean refreshing = false;

    public FragmentHome() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentHome.
     **/
    // TODO: Rename and change types and number of parameters
    public static FragmentHome newInstance() {
        fragmentHome = new FragmentHome();
        return fragmentHome;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        
        context = getContext();
        
        if(presenter == null)
            presenter = new HomePresenter( context, this );
        
        recyclerViewCategories = view.findViewById( R.id.recycler_view_categories );
        linearLayoutTryAgain   = view.findViewById( R.id.linear_layout_try_again );
        relativeLayoutReload   = view.findViewById( R.id.relative_layout_reload );
        editTextSearch         = view.findViewById( R.id.edit_text_search );
        imageViewSearch        = view.findViewById( R.id.image_view_search );
        viewPagerBanner        = view.findViewById( R.id.view_pager_banner );
        textViewError          = view.findViewById( R.id.text_view_error );
        buttonTryAgain         = view.findViewById( R.id.button_try_again );
        progressBarMain        = view.findViewById( R.id.progress_bar_main );
        swipeRefresh           = view.findViewById( R.id.swipe_refresh );
        swipeRefresh.setColorSchemeColors( getResources().getColor(R.color.colorPrimary) );
        
        imageViewSearch.setOnClickListener( clickListener );
        buttonTryAgain.setOnClickListener( clickListener );
        
        if(currentCategories == null)
            currentCategories = new ArrayList<>();
        homeAdapter = new HomeAdapter(context, currentCategories, getFragmentManager(), presenter);
        recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewCategories.setAdapter( homeAdapter );
    
        swipeRefresh.setOnRefreshListener( onRefreshListener );
        
        if( presenter.currentProductList.size() == 0 ) {
            reload();
            presenter.getHomeBanners();
        }
        //presenter.getCategoryById("85", "Second");
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        /*if( scrollValue < 300 ){
            linearLayoutHead.setVisibility(View.VISIBLE);
        }else{
            linearLayoutHead.setVisibility(View.GONE);
        }*/
        initialization();
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
                    StartFragment.startFragment(getFragmentManager(), "CategoryItems", category);
                    break;
                case R.id.button_try_again:
                    MainActivity.getInstance().getCart();
                    reload();
                    currentCategories.clear();
                    //currentCategories.add(new HomeProductCategories("089"));
                    presenter.getHomeBanners();
                    break;
            }
        }
    };
    
    private void initialization(){
        /*if(currentCategories == null) {
            if (presenter.currentCategories.size() > 0) {
                for (int counter = 0; counter < presenter.currentCategories.size(); counter++) {
                    currentCategories.add(presenter.currentCategories.get(counter));
            
                    homeAdapter = new HomeAdapter(getContext(), currentCategories, getFragmentManager(), presenter);
                    recyclerViewCategories.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    recyclerViewCategories.setAdapter(homeAdapter);
                }
            }
        }*/
    }
    
    private void getUser(){
    
    }
    
    private void showReload(){
        relativeLayoutReload.setVisibility( View.VISIBLE );
        linearLayoutTryAgain.setVisibility( View.VISIBLE );
        progressBarMain.setVisibility( View.GONE );
    }
    
    private void reload(){
        relativeLayoutReload.setVisibility( View.VISIBLE );
        progressBarMain.setVisibility( View.VISIBLE );
        linearLayoutTryAgain.setVisibility( View.GONE );
    }
    
    SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if( !refreshing ){
                refreshing = true;
                swipeRefresh.setRefreshing( true );
                currentCategories.clear();
                //currentCategories.add(new HomeProductCategories("089"));
                //presenter.getCategories();
                presenter.getHomeBanners();
            }
        }
    };
    
    @Override
    public void successful(List<HomeProductCategories> categories) {
    
    }
    
    @Override
    public void bannersReturned(List<String> banners) {
        currentCategories.add(new HomeProductCategories("5765765"));
    }
    
    @Override
    public void successful(List<ProductData> products, int round) {
        relativeLayoutReload.setVisibility( View.GONE );
        if(products.size() > 0) {
            if (!currentCategories.contains(presenter.currentCategories.get(round))) {
                currentCategories.add(presenter.currentCategories.get(round));
                recyclerViewCategories.post(new Runnable() {
                    @Override
                    public void run() {
                        homeAdapter.notifyDataSetChanged();
                    }
                });
            }
        }
        
        swipeRefresh.setRefreshing( false );
        refreshing = false;
    }
    
    @Override
    public void failure( String message ) {
        swipeRefresh.setRefreshing( false );
        refreshing = false;
        textViewError.setText( message );
        showReload();
    }
}
