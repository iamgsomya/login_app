package com.example.login_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GetTokenResult;

public class sign_up extends AppCompatActivity {
    public Button CreateAccountButton;
    private EditText InputName, Input_Email, Input_Password;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        CreateAccountButton = findViewById(R.id.reg_btn);
        Input_Email = findViewById(R.id.reg_Email_input);
        Input_Password = findViewById(R.id.reg_password_input);
        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(sign_up.this,create_profile.class);
                //startActivity(intent);
                String InputEmail = Input_Email.getText().toString();
                String InputPassword = Input_Password.getText().toString();
                if (InputEmail.isEmpty()) {
                    Input_Email.setError("Provide your Email first!");
                    Input_Email.requestFocus();
                } else if (InputPassword.isEmpty()) {
                    Input_Password.setError("Set your password");
                    Input_Password.requestFocus();
                } else if (InputEmail.isEmpty() && InputPassword.isEmpty()) {
                    Toast.makeText(sign_up.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
                } else if(!isValidEmail(InputEmail)){
                    Toast.makeText(sign_up.this, "Please Enter a valid email address!", Toast.LENGTH_SHORT).show();
                } else  if (!(InputEmail.isEmpty() && InputPassword.isEmpty())) {
                    mAuth.createUserWithEmailAndPassword(InputEmail, InputPassword).addOnCompleteListener(sign_up.this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(sign_up.this.getApplicationContext(), "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {

                                startActivity(new Intent(sign_up.this, create_profile.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
//                                AuthResult authResult = (AuthResult) task.getResult();
//                                System.out.println("authResult"+authResult.getUser().getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
//                                    @Override
//                                    public void onSuccess(GetTokenResult result) {
//                                        String idToken = result.getToken();
//                                        //Do whatever
//                                        Log.d("idToken", "GetTokenResult result = " + idToken);
//                                    }
//                                }));
                            }
                        }

                    });
                } else {
                    Toast.makeText(sign_up.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
                            
