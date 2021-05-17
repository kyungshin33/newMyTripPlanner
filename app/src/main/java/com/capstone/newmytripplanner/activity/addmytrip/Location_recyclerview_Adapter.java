package com.capstone.newmytripplanner.activity.addmytrip;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.model.location.Documents;

import java.util.ArrayList;
import java.util.List;

public class Location_recyclerview_Adapter extends RecyclerView.Adapter<Location_recyclerview_Adapter.ViewHolderItem> {
    List<Documents> locationList;
    public Location_recyclerview_Adapter() {
       locationList = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mytrip_recyclerview_item, parent, false);
        return new Location_recyclerview_Adapter.ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        holder.BindData(locationList.get(position));
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public void ListAdd(Documents documents) {
        locationList.add(documents);
        notifyDataSetChanged();
    }
    public void setList(List<Documents> list) {
        locationList = list;
        notifyDataSetChanged();
    }
    public class ViewHolderItem extends RecyclerView.ViewHolder {
        TextView mytrip_keyword;
        TextView mytrip_address;
        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            mytrip_keyword = itemView.findViewById(R.id.mytrip_location_keyword);
            mytrip_address = itemView.findViewById(R.id.mytrip_location_addr);
        }

        public void BindData(Documents documents) {
            Log.d("어뎁터","아이템 추가완료");
            mytrip_keyword.setText(documents.getPlace_name());
            mytrip_address.setText(documents.getAddress_name());
        }
    }
}
