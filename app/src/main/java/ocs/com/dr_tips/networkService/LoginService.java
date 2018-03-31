package ocs.com.dr_tips.networkService;

import ocs.com.dr_tips.model.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface LoginService {

    @GET("users/{uid}.json")
    Observable<User> getUserData(@Path("uid") String uid, @Query("auth") String tokenId);
}
