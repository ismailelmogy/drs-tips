package ocs.com.dr_tips.dataLayer;

import ocs.com.dr_tips.model.User;
import rx.Completable;
import rx.Observable;

public interface LoginAPI {
    Observable<User> getUserData(String uid,String tokenId);

    Completable setUserData(String uid, User user, String tokenId);
}
