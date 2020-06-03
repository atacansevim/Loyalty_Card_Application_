package com.example.loyalty_card_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class PersonalInformationActivity extends AppCompatActivity{
    EditText nameText,surnameText,birthdateText,phoneText;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    Spinner spinner_gender;
    String Gender;
    private DatePickerDialog.OnDateSetListener mDatePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);
        nameText = findViewById(R.id.editTextName);
        surnameText = findViewById(R.id.editTextSurname);
        birthdateText = findViewById(R.id.editTextBirthDate);
        phoneText = findViewById(R.id.editTextPhone);
        spinner_gender = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(adapter);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        Login();
        spinner_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Gender = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        birthdateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        PersonalInformationActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDatePicker,
                        year,month,day);
                dialog.getDatePicker().setMaxDate(new Date().getTime());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = month + "." + day + "." + year;
                birthdateText.setText(date);
            }
        };

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
        UserData.put("userEmail",firebaseUser.getEmail().toString());
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
            UserData.put("userBirthdate",userBirthdate);
        }
        if(userPhone.matches(""))
        {
            Toast.makeText(PersonalInformationActivity.this,"Empty Phone",Toast.LENGTH_LONG).show();
        }
        else
        {
            UserData.put("userPhone",userPhone);
        }
        if(Gender.matches("") || Gender == null)
        {
            Toast.makeText(PersonalInformationActivity.this,"Empty Gender",Toast.LENGTH_LONG).show();
        }
        else
        {
            UserData.put("userGender",Gender);
        }


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
