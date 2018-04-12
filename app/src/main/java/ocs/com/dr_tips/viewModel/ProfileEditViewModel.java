package ocs.com.dr_tips.viewModel;

import java.util.HashMap;

import rx.Completable;

public interface ProfileEditViewModel {

    Completable changeEmail(String newEmail);

    Completable changePassword(String oldPassword,String newPassword);

    Completable editProfile(HashMap<String,String> edit);
}
