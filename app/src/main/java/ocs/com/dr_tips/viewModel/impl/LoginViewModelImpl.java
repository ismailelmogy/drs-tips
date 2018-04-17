package ocs.com.dr_tips.viewModel.impl;

import android.content.Context;
import android.util.Patterns;

import com.facebook.AccessToken;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import ocs.com.dr_tips.R;
import ocs.com.dr_tips.dataLayer.LoginAPI;
import ocs.com.dr_tips.model.Country;
import ocs.com.dr_tips.model.User;
import ocs.com.dr_tips.viewModel.LoginViewModel;
import rx.Completable;
import rx.Observable;
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
            return getToken(currentUser).flatMap
                    (token -> getUserData(currentUser.getUid(),token));
        }
        return Observable.error(new Throwable("FirebaseUser = null"));
    }

    @Override
    public Completable setUserData(User registeredUser) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return getToken(currentUser).flatMapCompletable(token -> setUserData(currentUser.getUid(),registeredUser,token))
                    .toCompletable();
        }
        return Completable.error(new Throwable("FirebaseUser = null"));
    }


    @Override
    public int checkEmail(String email) {
        email = email.replace(" ","").replace("\n","");
        if (email.isEmpty()){
            return R.string.empty_email;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return R.string.wrong_email;
        }
        return R.string.ok;
    }

    @Override
    public int checkPassword(String password) {
        if (password.length() < 8) {
            return R.string.password_short;
        }
        return R.string.ok;
    }

    @Override
    public int isPasswordConfirmed(String password, String passwordConfirmation) {
        if (passwordConfirmation.length() < 8) {
            return R.string.password_short;
        }
        else if(!password.equals(passwordConfirmation)) {
            return R.string.password_mismatch;
        }
        return R.string.ok;
    }

    @Override
    public int checkName(String name) {
        if (name.isEmpty()) {
            return R.string.empty_name;
        }
        return R.string.ok;
    }

    @Override
    public int checkMobileNumber(String mobileNumber) {
        Pattern mobilePattern = Pattern.compile("\\+[0-9]+");
        if (mobileNumber.isEmpty()){
            return R.string.ok;
        } else if(!mobilePattern.matcher(mobileNumber).matches()){
            return R.string.wrong_mobile;
        }
        return R.string.ok;
    }

    @Override
    public Completable registerWithEmailAndPassword(String email, String password) {
        return Completable.create(completableSubscriber -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    completableSubscriber.onCompleted();
                } else {
                    completableSubscriber.onError(task.getException());
                }
            });
        });
    }



    @Override
    public List<Country> getCountriesDataFromJson(Context context) throws IOException {
        InputStream inputStream = context.getAssets().open("countries.json");
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String json = new String(buffer,"UTF-8");
        Type TYPE = new TypeToken<List<Country>>(){}.getType();
        Gson gson = new Gson();

        return gson.fromJson(json,TYPE);
    }

    private Observable<User> getUserData(String uid,String tokenId){
        return api.getUserData(uid,tokenId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private Completable setUserData(String uid, User user, String tokenId){
        return api.setUserData(uid,user,tokenId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}
