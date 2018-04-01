package ocs.com.dr_tips.viewModel.impl;

import ocs.com.dr_tips.dataLayer.HomeAPI;
import ocs.com.dr_tips.model.User;
import ocs.com.dr_tips.viewModel.HomeViewModel;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Randa on 3/18/2018.
 */

class HomeViewModelImpl implements HomeViewModel {
    private HomeAPI api;

    HomeViewModelImpl(HomeAPI api) {
        this.api = api;
    }
    @Override

  public Observable<User> getUserData(String userId) {

        return api.getUserData(userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

          }

}
