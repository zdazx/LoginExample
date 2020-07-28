package com.thoughtworks.loginexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity {
    private CreateViewModel createViewModel;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppContainer container = ((MyApplication) getApplicationContext()).getAppContainer();

        createViewModel = new CreateViewModel(container.getUserRepository());
        final Observer<String> observer = s -> Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        createViewModel.getCreateResult().observe(this, observer);

        Button autoCreateBtn = findViewById(R.id.auto_create_user_btn);
        autoCreateBtn.setOnClickListener(view -> autoCreateUser());

        loginViewModel = new LoginViewModel(container.getUserRepository());
        loginViewModel.getLoginResult().observe(this, observer);

        TextView nameView = findViewById(R.id.username);
        TextView passwordView = findViewById(R.id.password);
        Button loginBtn = findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(view -> login(nameView.getText().toString(), passwordView.getText().toString()));
    }

    private void login(String name, String password) {
        loginViewModel.login(name, password);
    }

    private void autoCreateUser() {
        createViewModel.autoCreateUser();
    }

}