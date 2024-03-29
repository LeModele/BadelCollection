package com.modele.badelcollection;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyWishlistFragment extends Fragment {


    public MyWishlistFragment() {
        // Required empty public constructor
    }

    private RecyclerView wishlistRecyclerView;
    private Dialog loadingDialog;
    public static WishlistAdapter wishlistAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_wishlist, container, false);

        ///// loading Dialog
        loadingDialog = new Dialog(getContext());
        loadingDialog.setContentView(R.layout.loading_progress_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setBackgroundDrawableResource(R.drawable.slider_background);
        //loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background)); // valab aparti de api 21
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        ///// loading Dialog

        wishlistRecyclerView = view.findViewById(R.id.my_wishlist_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        wishlistRecyclerView.setLayoutManager(linearLayoutManager);

        //List<WishlistModel> wishlistModelList =new ArrayList<>();

        if(DBqueries.wishlistModelList.size()==0){
            DBqueries.wishList.clear();
            DBqueries.loadWishList(getContext(),loadingDialog,true);
        }else {
            loadingDialog.dismiss();
        }

        wishlistAdapter = new WishlistAdapter(DBqueries.wishlistModelList,true);
        wishlistRecyclerView.setAdapter(wishlistAdapter);
        wishlistAdapter.notifyDataSetChanged();
        return view;
    }

}
