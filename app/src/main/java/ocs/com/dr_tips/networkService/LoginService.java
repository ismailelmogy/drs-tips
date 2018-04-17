package ocs.com.dr_tips.networkService;

import ocs.com.dr_tips.model.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Completable;
import rx.Observable;

public interface LoginService {

    @GET("user/{uid}.json")
    Observable<User> getUserData(@Path("uid") String uid, @Query("auth") String tokenId);

    @PUT("user/{uid}.json")
    Completable setUserData(@Path("uid") String uid, @Body User user,@Query("auth") String tokenId);
}
