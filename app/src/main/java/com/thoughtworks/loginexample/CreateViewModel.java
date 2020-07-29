package com.thoughtworks.loginexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.thoughtworks.loginexample.Utils.convertMD5;

public class CreateViewModel extends ViewModel {
    private MutableLiveData<String> createResult;
    private UserRepository userRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CreateViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<String> getCreateResult() {
        if (Objects.isNull(createResult)) {
            createResult = new MutableLiveData<>();
        }
        return createResult;
    }

    public void autoCreateUser() {
        final String name = "android";
        final String password = "123456";
        User user = new User(name, convertMD5(password));

        userRepository.save(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    String successResult = "Success";
                    String failResult = "Failed";

                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        createResult.setValue(successResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        createResult.setValue(failResult);
                    }
                });
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
    }
}
