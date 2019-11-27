package com.example.haniumqr;


import android.content.Intent;
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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class guest_Msg extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private DatabaseReference rDatabase;
    private String Msg;
    private String sendID;

    public guest_Msg() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String userID = getArguments().getString("userID");
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_guest__msg, container, false);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.guestMsgRecyclerView);
        Button guestMsgWriteButton = (Button)layout.findViewById(R.id.guest_write_button);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(layout.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        final GuestMsgAdapter guestMsgAdapter= new GuestMsgAdapter();
        recyclerView.setAdapter(guestMsgAdapter);
        firebaseDatabase = FirebaseDatabase.getInstance();
        rDatabase = firebaseDatabase.getReference("Msg").child(userID);

        rDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue().toString();
                if(key.equals("Message")) {
                    Msg = value;
                }
                else if(key.equals("sendID")) {
                    sendID = value;
                    GuestMsgData guestMsgData = new GuestMsgData("호스트 : "+sendID,Msg);
                    guestMsgAdapter.addItem(guestMsgData);
                    guestMsgAdapter.notifyDataSetChanged();
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

        guestMsgWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),MsgWriteActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
            }
        });
        return layout;
    }

}
