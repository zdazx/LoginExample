package com.thoughtworks.loginexample;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        User user = new User(name, convertMD5());

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

    private String convertMD5() {
        final String MD5 = "MD5";
        final String initPassword = "123456";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(initPassword.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
                while (h.length() < 2)
                    h.insert(0, "0");
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return initPassword;
    }

    @Override
    protected void onCleared() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.clear();
        }
        super.onCleared();
    }
}
