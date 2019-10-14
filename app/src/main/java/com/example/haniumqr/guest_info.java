package com.example.haniumqr;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class guest_info extends Fragment {




    public guest_info() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_guest_info, container, false);
        TextView text_Myid = layout.findViewById(R.id.Myid);
        TextView  text_Myemail=layout.findViewById(R.id.Myemail);

        String userID = getArguments().getString("userID");
        String email=getArguments().getString("email");
        text_Myid.setText(userID);
        text_Myemail.setText(email);

        return layout;
    }

}
