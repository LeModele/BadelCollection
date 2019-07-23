package com.modele.badelcollection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static com.modele.badelcollection.DeliveryActivity.SELECT_ADDRESS;

public class MyAddressesActivity extends AppCompatActivity {

    private RecyclerView myAddressesRecyclerView;
    private static AddressesAdapter addressesAdapter;
    private Button deliverHereBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_addresses);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("My Addresses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    myAddressesRecyclerView = findViewById(R.id.addresses_recyclerview);
        deliverHereBtn = findViewById(R.id.deliver_here_btn);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myAddressesRecyclerView.setLayoutManager(layoutManager);
        List<AddressesModel> addressesModelList = new ArrayList<>();
        addressesModelList.add(new AddressesModel("Dominique Charles","Rue du quai Fort liberte Haiti","33355",true));
        addressesModelList.add(new AddressesModel("jach jose","Rue du Anne Fort Haiti","33445",false));
        addressesModelList.add(new AddressesModel("donnn dooo","Rue du paix  liberte Haiti","336665",false));
        addressesModelList.add(new AddressesModel("Dominique Charles","Rue du quai Fort joseph Haiti","33355",true));
        addressesModelList.add(new AddressesModel("dioid ooss","Rue du ange Fort liban Haiti","333666",false));

        int mode = getIntent().getIntExtra("MODE",-1);
        if(mode == SELECT_ADDRESS){
            deliverHereBtn.setVisibility(View.VISIBLE);
        }else {
            deliverHereBtn.setVisibility(View.GONE);
        }
        addressesAdapter = new AddressesAdapter(addressesModelList,mode);
        myAddressesRecyclerView.setAdapter(addressesAdapter);
        ((SimpleItemAnimator)myAddressesRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        addressesAdapter.notifyDataSetChanged();
    }

    public static void refreshItem(int deselect, int select){
        addressesAdapter.notifyItemChanged(deselect);
        addressesAdapter.notifyItemChanged(select);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()== android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
