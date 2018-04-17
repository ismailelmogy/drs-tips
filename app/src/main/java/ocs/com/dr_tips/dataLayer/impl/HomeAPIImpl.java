package ocs.com.dr_tips.dataLayer.impl;

import java.util.HashMap;

import ocs.com.dr_tips.dataLayer.HomeAPI;
import ocs.com.dr_tips.model.User;
import ocs.com.dr_tips.model.Tip;
import ocs.com.dr_tips.model.TipsPackage;
import ocs.com.dr_tips.networkService.HomeService;
import rx.Observable;

/**
 * Created by Randa on 3/18/2018.
 */

class HomeAPIImpl implements HomeAPI {
    private HomeService service;

    HomeAPIImpl(HomeService service) {
        this.service = service;
    }
    @Override
    public Observable<User>getUserData(String userId){
        return service.getUserData(userId);
    }

    @Override
    public Observable<HashMap<String,Tip>> getTips() {
        return service.getTips();
    }

    @Override
    public Observable<HashMap<String, TipsPackage>> getPackages() {
        return service.getPackages();
    }
}
