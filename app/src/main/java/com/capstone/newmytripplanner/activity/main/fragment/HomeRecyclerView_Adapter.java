package com.capstone.newmytripplanner.activity.main.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.model.trip.SharePlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeRecyclerView_Adapter extends RecyclerView.Adapter<HomeRecyclerView_Adapter.ViewHolderItem> {
    ArrayList<SharePlan> sharePlans;
    Context context;

    public HomeRecyclerView_Adapter(ArrayList<SharePlan> sharePlans, Context context) {
        this.sharePlans = sharePlans;
        this.context = context;
    }

    public void updateList(ArrayList<SharePlan> sharePlans){
        Log.d("adapter","update");
        this.sharePlans.clear();
        this.sharePlans = sharePlans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolderItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_recyclerview_item, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderItem holder, int position) {
        holder.BindData(sharePlans.get(position));
    }

    @Override
    public int getItemCount() {
        return sharePlans.size();
    }

    class ViewHolderItem extends RecyclerView.ViewHolder {
        ViewPager2 viewPager2;
        HomeViewPager_Adapter viewPager_adapter;
        TextView title;
        TextView contents;
        SharePlan sharePlans;
        TextView keyword;
        TextView address;
        CircleImageView profile_image;
        TextView username_text;
        TextView useremail_text;
        Bitmap bitmap;
        private FirebaseAuth mAuth;
        private FirebaseUser user;

        public ViewHolderItem(@NonNull View itemView) {
            super(itemView);
            viewPager2 = itemView.findViewById(R.id.home_viewpager);
            title = itemView.findViewById(R.id.home_recyclerview_title);
            contents = itemView.findViewById(R.id.recyclerview_contents);
            keyword = itemView.findViewById(R.id.home_recyclerview_keyword);
            address = itemView.findViewById(R.id.home_recyclerview_addr);
            profile_image = itemView.findViewById(R.id.porfile_image);
            username_text = itemView.findViewById(R.id.username_text);
            useremail_text = itemView.findViewById(R.id.useremail_text);
            mAuth = FirebaseAuth.getInstance();
            user = mAuth.getCurrentUser();
        }
        public void BindData(SharePlan sharePlans) {
            this.sharePlans = sharePlans;
            viewPager_adapter = new HomeViewPager_Adapter(sharePlans.getImages(),context);
            title.setText(sharePlans.getTitle());
            contents.setText(sharePlans.getContents());
            keyword.setText(sharePlans.getLocation_keyword());
            address.setText(sharePlans.getLocation_addr());
            viewPager2.setAdapter(viewPager_adapter);
            /*Thread mThread= new Thread(){
                @Override
                public void run() {
                    try{
                        //현재로그인한 사용자 정보를 통해 PhotoUrl 가져오기
                        URL url = new URL(user.getPhotoUrl().toString());
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                    } catch (IOException ee) {
                        ee.printStackTrace();
                    }
                }
            };
            mThread.start();
            try{
                mThread.join();
                //변환한 bitmap적용
                profile_image.setImageBitmap(bitmap);
            }catch (InterruptedException e){
                e.printStackTrace();
            }*/
            //profile_image.setImageURI(sharePlans.getUserProfileImage());
            username_text.setText(sharePlans.getUserName());
            useremail_text.setText(sharePlans.getUserEmail());
        }
    }
}
