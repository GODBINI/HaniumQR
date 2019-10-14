package com.example.haniumqr;


import android.app.AlertDialog;
import android.net.sip.SipSession;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */




public class guest_search extends Fragment {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private DatabaseReference rDatabase;
    int count = 30;
    String []name = new String[count];
    String []detail = new String[count];
    String []address = new String[count];
    String []hostID = new String[count];
    int v_count = 0;
    public guest_search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v_count = 0;
        // Inflate the layout for this fragment
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_guest_search, container, false);
        LayoutInflater inflaters=getLayoutInflater();

        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.g_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(layout.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        final GuestSearchAdapter guestSearchAdapter = new GuestSearchAdapter();
        recyclerView.setAdapter(guestSearchAdapter);


        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        String userID = getArguments().getString("userID"); //4
        Button g_button = (Button)layout.findViewById(R.id.g_button);
        rDatabase = firebaseDatabase.getReference("Hotel_Info"); // Database 데이터 읽을 경로

        g_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guestSearchAdapter.init();
                int d_count = 0;
                while(d_count < v_count) {
                    SearchData searchData = new SearchData(name[d_count],address[d_count],detail[d_count],userID,hostID[d_count]);
                    guestSearchAdapter.addItem(searchData);
                    d_count++;
                }
                guestSearchAdapter.notifyDataSetChanged();

            }
        });

        rDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                hostID[v_count] = dataSnapshot.getKey();
                for(DataSnapshot mdata : dataSnapshot.getChildren()){
                    String key = mdata.getKey();
                    String value = mdata.getValue().toString();
                    if(key.equals("Hotel_name")) {
                        name[v_count] = value;
                        v_count++;
                    }
                    else if (key.equals("Hotel_detail")) {
                        detail[v_count] = value;
                    }
                    else if (key.equals("Hotel_address")) {
                        address[v_count] = value;

                    }
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                /* String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue(String.class);
                if(key.equals("Hotel_name")) {
                    name = value;
                }
                else if (key.equals("Hotel_detail")) {
                    detail = value;
                }
                else if (key.equals("Hotel_address")) {
                    address = value;
                } */
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
