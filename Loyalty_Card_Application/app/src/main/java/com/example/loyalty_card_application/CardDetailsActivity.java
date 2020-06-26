package com.example.loyalty_card_application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class CardDetailsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView Description;
    String CardName;
    String CardNumber;
    ImageView cardlogo;
    private FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    String currentuseremail;
    String DocumentId;
    String CardDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail().toString();
        imageView = (ImageView)findViewById(R.id.QRcode);
        Description = (TextView)findViewById(R.id.Description);
        CardName = getIntent().getStringExtra("CardName");
        CardNumber = getIntent().getStringExtra("CardNumber");
        cardlogo = (ImageView)findViewById(R.id.cardImageView);
        GetUrl();
        GenerateQR(CardNumber);
        getdataFromFirebase();



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation_bar);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent CardDetailsFromListActivity = new Intent(CardDetailsActivity.this,HomePageActivity.class);
                        startActivity(CardDetailsFromListActivity);
                        break;
                    case R.id.navigation_add_card:
                        Intent ChooseCardFromListActivity = new Intent(CardDetailsActivity.this,ChooseCardFromListActivity.class);
                        startActivity(ChooseCardFromListActivity);
                        break;
                    case R.id.navigation_offers:
                        Intent OfferActivity = new Intent(CardDetailsActivity.this,OfferActivity.class);
                        startActivity(OfferActivity);

                        break;
                    case R.id.navigation_account:
                        Intent AccountPageActivity = new Intent(CardDetailsActivity.this,AccountPageActivity.class);
                        startActivity(AccountPageActivity);

                        break;
                }
                return false;
            }
        });
        _CreateShopData();

    }
    public void GetUrl()
    {
        StorageReference urlreferance = FirebaseStorage.getInstance().getReference("logo/"+CardName.toLowerCase()+".png");
        urlreferance.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                   String dowlandurl = uri.toString();
                   Picasso.get().load(dowlandurl).into(cardlogo);
            }
        });
    }

    public void StartEditCardActivity(View view)
    {
        Intent intent = new Intent(CardDetailsActivity.this,CardEditActivity.class);
        intent.putExtra("CardName", CardName);
        intent.putExtra("CardNumber",CardNumber);
        intent.putExtra("DocumentId",DocumentId);
        intent.putExtra("CardDescription",CardDescription);
        startActivity(intent);
    }

    public void DeleteCard(View view)
    {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(CardDetailsActivity.this);
        builder1.setMessage("Are You Sure Delete Card?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        DocumentReference UserDataRef = firebaseFirestore.collection("CardData").document(DocumentId);

                        UserDataRef.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(CardDetailsActivity.this,"Successfully Deleted",Toast.LENGTH_LONG).show();
                                    Intent AccountPageActivity = new Intent(CardDetailsActivity.this,HomePageActivity.class);
                                    startActivity(AccountPageActivity);
                                }
                                else
                                {
                                    Toast.makeText(CardDetailsActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();

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

    public void GenerateQR(String text)
    {

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,400,400);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void getdataFromFirebase()
    {
        CollectionReference collectionReference = firebaseFirestore.collection("CardData");
        collectionReference.whereEqualTo("userEmail",currentuseremail).whereEqualTo("CardName",CardName.toLowerCase()).whereEqualTo("CardNumber",CardNumber).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e != null)
                {

                }
                if(queryDocumentSnapshots != null)
                {
                    for(DocumentSnapshot d:queryDocumentSnapshots.getDocuments())
                    {

                       Description.setText(d.get("CardDescription").toString());
                       DocumentId = d.getId();
                       CardDescription = d.get("CardDescription").toString();
                    }
                }
            }
        });
    }
    public void CardDetails(View view)
    {
        Intent intent = new Intent(CardDetailsActivity.this,CardTransactionActivity.class);
        intent.putExtra("CardNumber",CardNumber);
        intent.putExtra("CardName",CardName);
        startActivity(intent);
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    public void CreateShopData()
    {
        Random rand = new Random();
        int product_count = (int) (1  + Math.random() * (5 - 1));
        int index = 0;
        Double Amount = 10 + rand.nextDouble() * (1000 - 10);
        Amount = round(Amount, 2);
        HashMap<String,Object> ShopData = new HashMap<>();
        ArrayList<String> Produtcs = new ArrayList<String>();
        String[] Migros = new String[]{"Delicatessen","Fruits&Vegetables","Furniture","Drink","Food","Cleaning Materials"};
        String[] Teknosa = new String[]{"Phone","Computer","HeadPhone","Accessory","Charger","Computer Components"};

        for(int i = 0; i < product_count ;i++)
        {
            index = (int) (1  + Math.random() * (6 - 1));
            if(CardName.equalsIgnoreCase("Teknosa"))
            {
                Produtcs.add(Teknosa[index]);
            }
            else
            {
                Produtcs.add(Migros[index]);
            }
        }
        //Amount CardName CardNumber Date Product userEmail
        ShopData.put("Amount",Amount.toString());
        ShopData.put("CardName",CardName);
        ShopData.put("CardNumber",CardNumber);
        ShopData.put("userEmail",currentuseremail);
        ShopData.put("Product",Produtcs);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        ShopData.put("Date",currentDateandTime.toString());

        firebaseFirestore.collection("ShopData").add(ShopData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(CardDetailsActivity.this,"Added Shop Data",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CardDetailsActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }


    public void _CreateShopData()
    {
        Random rand = new Random();
        int product_count = (int) (1  + Math.random() * (5 - 1));
        int day = (int) (1  + Math.random() * (30 - 1));
        int month = (int) (1  + Math.random() * (6 - 1));
        int index = 0;
        Double Amount = 10 + rand.nextDouble() * (1000 - 10);
        Amount = round(Amount, 2);
        HashMap<String,Object> ShopData = new HashMap<>();
        ArrayList<String> Produtcs = new ArrayList<String>();
        String[] Migros = new String[]{"Delicatessen","Fruits&Vegetables","Furniture","Drink","Food","Cleaning Materials"};
        String[] Teknosa = new String[]{"Phone","Computer","HeadPhone","Accessory","Charger","Computer Components"};

        for(int i = 0; i < product_count ;i++)
        {
            index = (int) (1  + Math.random() * (6 - 1));
            if(CardName.equalsIgnoreCase("Teknosa"))
            {
                Produtcs.add(Teknosa[index]);
            }
            else
            {
                Produtcs.add(Migros[index]);
            }
        }
        //Amount CardName CardNumber Date Product userEmail
        ShopData.put("Amount",Amount.toString());
        ShopData.put("CardName",CardName);
        ShopData.put("CardNumber",CardNumber);
        ShopData.put("userEmail",currentuseremail);
        ShopData.put("Product",Produtcs);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        String[] parsedCurrentDay = currentDateandTime.split(" ");
        if (day < 10 & month < 10)
        {
            currentDateandTime = "0"+day+"."+"0"+month+"."+"2020 "+parsedCurrentDay[1];
        }
        else if (day >= 10 & month < 10)
        {
            currentDateandTime = day+"."+"0"+month+"."+"2020 "+parsedCurrentDay[1];
        }
        else if (day < 10 & month >= 10)
        {
            currentDateandTime = "0"+day+"."+month+"."+"2020 "+parsedCurrentDay[1];
        }
        else
        {
            currentDateandTime = currentDateandTime;
        }

        ShopData.put("Date",currentDateandTime.toString());

        firebaseFirestore.collection("ShopData").add(ShopData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(CardDetailsActivity.this,"Added Shop Data",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CardDetailsActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
