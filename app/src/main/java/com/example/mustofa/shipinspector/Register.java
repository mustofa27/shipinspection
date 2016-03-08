package com.example.mustofa.shipinspector;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends AppCompatActivity {

    UserLocalStore userLocalStore;
    EditText username,email,password,password_confirm;
    TextView backtologin;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email_register);
        password = (EditText) findViewById(R.id.password_register);
        password_confirm = (EditText) findViewById(R.id.password_confirm);
        register = (Button) findViewById(R.id.register);
        backtologin = (TextView) findViewById(R.id.backtologin);
        userLocalStore = new UserLocalStore(getApplicationContext());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password_confirm.getText().toString().equals(password.getText().toString())) {
                    User user = new User(0, username.getText().toString(), email.getText().toString(), password.getText().toString(), "inspector");
                    ServerRequest serverRequest = new ServerRequest(Register.this);
                    serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
                        @Override
                        public void Done(User returnedUser) {
                            startActivity(new Intent(Register.this, Login.class));
                        }
                    });
                }
                
            }
        });
        backtologin.setClickable(true);
        backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this,Login.class));
            }
        });
    }
}
