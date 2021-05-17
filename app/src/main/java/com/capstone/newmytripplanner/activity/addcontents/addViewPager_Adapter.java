package com.capstone.newmytripplanner.activity.addcontents;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstone.newmytripplanner.R;


import java.util.ArrayList;

public class addViewPager_Adapter extends RecyclerView.Adapter<addViewPager_Adapter.ViewPagerHolder>{
    ArrayList<Uri> imageList;
    Context context;

    public addViewPager_Adapter(ArrayList<Uri> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    public void updateViewPager(ArrayList<Uri> imageList){
        this.imageList.clear();
        this.imageList = imageList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_viewpager_item, parent, false);
        return new addViewPager_Adapter.ViewPagerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerHolder holder, int position) {
        holder.BindData(imageList.get(position));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    class ViewPagerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewPagerHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.add_viewpager_img);
        }
        public void BindData(Uri uri) {
            Log.d("뷰페이저", "uri -> " + uri.toString());
            Glide.with(context)
                    .load(uri)
                    .into(imageView);
        }
    }
}
