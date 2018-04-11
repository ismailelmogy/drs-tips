package ocs.com.dr_tips.dataLayer.impl;

import ocs.com.dr_tips.dataLayer.ProfileEditAPI;
import ocs.com.dr_tips.networkService.ProfileEditService;

class ProfileEditAPIImpl implements ProfileEditAPI {
    private ProfileEditService service;

    ProfileEditAPIImpl(ProfileEditService service) {
        this.service = service;
    }
}
