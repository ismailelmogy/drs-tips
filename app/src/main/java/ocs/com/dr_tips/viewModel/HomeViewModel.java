package ocs.com.dr_tips.viewModel;

import ocs.com.dr_tips.model.User;
import rx.Observable;

/**
 * Created by Randa on 3/18/2018.
 */

public interface HomeViewModel {
    Observable<User> getUserData(String userId);
}
