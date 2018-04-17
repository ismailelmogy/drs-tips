package ocs.com.dr_tips.viewModel.impl;

import ocs.com.dr_tips.dataLayer.HomeAPI;
import ocs.com.dr_tips.viewModel.HomeViewModel;

/**
 * Created by Randa on 3/18/2018.
 */

class HomeViewModelImpl extends BaseViewModelImpl implements HomeViewModel {
    private HomeAPI api;

    HomeViewModelImpl(HomeAPI api) {
        this.api = api;
    }

}
