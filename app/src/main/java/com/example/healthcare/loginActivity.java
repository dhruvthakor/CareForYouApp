package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {

    EditText email;
    EditText password;
    Button login;
    Button signup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        email = findViewById(R.id.emailLogin);
        password  = findViewById(R.id.passwordLogin);
        login = findViewById(R.id.btnLogin);
        signup = findViewById(R.id.signbtn);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        login.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                validate(email.getText().toString(),password.getText().toString());
            }
        });

        signup.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

    }
    private void validate(String useremail, String userpassword){

        progressDialog.setMessage("Wait for Login...");
        progressDialog.show();
        if(useremail.equals("")){
            Toast.makeText(loginActivity.this,"Enter the Email Id.",Toast.LENGTH_SHORT).show();
        }else if(userpassword.equals("")){
            Toast.makeText(loginActivity.this,"Enter the Password.",Toast.LENGTH_SHORT).show();
        }
        else{
            clickLogin(useremail,userpassword);
        }
    }
    public void clickLogin(String user_name,String password){

        firebaseAuth.signInWithEmailAndPassword(user_name,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(loginActivity.this,HomeActivity.class);
                    startActivity(intent1);
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(loginActivity.this,"Login Failed...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
