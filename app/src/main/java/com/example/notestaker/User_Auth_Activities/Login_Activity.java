package com.example.notestaker.User_Auth_Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notestaker.MainActivity;
import com.example.notestaker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.ktx.Firebase;

public class Login_Activity extends AppCompatActivity {

    TextInputEditText edittextEmail, editTextPassword;

    AppCompatButton log_btn;

    FirebaseAuth mAuth;

    ProgressBar progressBar;

    TextView txtreg;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currrentUser = mAuth.getCurrentUser();
        if(currrentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initilization
        edittextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        log_btn = findViewById(R.id.btn_login);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(com.google.android.material.R.id.progress_circular);
        txtreg = findViewById(R.id.txt_reg);


        txtreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        log_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;

                email = String.valueOf(edittextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if(TextUtils.isEmpty(email)) {
                    Toast.makeText(Login_Activity.this,
                            "Enter Email",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    Toast.makeText(Login_Activity.this,
                            "Enter Password",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()) {
                            Toast.makeText(Login_Activity.this,
                                    "Authentication Successful",
                                    Toast.LENGTH_SHORT).show();


                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(Login_Activity.this,
                                    "Wrong Email or Password",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }
}