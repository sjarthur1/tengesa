package com.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.constants.ProjectConfiguration;
import com.helpers.PreferenceManagement;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.OrderData;
import com.objects.Orders;
import com.objects.ProductOrder;
import com.objects.UserAddresses;
import com.presenter.PaymentPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FragmentPayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPayment extends Fragment implements PaymentPresenter.PaymentView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentPayment fragment;
    private View view;
    private ImageView imageViewBack;
    private TextView textViewSubTotal, textViewShipping, textViewTotal, textViewName, textViewAddress, textViewPhone, textViewExtraShipping;
    private Button buttonPlaceOrder;
    private LinearLayout linearLayoutExtraShipping;
    
    // TODO: Rename and change types of parameters
    private PaymentPresenter presenter;
    private Context context;
    
    public FragmentPayment() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment
     * @return A new instance of fragment FragmentPayment.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPayment newInstance() {
        fragment = new FragmentPayment();
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_payment, container, false);
        
        context = getContext();
        initialize();
        
        return view;
    }
    
    // TODO: Rename method, update argument and hook method into UI event
    public void initialize() {
        imageViewBack    = view.findViewById( R.id.image_view_back );
        textViewSubTotal = view.findViewById( R.id.text_view_sub_total );
        textViewShipping = view.findViewById( R.id.text_view_shipping );
        textViewTotal    = view.findViewById( R.id.text_view_total );
        textViewName     = view.findViewById( R.id.text_view_name );
        textViewAddress  = view.findViewById( R.id.text_view_address );
        textViewPhone    = view.findViewById( R.id.text_view_phone );
        textViewExtraShipping = view.findViewById( R.id.text_view_extra_shipping );
        linearLayoutExtraShipping = view.findViewById( R.id.linear_layout_extra_shipping );
        buttonPlaceOrder = view.findViewById( R.id.button_place_order );
        
        presenter = new PaymentPresenter( context, this );
        
        Bundle bundle = getArguments();
        UserAddresses address = bundle.containsKey( ProjectConfiguration.ADDRESS ) ? ( UserAddresses ) bundle.getParcelable( ProjectConfiguration.ADDRESS ) : null;
    
        List<ProductOrder> productOrderList = MainActivity.getInstance().getCartList();
        OrderData orderData = MainActivity.getInstance().getOrderData();
        textViewSubTotal.setText( orderData.getSubTotal()+"" );
        textViewShipping.setText( orderData.getShippingAmount()+"" );
        textViewTotal.setText( orderData.getTotalAmount()+"" );
        textViewPhone.setText( address.getCountry_Code() + address.getMobile_Number() );
        textViewName.setText( address.getFullname() );
        textViewAddress.setText( address.getArea()+", "+address.getArea() );
        
        presenter.setOrderAddress( address );
        presenter.setTotalAmount( orderData );
        presenter.setProductList( productOrderList );
        
        imageViewBack.setOnClickListener( onClickListener );
        buttonPlaceOrder.setOnClickListener( onClickListener );
        presenter.getAdditionalShippingFee( address.getCity() );
        
    }
    
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick( View view ) {
            switch (view.getId()) {
                case R.id.button_place_order:
                    presenter.pressOrder();
                    break;
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
            }
        }
    };
    
    
    @Override
    public void successful( double amount ) {
        if( amount > 0 ){
            textViewExtraShipping.setText( amount+"" );
            linearLayoutExtraShipping.setVisibility( View.VISIBLE );
        }else
            linearLayoutExtraShipping.setVisibility( View.GONE );
        buttonPlaceOrder.setEnabled( true );
    }
    
    @Override
    public void successful(Orders orders) {
        PreferenceManagement.RemoveItem( context, ProjectConfiguration.productId );
        MainActivity.getInstance().getCart();
        Toast.makeText( context, "order successful", Toast.LENGTH_LONG ).show();
    }
    
    @Override
    public void failure(String message, PaymentPresenter.ErrorType errorType) {
        buttonPlaceOrder.setEnabled( true );
    }
}
