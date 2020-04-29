package com.example.login_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.tv.TvView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mukesh.countrypicker.CountryPicker;
import com.mukesh.countrypicker.CountryPickerListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class create_profile extends AppCompatActivity {
    private TextView country;
    private CountryPicker picker;
    private RadioGroup radioSexGroup;
    private EditText name,age;
    private Button submit;
    private String strName;
    private String strCountry;
    int intAge;
    private String strGender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Intent oldIntent = getIntent();
        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        country = findViewById(R.id.country);
        submit = findViewById(R.id.submit);
        radioSexGroup = findViewById(R.id.radioSex);
        if(oldIntent.hasExtra("name")) {
            strName = oldIntent.getStringExtra("name");
            strCountry = oldIntent.getStringExtra("country");
            strGender = oldIntent.getStringExtra("gender");
            intAge = Integer.parseInt(oldIntent.getStringExtra("age"));
            name.setText(strName);

            age.setText(String.valueOf(intAge));
            country.setText(strCountry);
            if (strGender.equals("Female")){
                ((RadioButton)radioSexGroup.getChildAt(1)).setChecked(true);
            }else{
                ((RadioButton)radioSexGroup.getChildAt(0)).setChecked(true);
            }
        }
        picker = CountryPicker.newInstance("Select Country");

        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String s, String s1, String s2, int i) {
                System.out.println(s + "\n"+s1+"\n"+s2+"\n"+i);
                country.setText(s);
                picker.dismiss();
            }
        });
        country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getSupportFragmentManager(),"COUNTRY_PICKER");
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        name.getText().toString().equals("")
                        || country.getText().toString().equals("Select Country")
                        || age.getText().toString().equals("")){
                    Toast.makeText(create_profile.this, "Please fill in all the required details", Toast.LENGTH_SHORT).show();
                    return;
                }
                strName = name.getText().toString();
                strCountry = country.getText().toString();
                int selectedRadioButtonId = radioSexGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                strGender = selectedRadioButton.getText().toString();
                intAge = Integer.parseInt(age.getText().toString());
                UserModel model = new UserModel(strName, strCountry, intAge, strGender);
                // store data on database;
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("userInfo");
                myRef.child(user.getUid()).setValue(model);
                Intent i = new Intent(create_profile.this,Home.class);
                i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }

        });
    }



}
