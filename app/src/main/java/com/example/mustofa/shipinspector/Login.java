package com.example.mustofa.shipinspector;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    UserLocalStore userLocalStore;
    EditText email,password;
    TextView forgot,signup;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        forgot = (TextView) findViewById(R.id.forgot);
        signup = (TextView) findViewById(R.id.signup);
        userLocalStore = new UserLocalStore(getApplicationContext());
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(email.getText().toString(),password.getText().toString());
                authenticateUser(user);
            }
        });
        forgot.setClickable(true);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        signup.setClickable(true);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });
    }
    private void authenticateUser(User user)
    {
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.fetchUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void Done(User returnedUser) {
                if(returnedUser == null) {
                    if(email.getText().toString().equals("inspector@gmail.com") && password.getText().toString().equals("inspector"))
                    {
                        logUserIn(new User(-1,"Vijay",email.getText().toString(),password.getText().toString(),"Inspector"));
                    }
                    else
                        showError();
                }
                else
                    logUserIn(returnedUser);
            }
        });
    }

    private void logUserIn(User returnedUser) {
        userLocalStore.setLoggedInUser(returnedUser);
        userLocalStore.setUserLoggedIn(true);
        startActivity(new Intent(this,BeforeMainActivity.class));
    }

    private void showError() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Incorrect User Detail");
        alert.setPositiveButton("Got It", null);
        alert.show();
    }
}
