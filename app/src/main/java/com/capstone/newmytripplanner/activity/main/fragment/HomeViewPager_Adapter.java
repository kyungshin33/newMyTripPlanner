package com.capstone.newmytripplanner.activity.main.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstone.newmytripplanner.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class HomeViewPager_Adapter extends RecyclerView.Adapter<HomeViewPager_Adapter.ViewPagerHolder>{
    List<String> images;
    Context context;
    FirebaseStorage storage;
    public HomeViewPager_Adapter(List<String> images, Context context) {
        this.images = images;
        this.context = context;
        storage = FirebaseStorage.getInstance();
    }
    @NonNull
    @Override
    public ViewPagerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_viewpager_item, parent, false);
        return new ViewPagerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPagerHolder holder, int position) {
        holder.BindData(images.get(position));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class ViewPagerHolder extends RecyclerView.ViewHolder {
        String imagePath;
        ImageView imageView;
        public ViewPagerHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.home_viewpagerItem_text);
        }
        public void BindData(String imagePath) {
            this.imagePath = imagePath;
            Log.d("imagepath","data -> " + imagePath);
            StorageReference storageReference = storage.getReference().child("photo/" + imagePath);
            Glide.with(context)
                    .load(storageReference)
                    .into(imageView);
        }
    }
}
