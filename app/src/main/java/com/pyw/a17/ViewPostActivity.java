package com.pyw.a17;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.pyw.a17.adapter.CommentAdapter;
import com.pyw.a17.dto.CommentDTO;
import com.pyw.a17.dto.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

    // Comment
    private ListView postCommentListview;
    private CommentAdapter adapter;

    private Post postItem;
    private String board;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        postDetailTitle = (TextView)findViewById(R.id.postDetailTitle);
        postDetailWriter = (TextView)findViewById(R.id.postDetailWriter);
        postDetailWriteDate = (TextView)findViewById(R.id.postDetailWriteDate);
        postDetailContent = (TextView)findViewById(R.id.postDetailContent);
        postDetailPostWriterMenu = (LinearLayout)findViewById(R.id.postDetailPostWriterMenu);
        postCommentListview = (ListView)findViewById(R.id.postDetailCommentListView);
        postDetailModifyButton = (Button)findViewById(R.id.postDetailModifyButton);
        postDetailDeleteButton = (Button)findViewById(R.id.postDetailDeleteButton);
        postDetailWriteCommentButton = (ImageButton)findViewById(R.id.postDetailWriteCommentButton);
        postDetailWriteComment = (EditText)findViewById(R.id.postDetailWriteComment);
        postDetailCommentNumber = (TextView)findViewById(R.id.postDetailCommentNumber);

        postItem = (Post)getIntent().getSerializableExtra("post");
        board = (String)getIntent().getStringExtra("board");

        postDetailTitle.setText(postItem.getTitle());
        postDetailWriter.setText(postItem.getWriter());
        postDetailWriteDate.setText(postItem.getWriteDate());
        postDetailContent.setText("\n\n" + postItem.getContent());

        if(!postItem.getWriter().equals("null") && postItem.getWriter().equals(Global.id)) { // 자신의 글일 때

        }else {
            postDetailPostWriterMenu.setVisibility(View.GONE);
        }

        postDetailWriteCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // DB 접속해서 postNo, id, 글내용 넘겨주기
                int postNo = postItem.getNo();
                String id = Global.id;
                String content = postDetailWriteComment.getText().toString();
                if(content.trim().equals("") || content == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewPostActivity.this.getApplicationContext());
                    builder.setTitle("알림");
                    builder.setMessage("내용을 입력해 주세요.");
                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }

                postDetailWriteComment.setText("");
                new WriteCommentAsyncTaskClass().execute(postNo + "", id, content, board);
            }
        });


        // 댓글 로딩 작업
        new LoadCommentAsyncTaskClass().execute(postItem.getNo() + "", board);
    }

    // 댓글 로딩 작업
    class LoadCommentAsyncTaskClass extends AsyncTask<String, Void, String> {

        String postNo;
        String message;
        String board;

        @Override
        protected String doInBackground(String... params) {

            postNo = params[0];
            board = params[1] + "_comment";
            Log.i("post No", postNo);

            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("postNo", postNo)
                    .add("board", board)
                    .build();

            //request
            Request request = new Request.Builder()
                    .url("http://" + Global.IP_ADDRESS + "/SocialApp/LoadingComment.php")
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                message = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            adapter = new CommentAdapter(ViewPostActivity.this.getApplication());

            try {
                JSONArray jsonArray = new JSONArray(s);
                for(int i = 0 ; i < jsonArray.length() ; i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int no = jsonObject.getInt("no");
                    String content = jsonObject.getString("content");
                    String writer = jsonObject.getString("writer");
                    String writeDate = jsonObject.getString("write_date");

                    CommentDTO commentDTO = new CommentDTO(content, no, writeDate, writer);
                    // CommentAdapter에 addItem()하기
                    adapter.addItem(commentDTO);
                }
                postCommentListview.setAdapter(adapter);
                setListViewHeightBasedOnChildren(postCommentListview);

                // 댓글 개수 로딩
                setCommentCount();
            }catch(Exception e) {
                e.printStackTrace();
            }

        }
    }

    // CommentListview의 높이를 자동으로 조절
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        CommentAdapter listAdapter = (CommentAdapter)listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }

    public void setCommentCount() {
        postDetailCommentNumber.setText("(" + adapter.getCount() + ")");
    }

    // 댓글 추가하기 작업
    class WriteCommentAsyncTaskClass extends AsyncTask<String, Void, String> {

        String postNo;
        String id;
        String content;
        String writeDate;
        String message;
        String board;

        @Override
        protected String doInBackground(String... params) {

            postNo = params[0];
            id = params[1];
            content = params[2];
            board = params[3] + "_comment";

            Date ct = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            writeDate = simpleDateFormat.format(ct);

            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("content", content)
                    .add("writer", id)
                    .add("writeDate", writeDate)
                    .add("postNo", postNo)
                    .add("board", board)
                    .build();

            //request
            Request request = new Request.Builder()
                    .url("http://" + Global.IP_ADDRESS + "/SocialApp/WriteComment.php")
                    .post(body)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                message = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s.equals("1")) {
                // String content, int no, int postNo, String professor, String writeDate, String writer
                CommentDTO addedCommentDTO;

                addedCommentDTO = new CommentDTO(content, 0, writeDate, Global.id);

                adapter.addItem(addedCommentDTO);
                postCommentListview.setAdapter(adapter);
                setListViewHeightBasedOnChildren(postCommentListview);

                setCommentCount();
            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewPostActivity.this.getApplicationContext());
                builder.setTitle("알림");
                builder.setMessage("댓글을 추가하는데 실패하였습니다.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        }
    }
}
