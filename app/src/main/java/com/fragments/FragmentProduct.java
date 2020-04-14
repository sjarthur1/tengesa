package com.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.*;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.adapters.FirstLayoutAdapter;
import com.adapters.PagerAdapter;
import com.adapters.ProductViewPagerAdapter;
import com.adapters.SpecificationAdapter;
import com.constants.ActionOption;
import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.ProductData;
import com.objects.Specifications;
import com.presenter.ProductPresenter;
import com.presenter.StartFragment;

import java.util.*;


/**
 * A simple for displaying product details.
 */
public class FragmentProduct extends Fragment implements ProductPresenter.ProductView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static FragmentProduct fragment;
    private Fragment newFragment;
    private View view;
    private ImageView imageViewItemImage, imageViewBack;
    private TextView textViewItemTitle, textViewPrice, textViewDescription, textViewCurrency;
    private Button buttonAddToCart, buttonAddToWishList;
    private Spinner spinnerQuantity;
    private ViewPager viewPagerImages;
    private RecyclerView recyclerViewPageIndicator, recyclerViewList, recyclerViewSpecifications;
    
    private Context context;
    private ArrayList<String> imageList;
    private ArrayList<Boolean> condition;
    List<ProductData> productDataList;
    List<Specifications> specificationsList;
    private ProductViewPagerAdapter productViewPagerAdapter;
    private PagerAdapter pagerAdapter;
    private ProductData productData;
    private ProductPresenter presenter;
    private FirstLayoutAdapter layoutAdapter;
    private SpecificationAdapter adapter;
    private AlertDialog alertDialog;
    private int number;
    private String userId = null;
    
    public FragmentProduct() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentProduct.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProduct newInstance() {
        fragment = new FragmentProduct();
        return fragment;
    }
    
    public static FragmentProduct getInstance(){
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_product, container, false);
    
        imageViewBack              = view.findViewById( R.id.image_view_back );
        imageViewItemImage         = view.findViewById( R.id.image_view_item_image );
        viewPagerImages            = view.findViewById( R.id.view_pager_images );
        textViewItemTitle          = view.findViewById( R.id.text_view_item_title );
        textViewPrice              = view.findViewById( R.id.text_view_price );
        textViewCurrency           = view.findViewById( R.id.text_view_currency );
        textViewDescription        = view.findViewById( R.id.text_view_description );
        recyclerViewPageIndicator  = view.findViewById( R.id.recycler_view_page_indicator );
        spinnerQuantity            = view.findViewById( R.id.spinner_quantity );
        buttonAddToCart            = view.findViewById( R.id.button_add_to_cart );
        buttonAddToWishList        = view.findViewById( R.id.button_add_to_wish_list );
        recyclerViewList           = view.findViewById( R.id.recycler_view_list );
        recyclerViewSpecifications = view.findViewById( R.id.recycler_view_specifications );
        
        context = getContext();
        
        presenter = new ProductPresenter(context, this);
        
        imageList = new ArrayList<>();
        condition = new ArrayList<>();
        
        Bundle bundle = getArguments();
        productData = bundle.containsKey("product") ? ( ProductData ) bundle.getSerializable("product") : null;
        
        userId = PreferenceManagement.readString( context, ProjectConfiguration.userId, null );
        
        if(userId == null )
            buttonAddToWishList.setVisibility(View.GONE);
        
        if( productData != null )
            inflateView();
        else{
            String productId = bundle.getString( ProjectConfiguration.productId, null );
            presenter.getProductById( productId );
        }
        
        imageViewBack.setOnClickListener( clickListener );
        buttonAddToCart.setOnClickListener( clickListener );
        buttonAddToWishList.setOnClickListener( clickListener );
        return view;
    }
    
    @Override
    public void onResume() {
        super.onResume();
    }
    
    private void inflateView(){
        Set<Map.Entry<String, Object>> dataSet = productData.getImages().entrySet();
        Iterator<Map.Entry<String, Object>> iterator = dataSet.iterator();
        while (iterator.hasNext()){
            String imageKey = iterator.next().getKey();
            String imageValue = productData.getImages().get(imageKey).toString();
            imageList.add( imageValue );
        }
        number = dataSet.size();
        for(int count = 0; count < number; count++){
            if(count == 0)
                condition.add(true);
            else
                condition.add(false);
        }
        pagerAdapter = new PagerAdapter(getContext(), condition);
        recyclerViewPageIndicator.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        recyclerViewPageIndicator.setAdapter( pagerAdapter );
    
        productViewPagerAdapter = new ProductViewPagerAdapter(getChildFragmentManager(), imageList);
        viewPagerImages.setCurrentItem(0);
        viewPagerImages.setOffscreenPageLimit(imageList.size()-1);
        viewPagerImages.setAdapter( productViewPagerAdapter );
        viewPagerImages.addOnPageChangeListener( onPageChangeListener );
        
        textViewItemTitle.setText( productData.getTitle() );
        textViewPrice.setText( productData.getPrice() );
        textViewDescription.setText( productData.getDescription() );
        textViewCurrency.setText( productData.getCurrency() );
        int quantity = Integer.parseInt(productData.getStock());
    
        Map<String, Object> specifications = productData.getSpecification();
        Iterator<Map.Entry<String, Object>> entryIterator = specifications.entrySet().iterator();
        specificationsList = new ArrayList<>();
        while(entryIterator.hasNext()){
            Map.Entry<String, Object> entry = entryIterator.next();
            String key = entry.getKey();
            String value = entry.getValue().toString();
            if(value != null && !value.equals(""))
                specificationsList.add(new Specifications(key, value));
        }
        
        adapter = new SpecificationAdapter(context, specificationsList);
        recyclerViewSpecifications.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL, false));
        recyclerViewSpecifications.setAdapter(adapter);
        
        int index = quantity > 0 ? 1 : 0;
        ArrayList<Integer> quantityArray = new ArrayList<>();
        quantityArray.clear();
        for(int count = index; count <= quantity; count++){
            quantityArray.add(count);
        }
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter( context, android.R.layout.simple_list_item_1, quantityArray);
        //arrayAdapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spinnerQuantity.setAdapter( arrayAdapter );
    
        productDataList = presenter.currentProductList;
        layoutAdapter = new FirstLayoutAdapter(context, productDataList, getFragmentManager());
        recyclerViewList.setLayoutManager(new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false));
        recyclerViewList.setAdapter(layoutAdapter);
        
        presenter.getRelatedProducts( productData.getCategoryID(), productData.getProductID() );
    }
    
    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            condition.set(position, false);
        }
    
        @Override
        public void onPageSelected(int position) {
            condition.set(position, true);
            recyclerViewPageIndicator.post(new Runnable() {
                @Override
                public void run() {
                    pagerAdapter.notifyDataSetChanged();
                }
            });
        }
    
        @Override
        public void onPageScrollStateChanged(int state) {
        
        }
    };
    
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_add_to_cart:
                    String quantity = spinnerQuantity.getSelectedItem().toString();
                    presenter.addToCart( productData.getProductID(), quantity, productData.getTitle() );
                    break;
                case R.id.button_add_to_wish_list:
                    //showDialog();
                    presenter.saveToWishList( productData.getProductID(), productData.getTitle() );
                    break;
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
            }
        }
    };
    
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder( context );
        builder.setTitle("InduceSmile Code Snippet");
        builder.setMessage("This is Alert Dialog Box with Positive and Negative Button with a custom position");
        builder.setCancelable(false)
                .setPositiveButton("Go to Wish List", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).setCancelable( true );
        alertDialog = builder.create();
        alertDialog.show();
    
        alertDialog.getWindow().setGravity(Gravity.TOP);
    }
    
    @Override
    public void successful(ProductData productList) {
        productData = productList;
        inflateView();
    }
    
    @Override
    public void successful(List<ProductData> productList) {
        productDataList.addAll( productList );
        recyclerViewList.post(new Runnable() {
            @Override
            public void run() {
                layoutAdapter.notifyDataSetChanged();
            }
        });
    }
    
    @Override
    public void successful(String title, String message, final ActionOption action) {
        String buttonText = "Go to Wish List";
        if(action.equals(ActionOption.cart)){
            buttonText = "Go to Cart";
        }
        Toast.makeText( context, message, Toast.LENGTH_LONG ).show();
        
       /* AlertDialog.Builder builder = new AlertDialog.Builder( context );
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false)
                .setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if(action.equals(ActionOption.cart)){
                            newFragment = FragmentMyCart.newInstance();
                            StartFragment.startFragment(getFragmentManager(), "Cart", newFragment);
                        }else if(action.equals(ActionOption.wishlist)){
                            newFragment = FragmentWishList.newInstance();
                            StartFragment.startFragment(getFragmentManager(), "Cart", newFragment);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                }).setCancelable(true);
        alertDialog = builder.create();
        alertDialog.show();
    
        alertDialog.getWindow().setGravity(Gravity.TOP);*/
    }
    
    @Override
    public void failed(String error) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show();
    }
    
    @Override
    public void failed() {
        Log.e("Addddddd", "Failed");
    }
}
