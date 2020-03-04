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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class PersonalInformationActivity extends AppCompatActivity {
    EditText nameText,surnameText,birthdateText,phoneText;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        nameText = findViewById(R.id.editTextName);
        surnameText = findViewById(R.id.editTextSurname);
        birthdateText = findViewById(R.id.editTextBirthDate);
        phoneText = findViewById(R.id.editTextPhone);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        Login();
    }
    public void Login(){
        String userEmail = getIntent().getStringExtra("userEmail");
        String userPassword = getIntent().getStringExtra("userPassword");
        firebaseAuth.signInWithEmailAndPassword(userEmail,userPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PersonalInformationActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Save(View view){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userEmail = getIntent().getStringExtra("userEmail");
        String userName = nameText.getText().toString();
        String userSurname = surnameText.getText().toString();
        String userBirthdate = birthdateText.getText().toString();
        String userPhone = phoneText.getText().toString();
        HashMap<String,Object> UserData = new HashMap<>();
        UserData.put("userEmail",userEmail);
        if(userName.matches(""))
        {
            Toast.makeText(PersonalInformationActivity.this,"EmptyName",Toast.LENGTH_LONG).show();
        }
        else
        {
            UserData.put("userName",userName);
        }
        if(userSurname.matches(""))
        {
            Toast.makeText(PersonalInformationActivity.this,"EmptySurname",Toast.LENGTH_LONG).show();
        }
        else
        {
            UserData.put("userSurname",userSurname);
        }
        if(userBirthdate.matches(""))
        {
            Toast.makeText(PersonalInformationActivity.this,"Empty Birthdate",Toast.LENGTH_LONG).show();
        }
        else
        {
            Date userbirthdate;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            try {
                userbirthdate = format.parse(userBirthdate);
                UserData.put("userBirthdate",userbirthdate);
            } catch (ParseException e) {
                Toast.makeText(PersonalInformationActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }

        }
        if(userPhone.matches(""))
        {
            Toast.makeText(PersonalInformationActivity.this,"Empty Phone",Toast.LENGTH_LONG).show();
        }
        else
        {

            UserData.put("userPhone",userPhone);
        }
        //String user_id = firebaseAuth.getUid();

        //UserData.put("UserUUID",user_id);
        firebaseFirestore.collection("UserData").add(UserData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Intent intent = new Intent(PersonalInformationActivity.this,HomePageActivity.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PersonalInformationActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });



    }
    public void Skip(View view){
        //Consider this page if user does not fill fieldss...

    }
}
