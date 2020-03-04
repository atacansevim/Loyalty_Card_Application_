package com.example.loyalty_card_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText emailText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.EmaileditText);
    }

    public void ResetPassword(View view)
    {
        String email = emailText.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(ForgotPasswordActivity.this,"Empty Email",Toast.LENGTH_LONG).show();
        }
        else
        {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        //check e-mail exsist
                        Toast.makeText(ForgotPasswordActivity.this,"Please Check Your Email to reset your password",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ForgotPasswordActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(ForgotPasswordActivity.this,message,Toast.LENGTH_LONG).show();
                    }

                }
            });
        }

    }
}
