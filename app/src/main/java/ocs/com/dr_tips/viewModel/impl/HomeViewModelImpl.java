package ocs.com.dr_tips.viewModel.impl;

import java.util.HashMap;

import ocs.com.dr_tips.dataLayer.HomeAPI;
import ocs.com.dr_tips.model.User;
import ocs.com.dr_tips.model.Tip;
import ocs.com.dr_tips.model.TipsPackage;
import ocs.com.dr_tips.networkService.HomeService;
import ocs.com.dr_tips.viewModel.HomeViewModel;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.Scheduler;

/**
 * Created by Randa on 3/18/2018.
 */

class HomeViewModelImpl extends BaseViewModelImpl implements HomeViewModel {
    private HomeAPI api;

    HomeViewModelImpl(HomeAPI api) {
        this.api = api;
    }
    @Override

  public Observable<User> getUserData(String userId) {

        return api.getUserData(userId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

          }

    @Override
    public Observable<HashMap<String,Tip>> getTips() {

     return  api.getTips().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<HashMap<String, TipsPackage>> getPackages() {
        return api.getPackages().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<String>getAboutUsContent(){
        return  api.getAboutUsContent().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }
}
