package com.capstone.newmytripplanner.activity.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.activity.addmytrip.addMyTripActivity;
import com.capstone.newmytripplanner.database.FireBaseDB;
import com.capstone.newmytripplanner.util.RecyclerViewDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.schedulers.Schedulers;

public class MyTripFragment extends Fragment {
    private View view;
    private FloatingActionButton addTripBtn;
    private MyTripRecyclerView_Adapter myTripRecyclerView_adapter;
    private RecyclerView myTripRecyclerview;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_trip, container, false);
        initView();
        /*if (FireBaseDB.getInstance().getSelectDocument() != null){

        }*/
        loadMyTripPlans();
        addTripBtn = view.findViewById(R.id.mytrip_plan_plus);
        addTripBtn.setOnClickListener(v -> {
            Intent addMyTrip = new Intent(getActivity(), addMyTripActivity.class);
            startActivity(addMyTrip);
            getActivity().finish();
        });
        return view;
    }

    private void initView(){
        swipeRefreshLayout = view.findViewById(R.id.srl_commodities);
        swipeRefreshLayout.setOnRefreshListener(this::loadMyTripPlans);
        myTripRecyclerview = view.findViewById(R.id.recyclerview_mytrip);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        myTripRecyclerview.setLayoutManager(linearLayoutManager);
        myTripRecyclerview.setHasFixedSize(true);
        // 아이템 간격 설정
        myTripRecyclerView_adapter = new MyTripRecyclerView_Adapter(getActivity());
        myTripRecyclerview.setAdapter(myTripRecyclerView_adapter);
    }

    public void loadMyTripPlans(){
        FireBaseDB.getInstance().SelectMytrip()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    myTripRecyclerView_adapter.updateList(result);
                    swipeRefreshLayout.setRefreshing(false);
                });
    }
}
