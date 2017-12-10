package com.pyw.a17;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.pyw.a17.fragment.Fragment1;

/**
 * Created by XNOTE on 2017-10-12.
 */

public class CrawlingActivity extends Board {
    Fragment1 fragment1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freeboard);

        toolbarSetting();
        getSupportActionBar().setTitle("학습정보");

        fragment1 = new Fragment1();

        String title = "C";
        Bundle bundle = new Bundle();
        bundle.putString("menu_title", title);
        fragment1.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("Ruby"));
        tabs.addTab(tabs.newTab().setText("AWS"));
        tabs.addTab(tabs.newTab().setText("WEB"));
        tabs.addTab(tabs.newTab().setText("Python"));
        tabs.addTab(tabs.newTab().setText("C/C++"));
        tabs.setTabTextColors(R.color.submain_2, R.color.submain_2);

        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Fragment selected = new Fragment1();

                String title = tab.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("menu_title", title);

                selected.setArguments(bundle);

                getSupportFragmentManager().beginTransaction().replace(R.id.container, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }
}
