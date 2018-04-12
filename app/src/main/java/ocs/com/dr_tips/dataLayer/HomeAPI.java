package ocs.com.dr_tips.dataLayer;

import rx.Observable;

import ocs.com.dr_tips.model.User;

import java.util.HashMap;

import ocs.com.dr_tips.model.Tip;
import ocs.com.dr_tips.model.TipsPackage;
import rx.Observable;

/**
 * Created by Randa on 3/18/2018.
 */

public interface HomeAPI {
    Observable<User> getUserData(String userId);
    Observable<HashMap<String,Tip>> getTips();
    Observable<HashMap<String,TipsPackage>> getPackages();

}
