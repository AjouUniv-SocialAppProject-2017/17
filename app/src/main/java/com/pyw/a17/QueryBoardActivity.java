package com.pyw.a17;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.pyw.a17.adapter.PostAdapter;
import com.pyw.a17.dto.Post;

/**
 * Created by XNOTE on 2017-10-12.
 */

public class QueryBoardActivity extends Board {

    private ListView listView;
    private PostAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryboard);

        toolbarSetting();

        listView = (ListView)findViewById(R.id.post_listview);
        adapter = new PostAdapter();

        Post post1 = new Post("첫 글입니다", "첫 글의 내용입니다", "2017.11.8", "pyw123", "30");
        Post post2 = new Post("두번째 글입니다", "두번째 글의 내용입니다", "2017.11.8", "pyw123", "18");
        Post post3 = new Post("세번째 글입니다", "세번째 글의 내용입니다", "2017.11.8", "pyw123", " 30");

        adapter.addItem(post1);
        adapter.addItem(post2);
        adapter.addItem(post3);

        listView.setAdapter(adapter);
    }
}
