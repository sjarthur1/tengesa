package com.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adapters.WishListItemAdapter;
import com.constants.ActionOption;
import com.mobile.tengesa.MainActivity;
import com.mobile.tengesa.R;
import com.objects.WishListData;
import com.presenter.StartFragment;
import com.presenter.WishlistPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link FragmentWishList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentWishList extends Fragment implements WishlistPresenter.WishListView {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static FragmentWishList fragment;
    private WishlistPresenter presenter;
    private Context context;
    private List<WishListData> wishList;
    private WishListItemAdapter adapter;
    
    private View view;
    private Fragment newFragment;
    private ImageView imageViewBack;
    private RecyclerView recyclerViewWishList;
    private AlertDialog alertDialog;
    private String mParam2;
    
    public FragmentWishList() {
        // Required empty public constructor
    }
    
    /**
     * Use this factory method to create a new instance of
     * this fragment
     */
    public static FragmentWishList newInstance() {
        fragment = new FragmentWishList();
        return fragment;
    }
    public static FragmentWishList getInstance() {
        return fragment;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wish_list, container, false);
        context = getContext();
        
        if(presenter == null)
            presenter = new WishlistPresenter(context, this);
    
        imageViewBack    = view.findViewById( R.id.image_view_back );
        recyclerViewWishList = view.findViewById( R.id.recycler_view_wish_list );
        
        imageViewBack.setOnClickListener( onClickListener );
        wishList = presenter.wishList;
        adapter = new WishListItemAdapter( context, wishList, getFragmentManager(), presenter);
        recyclerViewWishList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerViewWishList.setAdapter( adapter );
    
        if (wishList.size() == 0)
            presenter.getWishList();
        return view;
    }
    
    
    
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.image_view_back:
                    MainActivity.getInstance().onBackPressed();
                    break;
            }
        }
    };
    
    public void onButtonPressed() {
    }
    
    @Override
    public void returnWishList(List<WishListData> wishListData) {
        if(wishListData != null) {
            wishList.addAll(wishListData);
            recyclerViewWishList.post(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }
    
    @Override
    public void successful(int position) {
        if(wishList.size() > position)
            wishList.remove( position );
        recyclerViewWishList.post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
    
    @Override
    public void successful(String title, String message, final ActionOption action) {
        String buttonText = "Go to Wish List";
        if(action.equals(ActionOption.cart)){
            buttonText = "Go to Cart";
        }
    
        AlertDialog.Builder builder = new AlertDialog.Builder( context );
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
    
        alertDialog.getWindow().setGravity(Gravity.TOP);
        
    }
    
    @Override
    public void successful() {
    
    }
    
    @Override
    public void failure(String message) {
        Toast.makeText( context, message+"", Toast.LENGTH_LONG ).show();
    }
}
