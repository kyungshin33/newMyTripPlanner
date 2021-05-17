package com.capstone.newmytripplanner.activity.addmytrip;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.newmytripplanner.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;


public class addMyTripActivity extends AppCompatActivity {
    private DatePickerFragment DateFragment;
    private selectLocationFragment selectLocationFragment;
    //private com.capstone.newmytripplanner.activity.main.fragment.addTripPlanFragment addTripPlanFragment;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    public List<CalendarDay> dates;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mytrip);
        // MyTrip 액티비티 프래그먼트 생성
        createFragments();
    }

    public void changeFragment(int flag){
        FragmentTransaction transaction2;
        transaction2 = fragmentManager.beginTransaction();
        switch (flag){
            case 0:
                transaction2.replace(R.id.add_mytrip_fragment_container, DateFragment).commitAllowingStateLoss();
                break;
            case 1:
                transaction2.replace(R.id.add_mytrip_fragment_container, selectLocationFragment).commitAllowingStateLoss();
                break;
            /*case 2:
                transaction2.replace(R.id.add_mytrip_fragment_container,addTripPlanFragment).commitAllowingStateLoss();
                break;*/
        }
    }

    private void createFragments(){
        fragmentManager = getSupportFragmentManager();
        // 프레그먼트 객체 생성
        DateFragment = new DatePickerFragment();
        selectLocationFragment = new selectLocationFragment();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.add_mytrip_fragment_container, DateFragment).commitAllowingStateLoss();
    }
    public void showDatePicker(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),"datePicker");
    }
    public void processDatePickerResult(int year, int month, int day){
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);

        Toast.makeText(this,"Date: "+dateMessage,Toast.LENGTH_SHORT).show();
    }
}
