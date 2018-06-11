package com.example.a11984.mady_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration_Activity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText mEmailFied;
    private EditText mPasswordField;
    private EditText mName;
    private Button Reg;
    private DatabaseReference mDatabase;
    private ProgressBar progressBar;
    private EditText mFirm;
    private static final String TAG = "Registration_Activity";
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_);
        Reg= findViewById(R.id.Reg_click);
        progressBar = findViewById(R.id.progressBar);
        mName = findViewById(R.id.signup_input_name);
        mEmailFied = findViewById(R.id.signup_input_email);
        mPasswordField = findViewById(R.id.signup_input_password);
        mFirm = findViewById(R.id.Firm);
        mAuth = FirebaseAuth.getInstance();
        Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailFied.getText().toString();
                String password = mPasswordField.getText().toString();
                if(!validateForm()){return;}
                /*checkIfEmailVerified();*/
                progressBar.setVisibility(View.VISIBLE);
                //create user
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registration_Activity.this, new OnCompleteListener<AuthResult>() {
                            @Override

                            public void onComplete(@NonNull Task<AuthResult> task) {
                      progressBar.setVisibility(View.GONE);
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {

                                    Toast.makeText(Registration_Activity.this, "You are registred, please Log In!",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    sendEmailVerification();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    writeNewUser(user.getUid(), user.getEmail());
                                    Toast.makeText(Registration_Activity.this, "Welcome! Please Log In", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Registration_Activity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.Reg_click).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.Reg_click).setEnabled(true);

                        if (task.isSuccessful()) {
                                    Toast.makeText(Registration_Activity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Registration_Activity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }
    private void writeNewUser(String userId, String email) {
        mDatabase=FirebaseDatabase.getInstance().getReference();
        String password = mPasswordField.getText().toString();
        String name_of_firm = mFirm.getText().toString();
        String name = mName.getText().toString();
        User user = new User(name, email,password,name_of_firm);
        FirebaseDatabase.getInstance().getReference().child("users").child(userId).setValue(user);
    }
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailFied.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailFied.setError("Required.");
            valid = false;
        } else {
            mEmailFied.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }
        String name = mName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mName.setError("Required.");
            valid = false;
        } else {
            mName.setError(null);
        }
        String Firm = mFirm.getText().toString();
        if (TextUtils.isEmpty(Firm)) {
            mFirm.setError("Required.");
            valid = false;
        } else {
            mFirm.setError(null);
        }
        if(!validateEmailId(email)){
            Toast.makeText(getApplicationContext(), "Invalid Email address" , Toast.LENGTH_SHORT).show();
            return false;
        }

        //Password cannot contain space
        else if(!Pattern.matches("[^ ]*", password)){
            Toast.makeText(getApplicationContext(), "Password cannot contain space", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(name.length()<3){
            Toast.makeText(getApplicationContext(), "Enter you're Name" , Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 8) {
            Toast.makeText(getApplicationContext(), "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mFirm.length()<2) {
            Toast.makeText(getApplicationContext(), "Please described Name of Firm", Toast.LENGTH_SHORT).show();
            return false;
        }
        return valid;
    }
    public boolean validateEmailId(String emailId) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailId);
        return matcher.find();
    }
}
