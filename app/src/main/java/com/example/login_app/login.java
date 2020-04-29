package com.example.login_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity {
    private EditText Email_id,InputPassword;
    private Button LoginButton;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();

        LoginButton=findViewById(R.id.login_btn);
        Email_id=findViewById(R.id.login_email_input);
        InputPassword=findViewById(R.id.login_password_input);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(login.this, "User logged in ", Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(login.this, Home.class);
                    startActivity(I);
                } else {
                    Toast.makeText(login.this, "Login to continue", Toast.LENGTH_SHORT).show();
                }
            }
        };

        LoginButton.setOnClickListener(new View.OnClickListener() {

                //Intent intent=new Intent(login.this,Home.class);
                //startActivity(intent);
                @Override
                public void onClick(View v) {
                    String userEmail = Email_id.getText().toString();
                    String userPaswd = InputPassword.getText().toString();
                    if (userEmail.isEmpty()) {
                        Email_id.setError("Provide your Email first!");
                        Email_id.requestFocus();
                    } else if (userPaswd.isEmpty()) {
                        InputPassword.setError("Enter Password!");
                        InputPassword.requestFocus();
                    } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                        Toast.makeText(login.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                    } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                        firebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd).addOnCompleteListener(login.this, new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if (!task.isSuccessful()) {
                                    Toast.makeText(login.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                                } else {
                                    Intent i = new Intent(login.this,  Home.class);
                                    i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);
                                }
                            }
                        });
                    } else {
                        Toast.makeText(login.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }


        });
    }
}
