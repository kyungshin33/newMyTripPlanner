package com.capstone.newmytripplanner.activity.main.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.activity.main.TripPlanActivity;
import com.capstone.newmytripplanner.database.FireBaseDB;
import com.capstone.newmytripplanner.model.trip.MytripPlan;
import com.capstone.newmytripplanner.model.trip.SharePlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyTripRecyclerView_Adapter extends RecyclerView.Adapter<MyTripRecyclerView_Adapter.ViewHolderItem> {
    ArrayList<MytripPlan> mytripPlans;
    Context context;
    public MyTripRecyclerView_Adapter(Context context) {
        mytripPlans = new ArrayList<>();
        this.context = context;
    }

    public void updateList(ArrayList<MytripPlan> mytripPlans){
        Log.d("adapter","update");
        this.mytripPlans.clear();
        this.mytripPlans = mytripPlans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mytrip_recyclerview_item, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        holder.BindData(mytripPlans.get(position));
    }

    @Override
    public int getItemCount() {
        return mytripPlans.size();
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView date;
        TextView addr;
        MytripPlan mytripPlan;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.mytrip_location_keyword);
            addr = itemView.findViewById(R.id.mytrip_location_addr);

            itemView.setOnClickListener(View -> {
                Log.d("클릭",  mytripPlan.getDate() + " 클릭됨");
                Intent intent = new Intent(context, TripPlanActivity.class);
                intent.putExtra("Plan", mytripPlan);
                context.startActivity(intent);
            });
        }
        public void BindData(MytripPlan mytripPlans) {
            this.mytripPlan = mytripPlans;
            date.setText(mytripPlans.getDate());
            addr.setText(mytripPlans.getLocation().get(0).getPlace_name());
        }
    }
}
