package ocs.com.dr_tips.viewModel.impl;

import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

import rx.Observable;

class BaseViewModelImpl {
    Observable<String> getToken(@NotNull FirebaseUser currentUser){
        return Observable.unsafeCreate(subscriber -> {
            currentUser.getIdToken(true).addOnCompleteListener(task -> {
                if (task.isSuccessful()){
                    subscriber.onNext(task.getResult().getToken());
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(task.getException());
                }
            });
        });
    }
}
