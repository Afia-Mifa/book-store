package com.aiub.knowlegebookstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public EditText emailEditText,passwordEditText;
    public Button loginButton;    //creating button variable
    public TextView goSignUp;  //creating text view variable
    private FirebaseAuth mAuth;
    private ProgressBar progressBarLogIn;
    public TextView goAdminLogin;
    public TextView forgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.logInMail);
        passwordEditText = findViewById(R.id.LogInPassword);
        loginButton = (Button) findViewById(R.id.logIn);   //finding id of log in button
        goSignUp = findViewById(R.id.goToSignUp);   // finding id of goToSingUp text
        progressBarLogIn = findViewById(R.id.progressBarlogIn);
        forgetPassword = findViewById(R.id.forgetPassword);
        goAdminLogin = findViewById(R.id.goToAdminLogIn);

        loginButton.setOnClickListener(this);  //adding listener with logIn button
        goSignUp.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
        goAdminLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.logIn:
                userRegister();
                break;
            case R.id.goToSignUp:
                Intent intent = new Intent(getApplicationContext(),SingUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.forgetPassword:
                Intent intent2 = new Intent(getApplicationContext(),resetPassword.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent2);
                break;
            case R.id.goToAdminLogIn:
                Intent intent1 = new Intent(getApplicationContext(),adminActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
        }
    }



    private void userRegister() {

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();


        if (email.isEmpty()) {
            emailEditText.setError("Enter an email Address");
            emailEditText.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter a valid email Address");
            emailEditText.requestFocus();
            return;

        }

        if (password.isEmpty()) {
            passwordEditText.setError("Enter an password");
            passwordEditText.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passwordEditText.setError("Minimum length of password is 6");
            passwordEditText.requestFocus();
            return;

        }
        progressBarLogIn.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBarLogIn.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(getApplicationContext(),HomePage.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            upload Up = new upload();
                            String T= Up.getProduct_type();
                            Toast.makeText(getApplicationContext(), "Warning: Invalid Password"+T, Toast.LENGTH_SHORT).show();

                        }
                    }
        });


    }
}