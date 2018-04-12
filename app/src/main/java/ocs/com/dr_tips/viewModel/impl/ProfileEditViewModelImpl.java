package ocs.com.dr_tips.viewModel.impl;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import ocs.com.dr_tips.dataLayer.ProfileEditAPI;
import ocs.com.dr_tips.viewModel.ProfileEditViewModel;
import rx.Completable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

class ProfileEditViewModelImpl extends BaseViewModelImpl implements ProfileEditViewModel {
    private ProfileEditAPI api;

    ProfileEditViewModelImpl(ProfileEditAPI api) {
        this.api = api;
    }

    @Override
    public Completable changeEmail(String newEmail) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return Completable.create(completableSubscriber -> {
                user.updateEmail(newEmail).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) completableSubscriber.onCompleted();
                    else completableSubscriber.onError(task.getException());
                });
            });
        } else {
            return Completable.error(new Throwable("FirebaseUser == null"));
        }
    }

    @Override
    public Completable changePassword(String oldPassword, String newPassword) {
        return reauthenticateUser(oldPassword).andThen(changePassword(newPassword));
    }

    @Override
    public Completable editProfile(HashMap<String, String> edit) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            return getToken(currentUser).flatMapCompletable(token -> editProfile(edit,currentUser.getUid(),token))
                    .toCompletable();
        }
        return Completable.error(new Throwable("FirebaseUser = null"));
    }

    private Completable reauthenticateUser(String oldPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            return Completable.create(completableSubscriber -> {
                AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), oldPassword);
                user.reauthenticate(credential).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) completableSubscriber.onCompleted();
                    else completableSubscriber.onError(task.getException());
                });
            });
        } else {
            return Completable.error(new Throwable("FirebaseUser == null"));
        }
    }

    private Completable changePassword(String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return Completable.create(completableSubscriber -> {
                user.updatePassword(newPassword).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) completableSubscriber.onCompleted();
                    else completableSubscriber.onError(task.getException());
                });
            });
        } else {
            return Completable.error(new Throwable("FirebaseUser == null"));
        }
    }

    private Completable editProfile(HashMap<String,String> edit,String userId,String tokenId){
        return api.editProfile(edit, userId, tokenId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
