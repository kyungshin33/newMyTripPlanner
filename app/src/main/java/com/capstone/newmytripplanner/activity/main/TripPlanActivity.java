package com.capstone.newmytripplanner.activity.main;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.activity.addmytrip.Location_recyclerview_Adapter;
import com.capstone.newmytripplanner.model.trip.MytripPlan;
import com.capstone.newmytripplanner.util.RecyclerViewDecoration;

import java.io.Serializable;

public class TripPlanActivity extends AppCompatActivity {
    MytripPlan mytripPlan;
    RecyclerView recyclerView;
    Location_recyclerview_Adapter location_recyclerview_adapter;
    ImageButton cancelBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.fragment_add_tripplan);
        super.onCreate(savedInstanceState);
        cancelBtn = findViewById(R.id.buttonPre_Trip);
        cancelBtn.setOnClickListener(v -> {
            finish();
        });
        Serializable serializable = getIntent().getSerializableExtra("Plan");
        mytripPlan = (MytripPlan) serializable;
        recyclerView = findViewById(R.id.add_trip_recyclerview);
        location_recyclerview_adapter = new Location_recyclerview_Adapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerViewDecoration(10));
        recyclerView.setAdapter(location_recyclerview_adapter);
        location_recyclerview_adapter.setList(mytripPlan.getLocation());
    }
}
