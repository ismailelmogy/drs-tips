package ocs.com.dr_tips.dataLayer.impl;

import java.util.HashMap;

import ocs.com.dr_tips.dataLayer.ProfileEditAPI;
import ocs.com.dr_tips.model.User;
import ocs.com.dr_tips.networkService.ProfileEditService;
import rx.Completable;

class ProfileEditAPIImpl implements ProfileEditAPI {
    private ProfileEditService service;

    ProfileEditAPIImpl(ProfileEditService service)
    {
        this.service = service;
    }

    @Override
    public Completable editProfile(HashMap<String, String> body, String uId, String tokenId) {
        return service.editProfile(body,uId,tokenId);
    }

}
