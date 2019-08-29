package com.example.haniumqr;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class sendHoteldata {
    public String Hotel_name;
    public String Hotel_detail;
    public String Hotel_address;

    public sendHoteldata(String Hotel_name,String Hotel_detail,String Hotel_address) {
        this.Hotel_address = Hotel_address;
        this.Hotel_detail = Hotel_detail;
        this.Hotel_name = Hotel_name;
    }
}
