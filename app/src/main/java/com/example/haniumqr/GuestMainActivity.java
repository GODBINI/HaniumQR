package com.example.haniumqr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class GuestMainActivity extends AppCompatActivity {

    ViewPager pager;
    Bundle payFragmentbundle; //1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_main);

        pager = (ViewPager)findViewById(R.id.pager);
        Button btn_first2 = (Button)findViewById(R.id.btn_first2);
        Button btn_second2 = (Button)findViewById(R.id.btn_second2);
        Button btn_third2 = (Button)findViewById(R.id.btn_third2);
        Button btn_fourth2 = (Button)findViewById(R.id.btn_fourth2);
        Intent intent = getIntent();

        payFragmentbundle = new Bundle(1); //2
        payFragmentbundle.putString("userID",intent.getStringExtra("userID")); //2

        GuestMainActivity.pagerAdapter pagerAdapter = new GuestMainActivity.pagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(0);

        View.OnClickListener movePageListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                int tag = (int)view.getTag();
                pager.setCurrentItem(tag);
            }
        };

        btn_first2.setOnClickListener(movePageListener);
        btn_first2.setTag(0);
        btn_second2.setOnClickListener(movePageListener);
        btn_second2.setTag(1);
        btn_third2.setOnClickListener(movePageListener);
        btn_third2.setTag(2);
        btn_fourth2.setOnClickListener(movePageListener);
        btn_fourth2.setTag(3);
    }

    private class pagerAdapter extends FragmentPagerAdapter
    {
        private ArrayList<Fragment> mData = new ArrayList<Fragment>();
        public pagerAdapter(FragmentManager fm )
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position)
            {
                case 0:
                    guest_confirm guestConfirm = new guest_confirm();
                    guestConfirm.setArguments(payFragmentbundle);
                    return guestConfirm;
                case 1:
                    guest_info guestInfo = new guest_info(); //3
                    guestInfo.setArguments(payFragmentbundle); //3
                    return guestInfo; //3
                case 2:
                    guest_Msg guestMsg = new guest_Msg();
                    guestMsg.setArguments(payFragmentbundle);
                    return guestMsg;
                case 3:
                    guest_search guestSearch = new guest_search();
                    guestSearch.setArguments(payFragmentbundle);
                    return guestSearch;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // total page count
            return 4;
        }
    }


}
