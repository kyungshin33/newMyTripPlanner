package com.capstone.newmytripplanner.activity.main.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.model.trip.MytripPlan;

import java.util.ArrayList;

public class tripPlan_recyclerview_Adapter extends RecyclerView.Adapter<tripPlan_recyclerview_Adapter.ViewHolderItem>{
    ArrayList<MytripPlan> myPlans;
    Context context;
    public tripPlan_recyclerview_Adapter(Context context) {
        myPlans = new ArrayList<>();
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mytrip_recyclerview_item, parent, false);
        return new tripPlan_recyclerview_Adapter.ViewHolderItem(view);
    }
    public void updateList(ArrayList<MytripPlan> mytripPlans){
        Log.d("adapter","update");
        this.myPlans.clear();
        this.myPlans = mytripPlans;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        holder.BindData(myPlans.get(position));
    }

    @Override
    public int getItemCount() {
        return myPlans.size();
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView date;
        TextView addr;
        MytripPlan mytripPlan;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.mytrip_location_keyword);
            addr = itemView.findViewById(R.id.mytrip_location_addr);
            mytripPlan = new MytripPlan();
        }

        public void BindData(MytripPlan mytripPlan) {

        }
    }
}
