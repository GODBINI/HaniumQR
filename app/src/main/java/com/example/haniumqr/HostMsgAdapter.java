package com.example.haniumqr;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HostMsgAdapter extends RecyclerView.Adapter<HostMsgAdapter.ItemViewHolder> {
    // adapter에 들어갈 list 입니다.
    private ArrayList<ApplyMsgData> listData = new ArrayList<>();

    @NonNull
    @Override
    public HostMsgAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apply_msg, parent, false);

        return new HostMsgAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HostMsgAdapter.ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(ApplyMsgData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    void init() {
        listData.clear();
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView apply_userID_text;
        private TextView apply_hotelname_text;
        private Button apply_reject_button;
        private Button apply_ok_button;
        private DatabaseReference mDatabase;
        private String hostID;


        ItemViewHolder(View itemView) {
            super(itemView);

            apply_userID_text = (TextView)itemView.findViewById(R.id.apply_userID_text);
            apply_hotelname_text = (TextView)itemView.findViewById(R.id.apply_hotel_text);
            apply_reject_button = (Button)itemView.findViewById(R.id.apply_reject_button);
            apply_ok_button = (Button)itemView.findViewById(R.id.apply_ok_button);
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }

        void onBind(ApplyMsgData data) {
            apply_userID_text.setText("예약자 : "+data.getUserID());
            apply_hotelname_text.setText("호텔명 : "+data.getHotel_name());
            hostID = data.getHostID();
            apply_reject_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendMsgData sendMsgData = new SendMsgData(hostID,"예약이 거절되었습니다.");
                    mDatabase.child("Msg").child(data.getUserID()).setValue(sendMsgData);
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("거절하였습니다.")
                            .setPositiveButton("확인",null)
                            .create()
                            .show();
                }
            });
            apply_ok_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SendMsgData sendMsgData = new SendMsgData(hostID,"예약 승인완료. 나의 예약을 확인하세요.");
                    mDatabase.child("Msg").child(data.getUserID()).setValue(sendMsgData);
                    mDatabase.child("QR").child(data.getUserID()).setValue(hostID+"/"+data.getHotel_name()+"/"+data.getUserID());
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("승인하였습니다.")
                            .setPositiveButton("확인",null)
                            .create()
                            .show();
                }
            });
        }
    }
}
