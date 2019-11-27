package com.example.haniumqr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MsgWriteActivity extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference mDatabase;
    Button MsgSendButton;
    Button MsgCancelButton;
    EditText ReceiverText;
    EditText MsgText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg_write);

        String userID = getIntent().getStringExtra("userID");
        MsgSendButton = (Button)findViewById(R.id.MsgSendButton);
        MsgCancelButton = (Button)findViewById(R.id.MsgCancelButton);
        ReceiverText = (EditText)findViewById(R.id.receiverText);
        MsgText = (EditText)findViewById(R.id.MsgText);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mDatabase = firebaseDatabase.getReference();

        MsgCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        MsgSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receiver = ReceiverText.getText().toString();
                String msg = MsgText.getText().toString();
                if(receiver.trim().equals("")) {
                    Toast.makeText(MsgWriteActivity.this,"수신자를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else if(msg.trim().equals("")) {
                    Toast.makeText(MsgWriteActivity.this,"메세지 내용을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    SendMsgData sendMsgData = new SendMsgData(userID,msg);
                    mDatabase.child("Msg").child(receiver).setValue(sendMsgData);
                    AlertDialog.Builder builder = new AlertDialog.Builder(MsgWriteActivity.this);
                    builder.setMessage("메세지 전송 성공")
                            .setPositiveButton("확인",null)
                            .create()
                            .show();
                }
            }
        });
    }
}
