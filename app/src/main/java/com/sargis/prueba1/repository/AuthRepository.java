package com.sargis.prueba1.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.sargis.prueba1.model.ArticleFav;
import com.sargis.prueba1.model.Profile;
import com.sargis.prueba1.utils.GenericData;

public class AuthRepository {
    private FirebaseAuth auth=FirebaseAuth.getInstance();
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private CollectionReference profileRef = rootRef.collection(GenericData.USERS);
    private final String TAG="AuthRepository";

    public void createNewUserFirestore(Profile profile){
        profileRef.document(auth.getUid()).set(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful() && task.isComplete())
                Log.d(TAG,"User created in Firebase Firestore");
            }
        });
    }
    public MutableLiveData<Profile> getProfileFirestore(){
        final MutableLiveData<Profile> profileMutableLiveData=new MutableLiveData<>();
       profileRef.document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Profile profile=document.toObject(Profile.class);
                    checkTokenMessaging(profile.getToken());
                    profileMutableLiveData.setValue(profile); } }
       });
       return profileMutableLiveData;
    }
    public void addArticleToFavFirestore(ArticleFav fav, String idArticle ){
        profileRef.document(auth.getUid()).collection("ArticleFav").document(idArticle).set(fav);
    }
    public void updateTokenMessaging(String token){
        profileRef.document(auth.getUid()).update("token",token).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d(TAG,"Token update");
            }
        });
    }
    public void checkTokenMessaging(final String token){
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener( new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if(token==null || !token.equals(task.getResult().getToken()))
                    updateTokenMessaging(task.getResult().getToken());
                }
            });
    }
}
