package com.modele.badelcollection;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyCartFragment extends Fragment {


    public MyCartFragment() {
        // Required empty public constructor
    }

    private RecyclerView cartItemsRecyclerView;
    private Button continueBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_cart, container, false);

        cartItemsRecyclerView = view.findViewById(R.id.cart_items_recyclerview);
        continueBtn = view.findViewById(R.id.cart_continue_btn);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        cartItemsRecyclerView.setLayoutManager(layoutManager);
        List<CartItemModel>cartItemModelList = new ArrayList<>();
        cartItemModelList.add(new CartItemModel(0,R.drawable.telephone,"Pixel 2",2,"Rs.4999","Rs.9999",1,0,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.telephone,"Nokia 2",0,"Rs.4999","Rs.9999",1,1,0));
        cartItemModelList.add(new CartItemModel(0,R.drawable.telephone,"Pixel 2",2,"Rs.4999","Rs.9999",1,2,0));
        cartItemModelList.add(new CartItemModel(1,"Price (3 items)","Rs.166999","Free","Rs.199899","Rs.50999"));
        CartAdapter cartAdapter = new CartAdapter(cartItemModelList);
        cartItemsRecyclerView.setAdapter(cartAdapter);
        cartAdapter.notifyDataSetChanged();

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deliveryIntente = new Intent(getContext(),AddAddressActivity.class);
                getContext().startActivity(deliveryIntente);
            }
        });
        return view;
    }

}
