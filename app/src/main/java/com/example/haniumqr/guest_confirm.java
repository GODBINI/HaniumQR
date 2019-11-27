package com.example.haniumqr;


import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Hashtable;


/**
 * A simple {@link Fragment} subclass.
 */
public class guest_confirm extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private DatabaseReference rDatabase;

    String QR="";


    public guest_confirm() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String userID = getArguments().getString("userID");
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_guest_confirm, container, false);
        ImageView QR_image = (ImageView)layout.findViewById(R.id.QR_image);
        TextView reserveText = (TextView)layout.findViewById(R.id.receiverText);

        firebaseDatabase = FirebaseDatabase.getInstance();
        rDatabase = firebaseDatabase.getReference("QR");
        rDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue().toString();
                if(key.equals(userID)) {
                    QR = value;
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try{
                        BitMatrix bitMatrix = multiFormatWriter.encode(QR, BarcodeFormat.QR_CODE,200,200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        QR_image.setImageBitmap(bitmap);
                    }catch (Exception e){}
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return layout;
    }
}
