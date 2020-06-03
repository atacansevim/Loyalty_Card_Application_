package com.example.loyalty_card_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    String currentuseremail;
    EditText nameText,surnameText,birthdateText,phoneText;
    Spinner spinner;
    String gender;
    String DocumentId;
    private DatePickerDialog.OnDateSetListener mDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        spinner = findViewById(R.id.spinner);
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender,R.layout.support_simple_spinner_dropdown_item);
        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //spinner.setAdapter(adapter);
       // spinner.setOnItemClickListener((AdapterView.OnItemClickListener) this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail();
        nameText = findViewById(R.id.editTextName);
        surnameText = findViewById(R.id.editTextSurname);
        birthdateText = findViewById(R.id.editTextBirthDate);
        phoneText = findViewById(R.id.editTextPhone);
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        getdataFromFirebase();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = parent.getItemAtPosition(position).toString();
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
                        EditProfileActivity.this,
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent CardDetailsFromListActivity = new Intent(EditProfileActivity.this,HomePageActivity.class);
                        startActivity(CardDetailsFromListActivity);
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(EditProfileActivity.this,ChooseCardFromListActivity.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(EditProfileActivity.this,OfferActivity.class);
                        startActivity(OfferActivity);

                        break;
                    case R.id.navigation_account:
                        Intent AccountPageActivity = new Intent(EditProfileActivity.this,AccountPageActivity.class);
                        startActivity(AccountPageActivity);

                        break;
                }
                return false;
            }
        });
    }

    public void getdataFromFirebase()
    {

        CollectionReference collectionReference = firebaseFirestore.collection("UserData");
        collectionReference.whereEqualTo("userEmail",currentuseremail).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null)
                {

                }
                if(queryDocumentSnapshots != null)
                {

                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments())
                    {
                        nameText.setText(d.get("userName").toString());
                        surnameText.setText(d.get("userSurname").toString());
                        phoneText.setText(d.get("userPhone").toString());
                        birthdateText.setText(d.get("userBirthdate").toString());

                         if(d.get("userGender").toString().equalsIgnoreCase("Female"))
                         {
                             spinner.setSelection(0);
                             gender = "Female";
                         }
                         else if(d.get("userGender").toString().equalsIgnoreCase("Male"))
                         {
                             spinner.setSelection(1);
                             gender = "Male";
                         }
                         else
                         {
                             spinner.setSelection(2);
                             gender = "IDK";

                         }
                        DocumentId = d.getId();

                    }

                }
            }
        });
    }

    public void Save(View view){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        String userName = nameText.getText().toString();
        String userSurname = surnameText.getText().toString();
        String userBirthdate = birthdateText.getText().toString();
        String userPhone = phoneText.getText().toString();
        HashMap<String,Object> UserData = new HashMap<>();
        UserData.put("userEmail",firebaseUser.getEmail().toString());
        if(userName.matches(""))
        {
            Toast.makeText(EditProfileActivity.this,"EmptyName",Toast.LENGTH_LONG).show();
        }
        else
        {
            UserData.put("userName",userName);
        }
        if(userSurname.matches(""))
        {
            Toast.makeText(EditProfileActivity.this,"EmptySurname",Toast.LENGTH_LONG).show();
        }
        else
        {
            UserData.put("userSurname",userSurname);
        }
        if(userBirthdate.matches(""))
        {
            Toast.makeText(EditProfileActivity.this,"Empty Birthdate",Toast.LENGTH_LONG).show();
        }
        else
        {

            UserData.put("userBirthdate",userBirthdate);


        }
        if(userPhone.matches(""))
        {
            Toast.makeText(EditProfileActivity.this,"Empty Phone",Toast.LENGTH_LONG).show();
        }
        else
        {
            UserData.put("userPhone",userPhone);
        }

        UserData.put("userGender",gender);

  DocumentReference UserDataRef = firebaseFirestore.collection("UserData").document(DocumentId);

  UserDataRef.update(UserData).addOnCompleteListener(new OnCompleteListener<Void>() {
      @Override
      public void onComplete(@NonNull Task<Void> task) {
          if(task.isSuccessful())
          {
              Toast.makeText(EditProfileActivity.this,"Succes",Toast.LENGTH_LONG).show();
          }
          else
          {
              Toast.makeText(EditProfileActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();

          }
      }
  });



    }



}
