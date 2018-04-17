package ocs.com.dr_tips.dataLayer;

import java.util.HashMap;

import rx.Completable;

public interface ProfileEditAPI {
    Completable editProfile(HashMap<String,String> body, String uId, String tokenId);
}
