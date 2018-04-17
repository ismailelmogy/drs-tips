package ocs.com.dr_tips.viewModel;

import ocs.com.dr_tips.model.User;
import java.util.HashMap;

import ocs.com.dr_tips.model.Tip;
import ocs.com.dr_tips.model.TipsPackage;
import rx.Observable;
import java.util.HashMap;
import rx.Subscriber;
import ocs.com.dr_tips.model.Tip;
/**
 * Created by Randa on 3/18/2018.
 */

public interface HomeViewModel {

    Observable<HashMap<String,Tip>> getTips();
    Observable<HashMap<String, TipsPackage>> getPackages();


    Observable<User> getUserData(String userId);
}
