package com.sargis.prueba1.messaging;

import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sargis.prueba1.repository.AuthRepository;

public class FirebaseMessaging extends FirebaseMessagingService {
    private String TAG="FirebaseMessaging";
    private AuthRepository authRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        authRepository=new AuthRepository();
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        Log.d(TAG,"Remote message "+remoteMessage.getData().toString());
    }

    @Override
    public void onNewToken(String token){
        Log.d(TAG,"New Token FirebaseMessaging "+token);
        authRepository.updateTokenMessaging(token);
    }
}
