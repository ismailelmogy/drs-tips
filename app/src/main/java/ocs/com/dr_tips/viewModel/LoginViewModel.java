package ocs.com.dr_tips.viewModel;

import android.content.Context;
import android.support.annotation.StringRes;

import com.facebook.AccessToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import ocs.com.dr_tips.model.Country;
import ocs.com.dr_tips.model.User;
import rx.Completable;
import rx.Observable;

/**
 * Created by Randa on 3/18/2018.
 */

public interface LoginViewModel {
    Completable loginWithFacebook(AccessToken token);

    Observable<User> getUserData(String uid);

    Completable setUserData(User registeredUser);


    @StringRes int checkEmail(String email);

    @StringRes int checkPassword(String password);

    @StringRes int isPasswordConfirmed(String password, String passwordConfirmation);

    @StringRes int checkName(String name);

    @StringRes int checkMobileNumber(String mobileNumber);

    Completable registerWithEmailAndPassword(String email,String password);

    List<Country> getCountriesDataFromJson(Context context) throws IOException;

}
