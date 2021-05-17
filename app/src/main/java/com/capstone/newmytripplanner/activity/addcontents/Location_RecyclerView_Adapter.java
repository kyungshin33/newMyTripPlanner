package com.capstone.newmytripplanner.activity.addcontents;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.model.location.Documents;

import java.util.ArrayList;
import java.util.List;

public class Location_RecyclerView_Adapter extends RecyclerView.Adapter<Location_RecyclerView_Adapter.ViewHolderItem>{
    List<Documents> locationData;
    private OnClickLocation onClickLocation;
    Context context;

    public Location_RecyclerView_Adapter(ArrayList<Documents> locationData, Context context, OnClickLocation onClickLocation) {
        this.onClickLocation = onClickLocation;
        this.locationData = locationData;
        this.context = context;
    }

    public void updateList(List<Documents> locationData){
        //Log.d("adapter","update");
        this.locationData.clear();
        this.locationData = locationData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Location_RecyclerView_Adapter.ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_recyclerview_item, parent, false);
        return new Location_RecyclerView_Adapter.ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        holder.BindData(locationData.get(position));
    }

    @Override
    public int getItemCount() {
        return locationData.size();
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView searchLocationText;
        Button button_select;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            searchLocationText = itemView.findViewById(R.id.searchText_loca);
            button_select = itemView.findViewById(R.id.item_button_select);
        }
        public void BindData(Documents locationData) {
            searchLocationText.setText(locationData.getPlace_name());
            button_select.setOnClickListener(v -> {
                onClickLocation.onClick(locationData);
            });
        }
    }
}
