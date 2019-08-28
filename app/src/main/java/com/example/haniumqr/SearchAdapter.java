package com.example.haniumqr;

import android.content.Intent;
import android.graphics.Bitmap;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<SearchData> listData = new ArrayList<>();

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {



        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.host_view_hotel, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));
    }

    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(SearchData data) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(data);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.
    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView hotel_name;
        private TextView hotel_address;
        private TextView hotel_detail;
        private Button reservation;



        ItemViewHolder(View itemView) {
            super(itemView);

            hotel_name = itemView.findViewById(R.id.hotel_name);
            hotel_address = itemView.findViewById(R.id.hotel_address);
            hotel_detail=itemView.findViewById(R.id.hotel_detail);
            reservation = itemView.findViewById(R.id.reservation);
        }

        void onBind(SearchData data) {
            hotel_name.setText(data.getHotel_name());
            hotel_address.setText(data.getHotel_address());
            hotel_detail.setText(data.getHotel_detail());
            reservation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }
}