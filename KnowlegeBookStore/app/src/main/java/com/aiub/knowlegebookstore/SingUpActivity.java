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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class SingUpActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    public EditText SemailEditText, SpasswordEditText;
    public Button SignUpButton;    //creating button variable
    public TextView goLogIn;  //creating text veiw variable
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        SemailEditText = findViewById(R.id.signUpMail);
        SpasswordEditText = findViewById(R.id.SignUpPassword);
        SignUpButton = (Button) findViewById(R.id.SingUpButton);   //finding id of log in button
        goLogIn = findViewById(R.id.GoLogIn);   // finding id of goToSingUp text
        progressBar = findViewById(R.id.progressBar);

        SignUpButton.setOnClickListener(this);  //adding listener with logIn button
        goLogIn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SingUpButton:
                userRegister();
                break;
            case R.id.GoLogIn:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    private void userRegister() {

        String email = SemailEditText.getText().toString().trim();
        String password = SpasswordEditText.getText().toString().trim();


        if (email.isEmpty()) {
            SemailEditText.setError("Enter an email Address");
            SemailEditText.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            SemailEditText.setError("Enter a valid email Address");
            SemailEditText.requestFocus();
            return;

        }

        if (password.isEmpty()) {
            SpasswordEditText.setError("Enter an password");
            SpasswordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            SpasswordEditText.setError("Minimum length of password is 6");
            SpasswordEditText.requestFocus();
            return;

        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(getApplicationContext(), "Register is successful", Toast.LENGTH_SHORT).show();
                }
                else {
                    // If sign in fails, display a message to the user.
                   if(task.getException() instanceof FirebaseAuthUserCollisionException){
                       Toast.makeText(getApplicationContext(), "User is alraedy registered", Toast.LENGTH_SHORT).show();
                   }
                   else{
                       Toast.makeText(getApplicationContext(), "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                   }
                }


            }

        });
    }

}




