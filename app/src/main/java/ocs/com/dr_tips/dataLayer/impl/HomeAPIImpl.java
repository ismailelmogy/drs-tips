package ocs.com.dr_tips.dataLayer.impl;

import ocs.com.dr_tips.dataLayer.HomeAPI;
import ocs.com.dr_tips.networkService.HomeService;

/**
 * Created by Randa on 3/18/2018.
 */

class HomeAPIImpl implements HomeAPI {
    private HomeService service;

    HomeAPIImpl(HomeService service) {
        this.service = service;
    }

}
