package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    EditText Lemail,Lpassword;
    Button loginbtn;
    TextView Rlbtn,fpassword;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Lemail=findViewById(R.id.email);
        Lpassword=findViewById(R.id.Password);
        fAuth=FirebaseAuth.getInstance();
        loginbtn=findViewById(R.id.loginbtn);
        Rlbtn=findViewById(R.id.RLbtn);
        fpassword=findViewById(R.id.fpassword);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=Lemail.getText().toString().trim();
                String password=Lpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    Lemail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Lpassword.setError("Password is required");
                }
                if(password.length()<8){
                    Lpassword.setError("Password must have more then 8 characters");
                }

                //
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Logged in Succesfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });
        Rlbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
        fpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Reset_password.class));
            }
        });

    }
}