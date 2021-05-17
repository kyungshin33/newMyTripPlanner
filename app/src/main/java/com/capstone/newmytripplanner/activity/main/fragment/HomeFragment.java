package com.capstone.newmytripplanner.activity.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.activity.LoginActivity;
import com.capstone.newmytripplanner.activity.addcontents.AddContentsActivity;
import com.capstone.newmytripplanner.database.FireBaseDB;
import com.capstone.newmytripplanner.model.trip.SharePlan;
import com.capstone.newmytripplanner.util.RecyclerViewDecoration;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;

public class HomeFragment extends Fragment {
    private View view;
    private FloatingActionButton buttonPlus;        // 플로팅버튼 -> 클릭시 AddContentsActivity 로 이동
    private RecyclerView homeRecyclerView;
    private HomeRecyclerView_Adapter homeRecyclerView_adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<SharePlan> sharePlan_list = new ArrayList<>();
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void initView(){
        homeRecyclerView = view.findViewById(R.id.home_recyclerview);
        homeRecyclerView_adapter = new HomeRecyclerView_Adapter(sharePlan_list, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        homeRecyclerView.setLayoutManager(linearLayoutManager);
        homeRecyclerView.setHasFixedSize(true);
        // 아이템 간격 설정
        homeRecyclerView.addItemDecoration(new RecyclerViewDecoration(30));
        // 리사이클러뷰 어댑터 할당
        homeRecyclerView.setAdapter(homeRecyclerView_adapter);

        /* 리스너 할당 */
        // 새로고침뷰 리스너 처리
        swipeRefreshLayout = view.findViewById(R.id.home_swipe);
        swipeRefreshLayout.setOnRefreshListener(this::loadSharePlans);

        // 플러스 버튼 처리
        buttonPlus = view.findViewById(R.id.buttonPlus);
        buttonPlus.setOnClickListener(v -> {
            // AddContentsActivity 로 이동
            Intent AddContentIntent = new Intent(getActivity(), AddContentsActivity.class);
            startActivity(AddContentIntent);
            // MainActivity 종료
            getActivity().finish();
        });

        // 여행 공유 데이터 불러오기 호출
        loadSharePlans();
    }

    // 유저 여행 공유 데이터 불러오기
    public void loadSharePlans(){
        FireBaseDB.getInstance().SelectSharePlan()
                .subscribeOn(Schedulers.io())
                .subscribe(result -> {
                    homeRecyclerView_adapter.updateList(result);
                    swipeRefreshLayout.setRefreshing(false);
                });
    }
}
