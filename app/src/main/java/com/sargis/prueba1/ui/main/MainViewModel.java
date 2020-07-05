package com.sargis.prueba1.ui.main;



import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sargis.prueba1.model.Article;
import com.sargis.prueba1.repository.FirestoreRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private FirestoreRepository firestorRep;
    private LiveData<List<Article>> articleList;

    public MainViewModel(){
        firestorRep=new FirestoreRepository();
    }

    public LiveData<List<Article>> getArticleList(){
        return this.articleList;
    }

    public void setArticleList(){
        this.articleList=firestorRep.getAllArticles();
    }




}
