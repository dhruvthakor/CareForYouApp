package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private EditText name, email, password, conpassword;
    private Button signup;
    private TextView login;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        clickSignup();

        firebaseAuth = FirebaseAuth.getInstance();

        //For Already Signedin And go to Login Activity
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, loginActivity.class));
            }
        });
    }
    public void clickSignup() {
        name = (EditText)findViewById(R.id.Name);
        email = (EditText)findViewById(R.id.Mail1);
        password = (EditText)findViewById(R.id.Password1);
        conpassword = (EditText)findViewById(R.id.Conpassword);
        signup = (Button)findViewById(R.id.Signupbtn);
        login = (TextView)findViewById(R.id.tvLogin);

        //On Signup button click
        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(validate()){
                    //Upload data to the DATABASE.
                    String user_email = email.getText().toString().trim();
                    String user_password = password.getText().toString().trim();

                    firebaseAuth.createUserWithEmailAndPassword(user_email,user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(SignupActivity.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                firebaseAuth.signOut();
                                Intent intent = new Intent(SignupActivity.this,loginActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(SignupActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validate(){
        Boolean result = false;

        String username = name.getText().toString();
        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();
        String userconpassword = conpassword.getText().toString();

        if(username.equals("")){
            Toast.makeText(SignupActivity.this,"Enter your name",Toast.LENGTH_SHORT).show();
        }else if(useremail.equals("")){
            Toast.makeText(SignupActivity.this,"Enter your Email Id",Toast.LENGTH_SHORT).show();
        }else if(userpassword.equals("")){
            Toast.makeText(SignupActivity.this,"Enter your password",Toast.LENGTH_SHORT).show();
        }else if(userconpassword.equals("")){
            Toast.makeText(SignupActivity.this,"Re-enter your password",Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
            // Toast.makeText(this,"Signup Successful...",Toast.LENGTH_SHORT).show();
            /* Intent intent = new Intent(SignupActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();*/
        }
        return result;
    }

   /* public void clickSignup(View view){
        Toast.makeText(this,"Signup Successful...",Toast.LENGTH_SHORT).show();

    }*/
}
