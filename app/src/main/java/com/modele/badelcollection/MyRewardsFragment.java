package com.modele.badelcollection;


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
public class MyRewardsFragment extends Fragment {


    public MyRewardsFragment() {
        // Required empty public constructor
    }

    private RecyclerView rewardsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_rewards, container, false);
        rewardsRecyclerView = view.findViewById(R.id.my_rewards_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rewardsRecyclerView.setLayoutManager(layoutManager);

        List<RewardModel> rewardModelList = new ArrayList<>();
        rewardModelList.add(new RewardModel("Cashback","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("Discount","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("buy 1 get 1","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("Cashback","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("Cashback","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("Discount","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));
        rewardModelList.add(new RewardModel("buy 1 get 1","till 2nd,june 2016","GET 20% CASHBACK ON ANY PRODUCT ENTRE 200 A 500"));

        MyRewardAdapter myRewardAdapter = new MyRewardAdapter(rewardModelList,false);
        rewardsRecyclerView.setAdapter(myRewardAdapter);
        myRewardAdapter.notifyDataSetChanged();

        return view;
    }

}
