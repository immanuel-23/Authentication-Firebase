package com.example.authentication;

import androidx.annotation.NonNull;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {
    EditText rfullname,remail,rpassword;
    Button rregisterbutton1;
    TextView loginbtn;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rfullname=findViewById(R.id.fullname);
        remail=findViewById(R.id.email);
        rpassword=findViewById(R.id.Password);
        rregisterbutton1=findViewById(R.id.registerbutton1);
        loginbtn=findViewById(R.id.loginbtn);

        fAuth =FirebaseAuth.getInstance();/*we are getting instance of database*/
        if(fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        rregisterbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=remail.getText().toString().trim();
                String password=rpassword.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    remail.setError("Email is required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    rpassword.setError("Password is required");
                }
                if(password.length()<8){
                    rpassword.setError("Password must have more then 8 characters");
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user =fAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(getApplicationContext(),"Verification email sent ",Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();


                                }
                            });
                            Toast.makeText(getApplicationContext(),"Account Created Succesfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Error "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                        }


                    }
                });

            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }
}