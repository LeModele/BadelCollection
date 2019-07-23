package com.modele.badelcollection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;


import java.util.ArrayList;
import java.util.List;


public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView viewAllRecyclerView;
    private GridView viewAllGridView;
    public static List<WishlistModel>wishlistModelList;
    public static List<HorizontalProductScrollModel> horizontalProductScrollModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        Toolbar toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewAllRecyclerView = findViewById(R.id.recyclerview_view_all);
        viewAllGridView = findViewById(R.id.grid_view);

        int layout_code = getIntent().getIntExtra("layout_code",-1);

        if(layout_code == 0) {

            viewAllRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            viewAllRecyclerView.setLayoutManager(layoutManager);


            WishlistAdapter adapter = new WishlistAdapter(wishlistModelList,false);
            viewAllRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }else if(layout_code == 1) {

            viewAllGridView.setVisibility(View.VISIBLE);

           // List<HorizontalProductScrollModel> horizontalProductScrollModelList = new ArrayList<>();
//
            GridProductLayoutAdapter gridProductLayoutAdapter = new GridProductLayoutAdapter(horizontalProductScrollModelList);

            viewAllGridView.setAdapter(gridProductLayoutAdapter);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
