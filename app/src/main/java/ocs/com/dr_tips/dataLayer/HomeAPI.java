package ocs.com.dr_tips.dataLayer;

import rx.Observable;

import ocs.com.dr_tips.model.User;

/**
 * Created by Randa on 3/18/2018.
 */

public interface HomeAPI {
      Observable<User> getUserData(String userId);
}
