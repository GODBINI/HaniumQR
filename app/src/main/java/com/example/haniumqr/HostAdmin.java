package com.example.haniumqr;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
public class HostAdmin extends Fragment {


    Button btn_addHotel;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    private DatabaseReference rDatabase;
    String name,detail,address;



    public HostAdmin() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_host_admin, container, false);
        LayoutInflater inflaters=getLayoutInflater();
        final View dialogView= inflaters.inflate(R.layout.hotel_admin_dialog, null);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();
        String userID = getArguments().getString("userID"); //4
        btn_addHotel = (Button)layout.findViewById(R.id.btn_addHotel);
        rDatabase = firebaseDatabase.getReference("Hotel_Info").child(userID); // Database 데이터 읽을 경로
        btn_addHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("숙소 정보를 입력해주세요.")
                        .setView(dialogView)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText editText = (EditText)dialogView.findViewById(R.id.hotel_name_text);
                                EditText editText1 = (EditText)dialogView.findViewById(R.id.hotel_detail_text);
                                EditText editText2 = (EditText)dialogView.findViewById(R.id.hotel_address_text);
                                String Hotel_name= editText.getText().toString();
                                String Hotel_detail= editText1.getText().toString();
                                String Hotel_address= editText2.getText().toString();
                                sendHoteldata sendHoteldata = new sendHoteldata(Hotel_name,Hotel_detail,Hotel_address);
                                mDatabase.child("Hotel_Info").child(userID).setValue(sendHoteldata);
                                Toast.makeText(v.getContext(),"숙소 입력완료!",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create()
                        .show();
            }
        });

        Button Hotel_Search = (Button) layout.findViewById(R.id.Hotel_Search);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(layout.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        final SearchAdapter searchAdapter= new SearchAdapter();
        recyclerView.setAdapter(searchAdapter);

        Hotel_Search.setOnClickListener(new View.OnClickListener() { //파이어베이스에서 값 받아오기!
            @Override
            public void onClick(View v) {
                searchAdapter.init();
                String HOTEL_NAME = name;
                String HOTEL_STATE = address;
                String HOTEL_DETAIL = detail;

                if(HOTEL_NAME == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("먼저 호텔을 등록해주세요.")
                            .setPositiveButton("확인",null)
                            .create()
                            .show();
                }
                else {
                    SearchData searchData = new SearchData(HOTEL_NAME, HOTEL_STATE, HOTEL_DETAIL, userID,"");
                    searchAdapter.addItem(searchData);
                    searchAdapter.notifyDataSetChanged();
                }
            }
        });

        rDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue(String.class);
                if(key.equals("Hotel_name")) {
                    name = value;
                }
                else if (key.equals("Hotel_detail")) {
                    detail = value;
                }
                else if (key.equals("Hotel_address")) {
                    address = value;
                }
                //Toast.makeText(container.getContext(),key +":"+ value.toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                String value = dataSnapshot.getValue(String.class);
                if(key.equals("Hotel_name")) {
                    name = value;
                }
                else if (key.equals("Hotel_detail")) {
                    detail = value;
                }
                else if (key.equals("Hotel_address")) {
                    address = value;
                }

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
