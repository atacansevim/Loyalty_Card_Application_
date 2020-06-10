package com.example.loyalty_card_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanBarcode extends AppCompatActivity implements View.OnClickListener {
    String cardnumber = "";
    String cardname = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);
        cardname = getIntent().getStringExtra("CardName");
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ScanBarcode.this);
        builder1.setMessage("Do want to Scan Card Number Or Enter Manually ?");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Scan",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        scannow();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Manually Enter",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(ScanBarcode.this,ManuallyCardAdding.class);
                        intent.putExtra("CardName",cardname);
                        startActivity(intent);
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
        {
            if(result.getContents() == null)
            {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
                alertdialogbuilder.setMessage("Result Not Found, Do you want write your card number?");
                alertdialogbuilder.setTitle("Enter Manullay");
                alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ScanBarcode.this,ManuallyCardAdding.class);
                        intent.putExtra("CardName",cardname);
                        startActivity(intent);

                    }
                });
                alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scannow();
                    }
                });
                AlertDialog alertDialog = alertdialogbuilder.create();
                alertDialog.show();
            }
            else
            {
                AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
                cardnumber = result.getContents();
                alertdialogbuilder.setMessage(result.getContents() + "\n\nScan Again?");
                alertdialogbuilder.setTitle("Result Scanned");
                alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        scannow();
                    }
                });
                alertdialogbuilder.setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ScanBarcode.this,ManuallyCardAdding.class);
                        intent.putExtra("CardName",cardname);
                        intent.putExtra("CardNumber",cardnumber);
                        startActivity(intent);
                        finish();
                    }
                });
                AlertDialog alertDialog = alertdialogbuilder.create();
                alertDialog.show();
            }
        }
        else
        {
            super.onActivityResult(requestCode,resultCode,data);
        }
    }

    public void scannow()
    {
        IntentIntegrator ıntentIntegrator = new IntentIntegrator(this);
        ıntentIntegrator.setCaptureActivity(Portrait.class);
        ıntentIntegrator.setOrientationLocked(false);
        ıntentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        ıntentIntegrator.setPrompt("Scan Your Card");
        ıntentIntegrator.setBarcodeImageEnabled(true);
        ıntentIntegrator.initiateScan();
    }

    @Override
    public void onClick(View v) {
        scannow();
    }

    public void clickbutton(View v)
    {
        Intent intent = new Intent(ScanBarcode.this,ManuallyCardAdding.class);
        intent.putExtra("CardName",cardname);
        startActivity(intent);
    }
}
