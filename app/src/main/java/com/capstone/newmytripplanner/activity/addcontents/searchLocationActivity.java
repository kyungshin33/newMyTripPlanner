package com.capstone.newmytripplanner.activity.addcontents;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstone.newmytripplanner.R;
import com.capstone.newmytripplanner.database.FireBaseDB;
import com.capstone.newmytripplanner.model.location.LocationPOJO;
import com.capstone.newmytripplanner.model.location.Documents;
import com.capstone.newmytripplanner.util.RecyclerViewDecoration;
import com.capstone.newmytripplanner.util.RetrofitService;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.uber.rxdogtag.RxDogTag;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class searchLocationActivity extends AppCompatActivity implements OnClickLocation{
    private ArrayList<Documents> documents = new ArrayList<>();
    private Location_RecyclerView_Adapter location_recyclerView_adapter;
    private RecyclerView locationRecycler;
    private EditText searchLocation;
    private ImageButton searchButton;
    private Button cancelButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_location);
        initView();
    }

    private void initView(){
        searchLocation = findViewById(R.id.searchEditText);
        location_recyclerView_adapter = new Location_RecyclerView_Adapter(documents, this, this);
        locationRecycler = findViewById(R.id.recyclerview_location);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        locationRecycler.setLayoutManager(linearLayoutManager);
        locationRecycler.setHasFixedSize(true);
        locationRecycler.addItemDecoration(new RecyclerViewDecoration(10));
        locationRecycler.setAdapter(location_recyclerView_adapter);
        searchButton = findViewById(R.id.button_location_search);
        searchButton.setOnClickListener(view -> {
            try {
                String queryString = URLEncoder.encode(searchLocation.getText().toString(), "UTF-8");
                Log.d("queryString","queryString -> " + queryString);
                Retrofit client = new Retrofit.Builder()
                        .baseUrl("https://dapi.kakao.com/v2/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();

                RetrofitService service = client.create(RetrofitService.class);


                Observable<LocationPOJO> locations = service.searchLocation(searchLocation.getText().toString(),2);
                RxDogTag.install();

                locations.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(data ->{
                            if(data != null){
                                location_recyclerView_adapter.updateList(data.getDocuments());
                            }
                        });

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
        cancelButton = findViewById(R.id.button_location_close);
        cancelButton.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void onClick(Documents documents) {
        /*if(!FireBaseDB.getInstance().clickMy_trip){
            // MyTrip 프래그먼트에서 호출했을때
            FireBaseDB.getInstance().addDocumentsList(documents);
        }else{
            // addContentsActivity 에서 호출했을때
            FireBaseDB.getInstance().setSelectDocument(documents);

        }
        finish();*/
        FireBaseDB.getInstance().setSelectDocument(documents);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FireBaseDB.getInstance().setSelectDocument(null);
    }
}
