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


public class HostMainActivity extends AppCompatActivity {

    ViewPager pager;
    Bundle payFragmentbundle; //1

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_main);

        pager = (ViewPager)findViewById(R.id.pager);
        Button btn_first = (Button)findViewById(R.id.btn_first2);
        Button btn_second = (Button)findViewById(R.id.btn_second2);
        Button btn_third = (Button)findViewById(R.id.btn_third2);
        Button btn_fourth = (Button)findViewById(R.id.btn_fourth2);
        Intent intent = getIntent();

        payFragmentbundle = new Bundle(2); //2
        payFragmentbundle.putString("userID",intent.getStringExtra("userID")); //2
        payFragmentbundle.putString("email",intent.getStringExtra("email"));

        pagerAdapter pagerAdapter = new pagerAdapter(getSupportFragmentManager());
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

        btn_first.setOnClickListener(movePageListener);
        btn_first.setTag(0);
        btn_second.setOnClickListener(movePageListener);
        btn_second.setTag(1);
        btn_third.setOnClickListener(movePageListener);
        btn_third.setTag(2);
        btn_fourth.setOnClickListener(movePageListener);
        btn_fourth.setTag(3);
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
                    HostCalendar hostCalendar = new HostCalendar();
                    hostCalendar.setArguments(payFragmentbundle);
                    return hostCalendar;
                case 1:
                    HostAdmin hostAdmin = new HostAdmin(); //3
                    hostAdmin.setArguments(payFragmentbundle); //3
                    return hostAdmin; //3
                case 2:
                    HostMsg hostMsg = new HostMsg();
                    hostMsg.setArguments(payFragmentbundle);
                    return hostMsg;
                case 3:
                    HostInfo hostInfo = new HostInfo();
                    hostInfo.setArguments(payFragmentbundle);
                    return hostInfo;
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
