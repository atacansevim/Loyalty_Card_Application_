package com.example.loyalty_card_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AccountPageActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    String currentuseremail;
    TextView useremailtw;
    ImageView useraccountimage;
    public FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        useremailtw = (TextView)findViewById(R.id.useremail);
        useraccountimage = (ImageView) findViewById(R.id.UserAvatarImage);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail();
        useremailtw.setText(currentuseremail);
        useraccountimage.setImageResource(R.drawable.user);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent AccountPageActivity = new Intent(AccountPageActivity.this,HomePageActivity.class);
                        startActivity(AccountPageActivity);
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(AccountPageActivity.this,ChooseCardFromListActivity.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(AccountPageActivity.this,OfferActivity.class);
                        startActivity(OfferActivity);

                        break;
                    case R.id.navigation_account:


                        break;
                }
                return false;
            }
        });
    }
    public void EditProfile(View view)
    {
        Intent a = new Intent(AccountPageActivity.this,EditProfileActivity.class);
        startActivity(a);
    }
    public void RemoveAllData(View view)
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(AccountPageActivity.this);
        builder1.setMessage("Are You Sure Delete Card?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        FindDocumentIdAndDelete("UserData");
                        FindDocumentIdAndDelete("CardData");
                        FindDocumentIdAndDelete("ShopData");
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    public void DeleteDocument(String DocumentId,String ColletionPath)
    {
        DocumentReference UserDataRef = firebaseFirestore.collection(ColletionPath).document(DocumentId);

        UserDataRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(AccountPageActivity.this,"Succes Deleted",Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(AccountPageActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void FindDocumentIdAndDelete(final String ColletionPath)
    {
        CollectionReference collectionReference = firebaseFirestore.collection(ColletionPath);
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
                        DeleteDocument(d.getId().toString(),ColletionPath);
                    }
                }
            }
        });
    }
    public void DeleteAccount(View view)
    {


        AlertDialog.Builder builder1 = new AlertDialog.Builder(AccountPageActivity.this);
        builder1.setMessage("Are You Sure Delete Account Your All Data Lose?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        FindDocumentIdAndDelete("UserData");
                        FindDocumentIdAndDelete("CardData");
                        FindDocumentIdAndDelete("ShopData");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        user.delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Intent OfferActivity = new Intent(AccountPageActivity.this,LoginActivity.class);
                                            startActivity(OfferActivity);
                                            finish();
                                        }
                                    }
                                });
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
