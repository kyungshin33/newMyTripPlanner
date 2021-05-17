package com.capstone.newmytripplanner.activity.main.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capstone.newmytripplanner.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserFragment extends Fragment {
    private View view;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Bitmap bitmap;
    private TextView nameTxtView;
    private TextView emailTxtView;
    private CircleImageView userImageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initView();
        return view;
    }

    private void initView(){
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            user = mAuth.getCurrentUser();
        }
        nameTxtView = view.findViewById(R.id.name_textview);
        emailTxtView = view.findViewById(R.id.email_textview);
        userImageView = view.findViewById(R.id.user_imageview);
        Thread mThread= new Thread(){
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
            userImageView.setImageBitmap(bitmap);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        nameTxtView.setText(user.getDisplayName());
        emailTxtView.setText(user.getEmail());
    }
}
