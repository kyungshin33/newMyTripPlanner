package com.capstone.newmytripplanner.activity.addmytrip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.activity.addcontents.searchLocationActivity;
import com.capstone.newmytripplanner.activity.main.MainActivity;
import com.capstone.newmytripplanner.database.FireBaseDB;
import com.capstone.newmytripplanner.model.trip.MytripPlan;
import com.capstone.newmytripplanner.util.RecyclerViewDecoration;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.List;

public class selectLocationFragment extends Fragment {
    View v;
    ImageButton preBtn;
    Button doneBtn;
    Button locationBtn;
    RecyclerView selectLocation_recyclerview;
    Location_recyclerview_Adapter location_recyclerview_adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_select_location, container, false);
        preBtn = v.findViewById(R.id.buttonPre_select_location);
        doneBtn = v.findViewById(R.id.buttonDone_select_location);
        locationBtn = v.findViewById(R.id.button_select_location);
        selectLocation_recyclerview = v.findViewById(R.id.select_location_recyclerveiw);
        location_recyclerview_adapter = new Location_recyclerview_Adapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        selectLocation_recyclerview.setLayoutManager(linearLayoutManager);
        selectLocation_recyclerview.setHasFixedSize(true);
        selectLocation_recyclerview.addItemDecoration(new RecyclerViewDecoration(10));
        selectLocation_recyclerview.setAdapter(location_recyclerview_adapter);
        preBtn.setOnClickListener(v -> {
            ((addMyTripActivity)getActivity()).changeFragment(0);
        });
        locationBtn.setOnClickListener(v -> {
            FireBaseDB.getInstance().clickMy_trip = true;
            Intent intent = new Intent(getActivity(), searchLocationActivity.class);
            startActivity(intent);
        });
        doneBtn.setOnClickListener(v -> {
            MytripUpload();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(FireBaseDB.getInstance().getSelectDocument() != null){
            Log.d("장소", "결과값있음");
            // 선택한 장소 저장
            location_recyclerview_adapter.ListAdd(FireBaseDB.getInstance().getSelectDocument());
        }
    }
    public void MytripUpload() {
        MytripPlan mytripPlan = new MytripPlan(
                returnDateString(((addMyTripActivity)getActivity()).dates),
                FireBaseDB.getInstance().firebaseUser.getUid(),
                location_recyclerview_adapter.locationList
        );
        FireBaseDB.getInstance().InsertDate("mytrip",mytripPlan);
    }

    public String returnDateString(List<CalendarDay> dates){
        StringBuilder dateStr = new StringBuilder();
        dateStr.append(dates.get(0).getYear() + "-");
        dateStr.append(dates.get(0).getMonth() + "-");
        dateStr.append(dates.get(0).getDay() + " ~ ");
        dateStr.append(dates.get(dates.size()-1).getYear() + "-");
        dateStr.append(dates.get(dates.size()-1).getMonth() + "-");
        dateStr.append(dates.get(dates.size()-1).getDay());
        return dateStr.toString();
    }


}
