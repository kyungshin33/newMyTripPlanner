package com.capstone.newmytripplanner.activity.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.activity.LoginActivity;
import com.capstone.newmytripplanner.activity.main.fragment.HomeFragment;
import com.capstone.newmytripplanner.activity.main.fragment.MyTripFragment;
import com.capstone.newmytripplanner.activity.main.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private MyTripFragment myTripFragment;
    private UserFragment userFragment;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 메인액티비티 프래그먼트 생성
        createFragments();
        // 메인액티비티 View 생성
        InitView();
    }


    private void InitView(){
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(item ->{
            FragmentTransaction transaction2;
            transaction2 = fragmentManager.beginTransaction();
            switch (item.getItemId()){
                case R.id.homeFragment_id:
                    transaction2.replace(R.id.fragment_container, homeFragment);
                    break;
                case R.id.myTripFragment_id:
                    transaction2.replace(R.id.fragment_container, myTripFragment);
                    break;
                case R.id.userFragment_id:
                    transaction2.replace(R.id.fragment_container, userFragment);
                    break;
            }
            transaction2.addToBackStack(null);
            transaction2.commit();
            return true;
        });
    }

    private void createFragments(){
        fragmentManager = getSupportFragmentManager();
        // 프레그먼트 객체 생성
        homeFragment = new HomeFragment();
        myTripFragment = new MyTripFragment();
        userFragment = new UserFragment();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, homeFragment).commitAllowingStateLoss();
    }
}
