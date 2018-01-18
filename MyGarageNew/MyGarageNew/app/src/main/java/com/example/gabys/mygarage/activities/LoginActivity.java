package com.example.gabys.mygarage.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gabys.mygarage.R;
import com.example.gabys.mygarage.UserHolderService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonSignin;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initializing firebase Auth object
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            //start logout activity
            finish();
            startActivity(new Intent(getApplicationContext(),LogoutActivity.class));
        }

        //initializing views
        editTextEmail = (EditText) findViewById(R.id.userField);
        editTextPassword = (EditText) findViewById(R.id.passwordField);
        buttonSignin = (Button) findViewById(R.id.signinButt);
        progressDialog = new ProgressDialog(this);
        //attaching listener to button
        buttonSignin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if(view == buttonSignin){
            // delete this 3 lines
            //UserHolderService.uuid = "AAPAIwZCnNPXAZs7NE1qFcHGw953";//"6iNvKrpRjYg6CFYGJOmBwq1psuW2";
            //finish();
            //startActivity(new Intent(getApplicationContext(), UserInsideActivity.class));

            userLogin();

        }

    }

    private void userLogin(){
        //

        //
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();
        Task<AuthResult> t = mAuth.signInWithEmailAndPassword(email,password);
        t.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            //start logout activity
                            UserHolderService.uuid = mAuth.getUid();
                            finish();
                            startActivity(new Intent(getApplicationContext(), UserInsideActivity.class));
                        }
                        else {
                            //display some message here
                            Toast.makeText(LoginActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }

                });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();



    }



    public void enterMap(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


    public void enterAboutUs(View view) {
        Intent intent = new Intent(this, ReadMeActivity.class);
        startActivity(intent);
    }
}
