package com.capstone.newmytripplanner.activity.addcontents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.activity.LoginActivity;
import com.capstone.newmytripplanner.activity.main.MainActivity;
import com.capstone.newmytripplanner.database.FireBaseDB;
import com.capstone.newmytripplanner.model.location.Documents;
import com.capstone.newmytripplanner.model.trip.SharePlan;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class addLocationFragment extends Fragment {
    private View view;
    private ImageButton buttonPre;
    private Button buttonDone;
    private Documents selectDocuments;
    private LinearLayout location_item;
    private TextView location_addr;
    private TextView location_keyword;
    private Disposable backgroundtask;
    private boolean result;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://capstonedesign-trip.appspot.com/photo");
    private StorageReference userStorageRef;
    private ArrayList<Uri> priorList = new ArrayList<>();
    private ArrayList<String> fileName = new ArrayList<>();
    private Uri file;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_location, container, false);
        initView();
        return view;
    }

    private void initView(){
        location_item = view.findViewById(R.id.location_item);
        location_keyword = view.findViewById(R.id.location_keyword);
        location_addr = view.findViewById(R.id.location_addr);
        buttonPre = view.findViewById(R.id.buttonPre);
        buttonPre.setOnClickListener(v ->{
            ((AddContentsActivity)getActivity()).changeFragment(0);
        });

        Button btn = view.findViewById(R.id.button_add_location);
        btn.setOnClickListener(v -> {
            FireBaseDB.getInstance().clickMy_trip = false;
            Intent intent = new Intent(getActivity(),searchLocationActivity.class);
            startActivity(intent);
        });
        buttonDone = view.findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(v -> uploadSharedTrip());
    }

    @Override
    public void onResume() {
        super.onResume();
        if(FireBaseDB.getInstance().getSelectDocument() != null){
            // 선택한 장소 저장
            selectDocuments = FireBaseDB.getInstance().getSelectDocument();
            location_keyword.setText(selectDocuments.getPlace_name());
            location_addr.setText(selectDocuments.getRoad_address_name());
            location_item.setVisibility(View.VISIBLE);
        }else{
            location_item.setVisibility(View.INVISIBLE);
        }
    }

    public void uploadSharedTrip(){
        priorList = ((AddContentsActivity)getActivity()).getImage_list();
        backgroundtask = Observable.fromCallable(() ->{
            for (int i=0; i<priorList.size(); i++){
                file = priorList.get(i);
                fileName.add(file.getLastPathSegment());
                userStorageRef = storageRef.child(file.getLastPathSegment());
                userStorageRef.putFile(file)
                        .addOnSuccessListener(taskSnapshot -> {
                            Log.d("이미지 성공여부","성공");
                            result = true;
                        })
                        .addOnFailureListener(exception -> {
                            Log.d("이미지 성공여부","실패");
                            result = false;
                        });
                Log.d("storage_child",userStorageRef.toString());
                Log.d("storage_child",file.toString());
            }
            SharePlan sharePlan = new SharePlan(
                    ((AddContentsActivity)getActivity()).getTitle_text(),      // 타이틀
                    ((AddContentsActivity)getActivity()).getContent_text(),    // 내용
                    fileName,                                                 // 사진 경로
                    Timestamp.now(),                                          // 시간
                    selectDocuments.getRoad_address_name(),                   // 장소 주소
                    selectDocuments.getPlace_name(),                          // 장소키워드
                    FireBaseDB.getInstance().firebaseUser.getDisplayName(),
                    FireBaseDB.getInstance().firebaseUser.getEmail()
                    /*FireBaseDB.getInstance().firebaseUser.getPhotoUrl()*/);
            FireBaseDB.getInstance().InsertDate("Contents",sharePlan);
            return result;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((result)->{
                    backgroundtask.dispose();
                    Intent changeView = new Intent(getActivity(), MainActivity.class);
                    startActivity(changeView);
                    getActivity().finish();
                });
    }
}
