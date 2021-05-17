package com.capstone.newmytripplanner.database;

import android.util.Log;

import com.capstone.newmytripplanner.model.location.Documents;
import com.capstone.newmytripplanner.model.trip.MytripPlan;
import com.capstone.newmytripplanner.model.trip.SharePlan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

import io.reactivex.Observable;

public class FireBaseDB {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "FIREBASE_DB";
    private Documents selectDocument = null;
    private ArrayList<Documents> DocumentsList = new ArrayList<>();
    private ArrayList<MytripPlan> MytripPlanList = new ArrayList<>();
    public boolean clickMy_trip = false;
    public FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    private FireBaseDB() {

    }
    private static class FireBaseDBHolder {
        public static final FireBaseDB INSTANCE = new FireBaseDB();
    }
    public static FireBaseDB getInstance() {
        return FireBaseDBHolder.INSTANCE;
    }

    public ArrayList<User> SelectUserData() {
        ArrayList<User> UserList = new ArrayList<>();
        db.collection("User")
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot querySnapshot = task.getResult();
                        for(QueryDocumentSnapshot documentSnapshot : querySnapshot){
                            Log.d("DATA","data -> " + documentSnapshot.getData());
                            User user = documentSnapshot.toObject(User.class);
                            UserList.add(user);
                        }
                    }else{
                        Log.w(TAG, "Error document", task.getException());
                    }
                });
        return UserList;
    }

    public Observable<ArrayList<SharePlan>> SelectSharePlan() {
        return Observable.create(item -> {
            ArrayList<SharePlan> result = new ArrayList<>();
            db.collection("Contents")
                    .orderBy("tm", Query.Direction.DESCENDING)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result.add(document.toObject(SharePlan.class));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        item.onNext(result);
                        item.onComplete();
                    });
        });
    }
    public Observable<ArrayList<MytripPlan>> SelectMytrip() {
        return Observable.create(item -> {
            ArrayList<MytripPlan> result = new ArrayList<>();
            db.collection("mytrip")
                    .whereEqualTo("userId", firebaseUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                result.add(document.toObject(MytripPlan.class));
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                        item.onNext(result);
                        item.onComplete();
                    });
        });
    }

    public void InsertDate(String ColPath, Object data) {
        db.collection(ColPath)
                .add(data)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    public Documents getSelectDocument() {
        return selectDocument;
    }

    public void setSelectDocument(Documents selectDocument) {
        this.selectDocument = selectDocument;
    }

    public ArrayList<Documents> getDocumentsList() {
        return DocumentsList;
    }

    public void addDocumentsList(Documents documents) {
        DocumentsList.add(documents);
    }

    public void clearDocumentsList(){
        DocumentsList.clear();
    }
}
