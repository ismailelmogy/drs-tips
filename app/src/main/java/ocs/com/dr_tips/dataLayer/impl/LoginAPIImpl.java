package ocs.com.dr_tips.dataLayer.impl;

import ocs.com.dr_tips.dataLayer.LoginAPI;
import ocs.com.dr_tips.model.User;
import ocs.com.dr_tips.networkService.LoginService;
import rx.Observable;

class LoginAPIImpl implements LoginAPI {
    private LoginService service;

    public LoginAPIImpl(LoginService service) {

        this.service = service;
    }

    @Override
    public Observable<User> getUserData(String uid, String tokenId) {
        return service.getUserData(uid, tokenId);
    }
}
