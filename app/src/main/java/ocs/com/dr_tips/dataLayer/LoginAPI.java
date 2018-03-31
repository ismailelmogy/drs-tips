package ocs.com.dr_tips.dataLayer;

import ocs.com.dr_tips.model.User;
import rx.Observable;

public interface LoginAPI {
    Observable<User> getUserData(String uid,String tokenId);
}
