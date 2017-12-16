package com.pyw.a17;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.pyw.a17.fragment.FragmentMy;
import com.pyw.a17.fragment.FrgmentMyVisit;

/**
 * Created by XNOTE on 2017-10-12.
 */

public class MyActivity extends Board {

    FragmentMy fragmentMy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        toolbarSetting();
        getSupportActionBar().setTitle("마이페이지");

        fragmentMy = new FragmentMy();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentMy).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("내정보"));
        tabs.addTab(tabs.newTab().setText("방명록"));
        tabs.setTabTextColors(R.color.submain_2, R.color.submain_2);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                int pos = tab.getPosition();

                Fragment selected;

                if(pos == 0) {
                    selected = new FragmentMy();
                }else {
                    selected = new FrgmentMyVisit();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
}
