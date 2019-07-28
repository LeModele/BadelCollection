package com.modele.badelcollection;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.modele.badelcollection.DBqueries.categoryModelList;
import static com.modele.badelcollection.DBqueries.firebaseFirestore;
import static com.modele.badelcollection.DBqueries.lists;
import static com.modele.badelcollection.DBqueries.loadCategories;
import static com.modele.badelcollection.DBqueries.loadFragmentData;
import static com.modele.badelcollection.DBqueries.loadedCategoriesNames;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;

    public static SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView categoryRecyclerView;
    private Category_Adapter category_adapter;
    private List<Category_Model>categoryModelFakeList = new ArrayList<>();
    private RecyclerView homePageRecyclerView;
    private List<HomePageModel> homePageModelFakeList = new ArrayList<>();
    private HomePageAdapter adapter;
    private ImageView noInternetConnection;
    private Button retryBtn;

    //private FirebaseFirestore firebaseFirestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        noInternetConnection= view.findViewById(R.id.no_internet_connection);
        categoryRecyclerView=view.findViewById(R.id.category_recyclerview);
        homePageRecyclerView =view.findViewById(R.id.home_page_recyvlerview);
        retryBtn= view.findViewById(R.id.retry_btn);

        swipeRefreshLayout.setColorSchemeColors(
                getContext().getResources().getColor(R.color.colorPrimary),
                getContext().getResources().getColor(R.color.colorAccent),
                getContext().getResources().getColor(R.color.greenSuccessful));

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecyclerView.setLayoutManager(layoutManager);

        LinearLayoutManager testingLayoutManager =new LinearLayoutManager(getContext());
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homePageRecyclerView.setLayoutManager(testingLayoutManager);


        //// categorie fake list
        categoryModelFakeList.add(new Category_Model("null",""));
        categoryModelFakeList.add(new Category_Model("",""));
        categoryModelFakeList.add(new Category_Model("",""));
        categoryModelFakeList.add(new Category_Model("",""));
        categoryModelFakeList.add(new Category_Model("",""));
        categoryModelFakeList.add(new Category_Model("",""));
        categoryModelFakeList.add(new Category_Model("",""));
        categoryModelFakeList.add(new Category_Model("",""));

        //// categorie fake list

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
        category_adapter =new Category_Adapter(categoryModelFakeList);
        //categoryRecyclerView.setAdapter(category_adapter);

        adapter = new HomePageAdapter(homePageModelFakeList);
        //homePageRecyclerView.setAdapter(adapter);

        connectivityManager =(ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo !=null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

             if(categoryModelList.size() == 0)
            {
                loadCategories(categoryRecyclerView,getContext());
            }else {
                category_adapter = new Category_Adapter(categoryModelList);
                category_adapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(category_adapter);

            if(lists.size() == 0)
            {
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecyclerView,getContext(),0,"Home");
            }else {
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);

        }else {
            MainActivity.drawer.setDrawerLockMode(1);
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_internet_connexion).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
        }

        /// refresh layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refresh();//reloadPage();
            }
        });
        /// refresh layout
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeRefreshLayout.setRefreshing(true);
               refresh(); //reloadPage();
            }
        });
        return view;
    }

    private void refresh(){
        networkInfo = connectivityManager.getActiveNetworkInfo();
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        if(networkInfo !=null && networkInfo.isConnected() == true) {
            MainActivity.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.VISIBLE);

            if(categoryModelList.size() == 0)
            {
                loadCategories(categoryRecyclerView,getContext());
            }else {
                category_adapter = new Category_Adapter(categoryModelList);
                category_adapter.notifyDataSetChanged();
            }
            categoryRecyclerView.setAdapter(category_adapter);

            if(lists.size() == 0)
            {
                loadedCategoriesNames.add("HOME");
                lists.add(new ArrayList<HomePageModel>());
                loadFragmentData(homePageRecyclerView,getContext(),0,"Home");
            }else {
                adapter = new HomePageAdapter(lists.get(0));
                adapter.notifyDataSetChanged();
            }
            homePageRecyclerView.setAdapter(adapter);

        }else {
            MainActivity.drawer.setDrawerLockMode(1);
            String noConnexion = getString(R.string.NoConnexion);
            Toast.makeText(getContext(),noConnexion,Toast.LENGTH_SHORT).show();
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(this).load(R.drawable.no_internet_connexion).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }

    }

    private void reloadPage(){
        networkInfo = connectivityManager.getActiveNetworkInfo();
        categoryModelList.clear();
        lists.clear();
        loadedCategoriesNames.clear();
        if(networkInfo !=null && networkInfo.isConnected()== true) {
            MainActivity.drawer.setDrawerLockMode(0);
            noInternetConnection.setVisibility(View.GONE);
            retryBtn.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            homePageRecyclerView.setVisibility(View.GONE);

            category_adapter = new Category_Adapter(categoryModelFakeList);
            adapter = new HomePageAdapter(homePageModelFakeList);
            categoryRecyclerView.setAdapter(category_adapter);
            homePageRecyclerView.setAdapter(adapter);

            loadCategories(categoryRecyclerView,getContext());

            loadedCategoriesNames.add("HOME");
            lists.add(new ArrayList<HomePageModel>());
            adapter = new HomePageAdapter(lists.get(0));
            loadFragmentData(homePageRecyclerView,getContext(),0,"Home");


        }else {
            MainActivity.drawer.setDrawerLockMode(1);
            String noConnexion = getString(R.string.NoConnexion);
            Toast.makeText(getContext(),noConnexion,Toast.LENGTH_SHORT).show();
            categoryRecyclerView.setVisibility(View.GONE);
            homePageRecyclerView.setVisibility(View.GONE);
            Glide.with(getContext()).load(R.drawable.no_internet_connexion).into(noInternetConnection);
            noInternetConnection.setVisibility(View.VISIBLE);
            retryBtn.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
        }

    }

}
