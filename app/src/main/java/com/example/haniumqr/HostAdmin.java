package com.example.haniumqr;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class HostAdmin extends Fragment {
    TextView text;//Dialog를 통해 입력된 멤버의 정보를 출력하는 TextView 참조변수
    String str="";//빈 문자열 String 객체

    Button btn_addHotel;
    private DatabaseReference mDatabase;




    public HostAdmin() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_host_admin, container, false);
        LayoutInflater inflaters=getLayoutInflater();
        final View dialogView= inflaters.inflate(R.layout.hotel_admin_dialog, null);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        String userID = getArguments().getString("userID"); //4
        btn_addHotel = (Button)layout.findViewById(R.id.btn_addHotel);
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

        Button Hotel_Search = (Button) layout.findViewById(R.id.hotel_refresh);
        RecyclerView recyclerView = (RecyclerView) layout.findViewById(R.id.view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(layout.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        final SearchAdapter searchAdapter= new SearchAdapter();
        recyclerView.setAdapter(searchAdapter);

        Hotel_Search.setOnClickListener(new View.OnClickListener() { //파이어베이스에서 값 받아오기!
            @Override
            public void onClick(View v) {
                String HOTEL_NAME = "호텔이름";
                String HOTEL_STATE = "호텔주소";
                String HOTEL_DETAIL = "호텔정보";
                SearchData searchData = new SearchData(HOTEL_NAME,HOTEL_STATE,HOTEL_DETAIL);
                searchAdapter.addItem(searchData);
                searchAdapter.notifyDataSetChanged();
            }
        });

        return layout;
    }


}
