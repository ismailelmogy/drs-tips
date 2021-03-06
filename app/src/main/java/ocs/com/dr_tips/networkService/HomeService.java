package ocs.com.dr_tips.networkService;

import java.util.HashMap;

import ocs.com.dr_tips.model.Tip;
import ocs.com.dr_tips.model.User;
import ocs.com.dr_tips.model.TipsPackage;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Randa on 3/18/2018.
 */

public interface HomeService {
    @GET("tips.json")
    Observable<HashMap<String,Tip>> getTips();
    @GET("packages.json")
    Observable<HashMap<String,TipsPackage>> getPackages();
    @GET("/user/{uid}.json")
    Observable<User>getUserData(@Path("uid") String userId);
    @GET("aboutUs.json")
    Observable<String>getAboutUsContent();

}
