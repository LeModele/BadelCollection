package com.modele.badelcollection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import static com.modele.badelcollection.DBqueries.lists;
import static com.modele.badelcollection.DBqueries.loadFragmentData;
import static com.modele.badelcollection.DBqueries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();
    private HomePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title= getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //// home fake list
        List<SliderModel>sliderModelFakeList =new ArrayList<>();
        sliderModelFakeList.add(new SliderModel("null"));
        sliderModelFakeList.add(new SliderModel("null"));
        sliderModelFakeList.add(new SliderModel("null"));
        sliderModelFakeList.add(new SliderModel("null"));
        sliderModelFakeList.add(new SliderModel("null"));
        sliderModelFakeList.add(new SliderModel("null"));


        List<HorizontalProductScrollModel>horizontalProductScrollModeFakelList = new ArrayList<>();
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));
        horizontalProductScrollModeFakelList.add(new HorizontalProductScrollModel("","","","",""));

        homePageModelFakeList.add(new HomePageModel(0,sliderModelFakeList));
        homePageModelFakeList.add(new HomePageModel(1,"","#ffffff"));
        homePageModelFakeList.add(new HomePageModel(2,"",horizontalProductScrollModeFakelList,new ArrayList<WishlistModel>()));
        homePageModelFakeList.add(new HomePageModel(3,"",horizontalProductScrollModeFakelList));

        /// home fake list

        categoryRecyclerView = findViewById(R.id.category_recyclerview);
        LinearLayoutManager testingLayoutManager =new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);

        adapter = new HomePageAdapter(homePageModelFakeList);
        //categoryRecyclerView.setAdapter(adapter);

        int listPosition = 0;
        for(int x = 0; x<loadedCategoriesNames.size(); x++){

            if (loadedCategoriesNames.get(x).equals(title.toUpperCase())){
                listPosition = x;
            }
        }

        if(listPosition == 0){

            loadedCategoriesNames.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            //adapter = new HomePageAdapter(lists.get(loadedCategoriesNames.size() - 1));
            loadFragmentData(categoryRecyclerView,this,loadedCategoriesNames.size() - 1,title);

        }else {
            adapter = new HomePageAdapter(lists.get(listPosition));

        }

        categoryRecyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        ///////////////////////////////////
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_search_icon) {
            // todo: search
            return true;
         } else if(id== android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
