package com.example.haniumqr;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class HostMsg extends Fragment {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private DatabaseReference rDatabase;
    String apply_userID;
    String hotel_name;
    public HostMsg() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String userID = getArguments().getString("userID");
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_host_msg, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        rDatabase = firebaseDatabase.getReference("Apply_list");

        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.HotelMsgRecyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(layout.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        final HostMsgAdapter hostMsgAdapter= new HostMsgAdapter();
        recyclerView.setAdapter(hostMsgAdapter);

        rDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                for(DataSnapshot mdata : dataSnapshot.getChildren()) {
                    String key = mdata.getKey();
                    if(key.equals("Hotel_name")) {
                        hotel_name = mdata.getValue().toString();
                    }
                    if(mdata.getValue().toString().equals(userID)) {
                        ApplyMsgData applyMsgData = new ApplyMsgData(hotel_name,dataSnapshot.getKey(),userID);
                        hostMsgAdapter.addItem(applyMsgData);
                        hostMsgAdapter.notifyDataSetChanged();
                    }
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
