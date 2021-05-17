package com.capstone.newmytripplanner.activity.addcontents;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.activity.main.MainActivity;

import java.util.ArrayList;

public class addImagesFragment extends Fragment {
    private static final String TAG = "addImagesFragment";
    private View view;
    private ViewPager2 addViewPager;
    private addViewPager_Adapter addViewPager_adapter;
    private ArrayList<Uri> imageList = new ArrayList<>();
    private Context mContext;
    private Button nextButton;
    private EditText textTitle;
    private EditText textContent;
    private ImageButton buttonPre_add;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_images, container, false);
        mContext = getContext();
        initView();
        return view;
    }

    private void initView(){
        textTitle = view.findViewById(R.id.input_title);
        textContent = view.findViewById(R.id.input_content);
        addViewPager = view.findViewById(R.id.add_iamges_viewpager);
        buttonPre_add = view.findViewById(R.id.buttonPre_contents);
        buttonPre_add.setOnClickListener(v ->{
            getActivity().finish();
            Intent preActivity = new Intent(getActivity(),MainActivity.class);
            startActivity(preActivity);
        });
        // 액티비티 데이터 저장
        textTitle.setText(((AddContentsActivity)getActivity()).getTitle_text());
        textContent.setText(((AddContentsActivity)getActivity()).getContent_text());
        if(((AddContentsActivity)getActivity()).getImage_list() != null && ((AddContentsActivity)getActivity()).getImage_list().size() != 0){
            imageList = ((AddContentsActivity)getActivity()).getImage_list();
        }

        addViewPager_adapter = new addViewPager_Adapter(imageList, mContext);
        addViewPager.setAdapter(addViewPager_adapter);

        nextButton = view.findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(v ->{
            // 데이터 보존
            ((AddContentsActivity)getActivity()).setTitle_text(textTitle.getText().toString());
            ((AddContentsActivity)getActivity()).setContent_text(textContent.getText().toString());
            ((AddContentsActivity)getActivity()).setImage_list(imageList);
            // 장소추가 프래그먼트로 이동
            ((AddContentsActivity)getActivity()).changeFragment(1);
        });
    }

    public void setViewPagerItemList(ArrayList<Uri> imageList){
        this.imageList = imageList;
        Log.d(TAG,"이미지 선택 완료 및 뷰페이저 업데이트");
        addViewPager_adapter.updateViewPager(imageList);
    }
}
