package com.thoughtworks.loginexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Objects;

import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.thoughtworks.loginexample.Utils.convertMD5;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<String> loginResult;
    private UserRepository userRepository;
    private CompositeDisposable compositeDisposable;
    private Disposable disposable;

    public LoginViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        compositeDisposable = new CompositeDisposable();
    }

    public LiveData<String> getLoginResult() {
        if (Objects.isNull(loginResult)) {
            loginResult = new MutableLiveData<>();
        }
        return loginResult;
    }

    public void login(String name, String password) {
        userRepository.findByName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<User>() {
                    private User user;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                        compositeDisposable.add(disposable);
                    }

                    @Override
                    public void onSuccess(User user) {
                        this.user = user;
                        if (!user.getPassword().equals(convertMD5(password))) {
                            String passwordNotCorrect = "Password is invalid";
                            loginResult.setValue(passwordNotCorrect);
                            return;
                        }
                        String successResult = "Login successfully";
                        loginResult.setValue(successResult);
                    }

                    @Override
                    public void onError(Throwable e) {
                        String failedResult = "Failed";
                        loginResult.setValue(failedResult);
                    }

                    @Override
                    public void onComplete() {
                        if (Objects.isNull(user)) {
                            String nameNotExist = "Username does not exist";
                            loginResult.setValue(nameNotExist);
                        }
                        if (!disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });
    }

    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
        super.onCleared();
    }
}
