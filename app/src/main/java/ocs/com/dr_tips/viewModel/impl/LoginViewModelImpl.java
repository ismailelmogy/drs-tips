package ocs.com.dr_tips.viewModel.impl;

import com.facebook.AccessToken;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import ocs.com.dr_tips.dataLayer.LoginAPI;
import ocs.com.dr_tips.model.User;
import ocs.com.dr_tips.viewModel.LoginViewModel;
import rx.Completable;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Randa on 3/18/2018.
 */

class LoginViewModelImpl implements LoginViewModel {
    private LoginAPI api;

    LoginViewModelImpl(LoginAPI api) {
        this.api = api;
    }

    @Override
    public Completable loginWithFacebook(AccessToken token){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        return Completable.create(completableSubscriber -> {
            AuthCredential facebookCredential = FacebookAuthProvider.getCredential(token.getToken());
            auth.signInWithCredential(facebookCredential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    completableSubscriber.onCompleted();
                }
                else {
                    completableSubscriber.onError(task.getException());
                }
            });
        });
    }

    @Override
    public Observable<User> getUserData(String uid) {
        return api.getUserData(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
