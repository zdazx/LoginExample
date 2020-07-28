package com.thoughtworks.loginexample;

public class AppContainer {
    private UserRepository userRepository;

    public AppContainer() {
        UserDBDataSource userDBDataSource = new UserDBDataSource();
        userRepository = new UserRepository(userDBDataSource);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
