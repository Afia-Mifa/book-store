package com.aiub.knowlegebookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class adminActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText AemailEditText, ApasswordEditText;
    public Button AloginButton;    //creating button variable
    public TextView goUserLogin;  //creating text view variable
    // private FirebaseAuth mAuth;
    private ProgressBar AprogressBarLogIn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //mAuth = FirebaseAuth.getInstance();z


        AemailEditText = findViewById(R.id.adminlogInMail);
        ApasswordEditText = findViewById(R.id.adminLogInPassword);
        AloginButton = (Button) findViewById(R.id.adminlogIn);   //finding id of log in button
        goUserLogin = findViewById(R.id.goToUserLogin);   // finding id of goToSingUp text
        AprogressBarLogIn = findViewById(R.id.adminProgressBar);

        AloginButton.setOnClickListener(this);  //adding listener with logIn button
        goUserLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.adminlogIn:
                verifyAdmin(AemailEditText.getText().toString().trim(),ApasswordEditText.getText().toString().trim());
                break;
            case R.id.goToUserLogin:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }




    //progressBarLogIn.setVisibility(View.VISIBLE);



    private void verifyAdmin(final String p,final String w) {


        final DatabaseReference rootRef;
        rootRef = FirebaseDatabase.getInstance().getReference();

        if (p.isEmpty()) {
            AemailEditText.setError("Enter an email Address");
            AemailEditText.requestFocus();
            return;
        }
        if (w.isEmpty()) {
            ApasswordEditText.setError("Enter an password");
            ApasswordEditText.requestFocus();
            return;
        }

        AprogressBarLogIn.setVisibility(View.VISIBLE);

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child("Admin").child(p).exists())
                {

                      String phone1 = dataSnapshot.child("Admin").child(p).child("phone").getValue().toString();
                      String password1 = dataSnapshot.child("Admin").child(p).child("password").getValue().toString();
                    String name = dataSnapshot.child("Admin").child(p).child("Name").getValue().toString();

                    if(p.equals(phone1) && w.equals(password1)) {
                        AprogressBarLogIn.setVisibility(View.GONE);

                        Intent intent = new Intent(getApplicationContext(),productActivity.class);
                        intent.putExtra("N",name);
                        intent.putExtra("id",phone1);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        AprogressBarLogIn.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Illegal Attempt: Wrong entry Code", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    AprogressBarLogIn.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), " No Admin Registered By this ID", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }


}

