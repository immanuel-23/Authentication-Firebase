package com.example.authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_password extends AppCompatActivity {
        EditText reset_mail;
        Button rbutton;
        FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        reset_mail=findViewById(R.id.reset_mail);
        rbutton=findViewById(R.id.rbutton);
        fAuth=FirebaseAuth.getInstance();


        rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=reset_mail.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    reset_mail.setError("Email is required");
                    return;
                }
                fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Reset Password  link sent ",Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });
    }
}