package com.example.a11984.mady_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailView, mPasswordView;
    private static final String TAG = "LoginActivity";
    private TextView nextButton;
    private TextView editText;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mEmailView = findViewById(R.id.email);
        mPasswordView = findViewById(R.id.password);
        nextButton = findViewById(R.id.sign_in);
        editText = findViewById(R.id.Registration);
        mAuth = FirebaseAuth.getInstance();

    }
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.

    }
    public void onResume() {
        super.onResume();

    }
    public void Sign_in_Button(View view) {
        int i = view.getId();

        if (!validateForm()) {
            return;
        }
        signIn(mEmailView.getText().toString(), mPasswordView.getText().toString());
    }
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
            // [START sign_in_with_email]
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser mCurrentUser = mAuth.getCurrentUser();
                            mCurrentUser.reload();
                            if(mCurrentUser.isEmailVerified()){
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "Successful Sign In");
                                nextButton.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View view) {
                                        startActivity(new Intent(LoginActivity.this, Main_Activity.class));
                                    finish();
                                    }
                                });
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }}
                                      else {
                                Toast.makeText(LoginActivity.this, "Please verify you're email",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            // [END sign_in_with_email]
        }


    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError("Required.");
            valid = false;
        } else {
            mEmailView.setError(null);
        }

        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Required.");
            valid = false;
        } else {
            mPasswordView.setError(null);
        }

        return valid;
    }

    public void Registration(View view) {
        editText.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, Registration_Activity.class));
            }
        });

}}

