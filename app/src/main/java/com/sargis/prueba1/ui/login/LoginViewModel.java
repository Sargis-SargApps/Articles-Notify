package com.sargis.prueba1.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sargis.prueba1.model.ArticleFav;
import com.sargis.prueba1.model.Profile;
import com.sargis.prueba1.repository.AuthRepository;


public class LoginViewModel extends ViewModel {
    private LiveData<Profile> profile;
    private AuthRepository authRepository;

    public LoginViewModel() {
        authRepository=new AuthRepository();
    }
    public void createProfile(Profile profile){
        authRepository.createNewUserFirestore(profile);
        MutableLiveData<Profile> profileMutableLiveData=new MutableLiveData<>();
        profileMutableLiveData.setValue(profile);
        this.profile=profileMutableLiveData;
    }
    public LiveData<Profile> getProfile(){
        this.profile=authRepository.getProfileFirestore();
        return profile;
    }
    public void addArticleToFav(ArticleFav fav, String idArticle){
        authRepository.addArticleToFavFirestore(fav,idArticle);
    }
}
