package com.sargis.prueba1.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.sargis.prueba1.model.Article;
import com.sargis.prueba1.utils.GenericData;

import java.util.ArrayList;
import java.util.List;

public class FirestoreRepository {

    private static FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private static CollectionReference articlesRef = rootRef.collection(GenericData.ARTICLES);
    private final static String TAG="FirestoreRepository";


    public MutableLiveData<List<Article>> getAllArticles(){
       final MutableLiveData<List<Article>> listArticle = new MutableLiveData<>();
      final List<Article> articles=new ArrayList<>();

       //.orderBy("date", Query.Direction.ASCENDING)
        Log.d("REPOSITORY"," repository");
        articlesRef.limit(50).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful())
                    for(QueryDocumentSnapshot d:task.getResult()){
                        Log.d("rpository:::::  ",d.getId());

                                articles.add(new Article( d.get("title").toString(),
                                d.get("description").toString(),
                                d.get("ownerID").toString(),
                                d.get("ownerName").toString(),
                                        d.getId()));
                    }
                listArticle.setValue(articles);
            }
        });

        return listArticle;
    }

    public static void createArticle(Article article){
        articlesRef.add(article).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful() && task.isComplete())
                    Log.d(TAG, "OK");
            }
        });
    }
}
