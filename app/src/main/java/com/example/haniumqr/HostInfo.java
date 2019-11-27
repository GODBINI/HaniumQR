package com.example.haniumqr;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HostInfo extends Fragment {


    public HostInfo() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.fragment_host_info, container, false);
        TextView text_Myid = layout.findViewById(R.id.H_Myid);
        TextView  text_Myemail=layout.findViewById(R.id.H_Myemail);

        String userID = getArguments().getString("userID");
        String email=getArguments().getString("email");
        text_Myid.setText(userID);
        text_Myemail.setText(email);
        return layout;
    }



}
