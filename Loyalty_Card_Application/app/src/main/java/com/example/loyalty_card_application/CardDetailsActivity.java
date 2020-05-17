package com.example.loyalty_card_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CardDetailsActivity extends AppCompatActivity {
    ImageView imageView;
    TextView Description;
    String test;
    ImageView cardlogo;
    private FirebaseAuth firebaseAuth;
    public FirebaseFirestore firebaseFirestore;
    String currentuseremail;String useremal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_details);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        currentuseremail = firebaseAuth.getCurrentUser().getEmail();
        imageView = (ImageView)findViewById(R.id.QRcode);
        Description = (TextView)findViewById(R.id.Description);
        test = getIntent().getStringExtra("CardName");
        GenerateQR(test);
        getdataFromFirebase();
        cardlogo = (ImageView)findViewById(R.id.cardImageView);
        if(test.equals("Migros"))
        {
            cardlogo.setImageResource(R.drawable.migros);
        }
        else if(test.equals("Teknosa"))
        {
            cardlogo.setImageResource(R.drawable.teknosa_logo);
        }
        else
        {
            cardlogo.setImageResource(R.drawable.migros);
        }
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
        collectionReference.whereEqualTo("userEmail",currentuseremail).whereEqualTo("CardName",test).addSnapshotListener(new EventListener<QuerySnapshot>() {
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

                    }

                }
            }
        });




    }
}
