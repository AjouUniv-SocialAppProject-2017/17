package com.pyw.a17;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pyw.a17.dto.Post;

/**
 * Created by XNOTE on 2017-11-18.
 */

public class ViewPostActivity extends Board {

    private ViewGroup rootView;
    private TextView postDetailTitle;
    private TextView postDetailWriter;
    private TextView postDetailWriteDate;
    private TextView postDetailContent;
    private LinearLayout postDetailPostWriterMenu;
    private Button postDetailModifyButton;
    private Button postDetailDeleteButton;
    private ImageButton postDetailWriteCommentButton;
    private EditText postDetailWriteComment;
    private TextView postDetailCommentNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        postDetailTitle = (TextView)findViewById(R.id.postDetailTitle);
        postDetailWriter = (TextView)findViewById(R.id.postDetailWriter);
        postDetailWriteDate = (TextView)findViewById(R.id.postDetailWriteDate);
        postDetailContent = (TextView)findViewById(R.id.postDetailContent);
        postDetailPostWriterMenu = (LinearLayout)findViewById(R.id.postDetailPostWriterMenu);
        postDetailModifyButton = (Button)findViewById(R.id.postDetailModifyButton);
        postDetailDeleteButton = (Button)findViewById(R.id.postDetailDeleteButton);
        postDetailWriteCommentButton = (ImageButton)findViewById(R.id.postDetailWriteCommentButton);
        postDetailWriteComment = (EditText)findViewById(R.id.postDetailWriteComment);
        postDetailCommentNumber = (TextView)findViewById(R.id.postDetailCommentNumber);

        Post postItem = (Post)getIntent().getSerializableExtra("post");

        postDetailTitle.setText(postItem.getTitle());
        postDetailWriter.setText(postItem.getWriter());
        postDetailWriteDate.setText(postItem.getWriteDate());
        postDetailContent.setText("\n\n" + postItem.getContent());

        if(!postItem.getWriter().equals("null") && postItem.getWriter().equals(Global.id)) { // 자신의 글일 때

        }else {
            postDetailPostWriterMenu.setVisibility(View.GONE);
        }
    }
}
