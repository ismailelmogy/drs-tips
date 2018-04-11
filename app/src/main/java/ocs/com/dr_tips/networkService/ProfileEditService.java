package ocs.com.dr_tips.networkService;

import java.util.HashMap;

import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Completable;

public interface ProfileEditService {

    @PATCH("user/{uid}.json")
    Completable editProfile(@Body HashMap<String,String> edit, @Path("uid") String userId, @Query("auth") String tokenId);
}
