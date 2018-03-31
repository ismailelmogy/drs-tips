package ocs.com.dr_tips.viewModel.impl;

import com.facebook.AccessToken;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ocs.com.dr_tips.dataLayer.LoginAPI;
import ocs.com.dr_tips.model.User;
import ocs.com.dr_tips.viewModel.LoginViewModel;
import rx.Completable;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Randa on 3/18/2018.
 */

class LoginViewModelImpl extends BaseViewModelImpl implements LoginViewModel {
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
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return Observable.unsafeCreate(subscriber ->
                    getToken(currentUser).subscribe(token -> subscribeToGetUserData(subscriber, uid, token),
                            throwable -> subscriber.onError(new Throwable("cannot get token")))
            );
        }
        return Observable.error(new Throwable("FirebaseUser = null"));
    }

    private void subscribeToGetUserData(Subscriber<? super User> subscriber, String uid, String token) {
        getUserData(uid, token).subscribe(subscriber::onNext, subscriber::onError);
    }

    private Observable<User> getUserData(String uid,String tokenId){
        return api.getUserData(uid,tokenId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
