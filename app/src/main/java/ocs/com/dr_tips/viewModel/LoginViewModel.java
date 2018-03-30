package ocs.com.dr_tips.viewModel;

import com.facebook.AccessToken;

import ocs.com.dr_tips.model.User;
import rx.Completable;
import rx.Observable;

/**
 * Created by Randa on 3/18/2018.
 */

public interface LoginViewModel {
    Completable loginWithFacebook(AccessToken token);

    Observable<User> getUserData(String uid);
}
