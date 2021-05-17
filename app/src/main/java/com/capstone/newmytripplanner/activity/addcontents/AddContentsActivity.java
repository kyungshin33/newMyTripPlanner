package com.capstone.newmytripplanner.activity.addcontents;

import android.Manifest;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.activity.main.MainActivity;

import java.util.ArrayList;


public class AddContentsActivity extends AppCompatActivity {
    private static final int RESULT_CODE_IMAGES = 101;
    private static final int MAX_IMAGES_CNT = 9;        // 이미지 최대 선택 개수
    private static final String TAG = "AddContentsActivity";

    private addImagesFragment addImagesFragment;
    private addLocationFragment addLocationFragment;
    private FragmentTransaction transaction;
    private FragmentManager fragmentManager;
    private ArrayList<Uri> image_list = new ArrayList<>();
    private String Title_text = "";
    private String Content_text = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contents);
        // 프래그먼트 생성
        createFragments();
        // View 생성
        InitView();
        // 권한체크
        PermissionsCheck();
    }

    private void InitView(){

    }

    private void createFragments(){
        fragmentManager = getSupportFragmentManager();
        // 프레그먼트 객체 생성
        addImagesFragment = new addImagesFragment();
        addLocationFragment = new addLocationFragment();

        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.add_contents_fragment_container, addImagesFragment).commitAllowingStateLoss();
    }

    public void changeFragment(int flag){
        FragmentTransaction transaction2;
        transaction2 = fragmentManager.beginTransaction();
        switch (flag){
            case 0:
                transaction2.replace(R.id.add_contents_fragment_container, addImagesFragment).commitAllowingStateLoss();
                break;
            case 1:
                transaction2.replace(R.id.add_contents_fragment_container, addLocationFragment).commitAllowingStateLoss();
                break;
        }
    }

    private void PermissionsCheck(){
        String temp = "";
        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        } //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }
        if (TextUtils.isEmpty(temp) == false) {
            // 권한 요청
            ActivityCompat.requestPermissions(this, temp.trim().split(" "),1);
        }else {
            // 모두 허용 상태
            Toast.makeText(this, "권한을 모두 허용", Toast.LENGTH_SHORT).show();
            getImages();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //권한을 허용 했을 경우
        if(requestCode == 1){
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) { // 동의
                    Log.d("MainActivity","권한 허용 : " + permissions[i]);
                    getImages();
                }else{
                    // 권한체크가 안됬을 경우
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
    // 앨범 접근
    private void getImages(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); //기기 기본 갤러리 접근
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //구글 갤러리 접근
        // intent.setType("image/*");
        startActivityForResult(intent,RESULT_CODE_IMAGES);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_CODE_IMAGES && resultCode == RESULT_OK){
            // 이미지를 가져오는데 성공한경우
            if(data == null){
                Log.d(TAG,"data is NULL");
            }else{
                if(data.getClipData() == null){
                    Log.d(TAG,"data ClipData NULL");
                }else{
                    ClipData clipData = data.getClipData();
                    Log.d(TAG,"ClipData Cnt : " + clipData.getItemCount());

                    if(clipData.getItemCount() > MAX_IMAGES_CNT){
                        Log.d(TAG," 사진 9장 이상 초과 ");
                    }else if(clipData.getItemCount() == 1){
                        // 선택한 사진이 하나일경우
                        image_list.clear();
                        image_list.add(clipData.getItemAt(0).getUri());
                    }else if(clipData.getItemCount() > 1 && clipData.getItemCount() < MAX_IMAGES_CNT){
                        image_list.clear();
                        for(int i=0;i<clipData.getItemCount();i++){
                            image_list.add(clipData.getItemAt(i).getUri());
                        }
                    }
                    // 저장한 이미지를 이미지추가 프래그먼트에 전송
                    if(addImagesFragment != null){
                        addImagesFragment.setViewPagerItemList(image_list);
                    }
                }
            }
        }
    }


    // 데이터 보존용

    public ArrayList<Uri> getImage_list() {
        return image_list;
    }

    public void setImage_list(ArrayList<Uri> image_list) {
        this.image_list = image_list;
    }

    public String getTitle_text() {
        return Title_text;
    }

    public void setTitle_text(String title_text) {
        Title_text = title_text;
    }

    public String getContent_text() {
        return Content_text;
    }

    public void setContent_text(String content_text) {
        Content_text = content_text;
    }
}
