package com.thoughtworks.loginexample;

import android.app.Application;

public class MyApplication extends Application {
    private static MyApplication application;
    private AppContainer appContainer;

    public static MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        appContainer = new AppContainer();
    }

    public AppContainer getAppContainer() {
        return appContainer;
    }
}
