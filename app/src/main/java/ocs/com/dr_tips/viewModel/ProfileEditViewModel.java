package ocs.com.dr_tips.viewModel;

import rx.Completable;

public interface ProfileEditViewModel {

    Completable changeEmail(String newEmail);

    Completable changePassword(String oldPassword,String newPassword);
}
