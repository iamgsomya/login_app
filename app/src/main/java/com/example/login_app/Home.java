package com.example.login_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Home extends AppCompatActivity {
    Button btnLogOut, editProfile;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    TextView name, age, gender, country, created;
    String strName, strGender, strCountry;
    String strAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnLogOut = findViewById(R.id.btnLogOut);
        editProfile = findViewById(R.id.edit_profile);
        name = findViewById(R.id.tvName);
        created = findViewById(R.id.tvCreated);
        age = findViewById(R.id.tvAge);
        country = findViewById(R.id.tvCountry);
        gender = findViewById(R.id.tvGender);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(Home.this, MainActivity.class);
                i.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Home.this, create_profile.class);
                if ( strGender!=null ){
                    i.putExtra("name",strName);
                    i.putExtra("age",strAge);
                    i.putExtra("country",strCountry);
                    i.putExtra("gender",strGender);
                }
                startActivity(i);
            }
        });
        try{
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference myRef = firebaseDatabase.getReference();
            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            myRef.child("userInfo").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //fetching and setting data on home screen
                    strName = dataSnapshot.child("name").getValue(String.class);
                    strAge = String.valueOf(dataSnapshot.child("age").getValue(Long.class));
                    System.out.println("fetched age:"+strAge);
                    strCountry = dataSnapshot.child("country").getValue(String.class);
                    strGender = dataSnapshot.child("gender").getValue(String.class);
                    name.setText(strName);
                    age.setText(strAge);
                    country.setText(strCountry);
                    gender.setText(strGender);
                    created.setText(getDate(user.getMetadata().getCreationTimestamp(),"dd/MM/yyyy | hh:mm:ss"));
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Home.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //to convert millisecond time to DateTime format
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

}
