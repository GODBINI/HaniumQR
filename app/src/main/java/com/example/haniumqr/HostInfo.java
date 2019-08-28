package com.example.haniumqr;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class HostInfo extends Fragment {


    public HostInfo() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String userID = getArguments().getString("userID");
        RelativeLayout layout = (RelativeLayout)inflater.inflate(R.layout.fragment_host_info,

                container, false);
        return layout;
    }



}