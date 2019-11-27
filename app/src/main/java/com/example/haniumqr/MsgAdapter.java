package com.example.haniumqr;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MsgAdapter extends RecyclerView.Adapter <MsgAdapter.ItemViewHolder>{
    // adapter에 들어갈 list 입니다.
    private ArrayList<GuestMsgData> listData = new ArrayList<>();

    @NonNull
    @Override
    public MsgAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.guest_msg, parent, false);

        return new MsgAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MsgAdapter.ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(GuestMsgData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    void init() {
        listData.clear();
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView guest_msgID;
        private TextView guest_msg;



        ItemViewHolder(View itemView) {
            super(itemView);

            guest_msgID = (TextView)itemView.findViewById(R.id.guest_msgID);
            guest_msg = (TextView)itemView.findViewById(R.id.guest_msg);

        }

        void onBind(GuestMsgData data) {
            guest_msgID.setText(data.getMsgID());
            guest_msg.setText(data.getMsg());
        }
    }
}
