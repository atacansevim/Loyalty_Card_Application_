package com.example.loyalty_card_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    EditText emailText,passwordText,confirmpasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.editTextEmail);
        passwordText = findViewById(R.id.editTextPassword);
        confirmpasswordText = findViewById(R.id.editTextConfirmPassword);


    }

    public void SignUpClicked(View view)
    {
        final String email = emailText.getText().toString();
        final String password = passwordText.getText().toString();
        String confirmpassword = confirmpasswordText.getText().toString();

        if(password.matches(confirmpassword))
        {
            //verify email
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MainActivity.this,"SignUp Succes",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,PersonalInformationActivity.class);
                    intent.putExtra("userEmail", email);
                    intent.putExtra("userPassword",password);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }
            });

        }
        else
        {
            Toast.makeText(MainActivity.this,"Unmatch Passwords",Toast.LENGTH_LONG).show();
        }


    }
    public void goLoginActivity(View view){
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }


}
