package com.thoughtworks.loginexample;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity {
    private CreateViewModel createViewModel;

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
    }

    private void autoCreateUser() {
        createViewModel.autoCreateUser();
    }

}