package com.pyw.a17;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.pyw.a17.adapter.PostAdapter;
import com.pyw.a17.dto.Post;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by XNOTE on 2017-10-12.
 */

public class StudyBoardActivity extends Board {

    private ListView listView;
    private PostAdapter adapter;

    private Button writePostBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studyboard);

        toolbarSetting();
        getSupportActionBar().setTitle("스터디게시판");
        writePostBtn = (Button)findViewById(R.id.write_post_btn);
        writePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudyBoardActivity.this, WritePostActivity.class);
                intent.putExtra("board_kind", "post_study");
                startActivityForResult(intent, Global.WRITE_REQUEST_CODE);
            }
        });

        listView = (ListView)findViewById(R.id.post_listview);
        adapter = new PostAdapter();

        TaskGetPost taskGetPost = new TaskGetPost();
        taskGetPost.execute("post_study");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK) {
            return;
        }

        if(requestCode == Global.WRITE_REQUEST_CODE) {
            adapter.empty();
            TaskGetPost taskGetPost = new TaskGetPost();
            taskGetPost.execute("post_study");
        }
    }

    class TaskGetPost extends AsyncTask<String, Void, String> {

        String board;
        String message;

        @Override
        protected String doInBackground(String... params) {

            board = params[0];

            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("board", board)
                    .build();

            //request
            Request request = new Request.Builder()
                    .url("http://" + Global.IP_ADDRESS + "/SocialApp/GetPost.php")
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
            Log.i("message", s);

            try {
                JSONArray jsonArr = new JSONArray(s);
                for(int i = 0 ; i < jsonArr.length() ; i++) {
                    JSONObject jsonObj = jsonArr.getJSONObject(i);

                    int no = jsonObj.getInt("no");
                    String title = jsonObj.getString("title");
                    String content = jsonObj.getString("content");
                    String writeDate = jsonObj.getString("write_date");
                    String writer = jsonObj.getString("writer");
                    String category = jsonObj.getString("category");

                    Post PostDTO = new Post(no, title, content, writeDate, writer, category);
                    adapter.addItem(PostDTO);
                    listView.setAdapter(adapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Post post = (Post)adapter.getItem(position);

                            Intent intent = new Intent(StudyBoardActivity.this, ViewPostActivity.class);
                            intent.putExtra("post", post);
                            intent.putExtra("board", "post_study");
                            startActivity(intent);
                        }
                    });
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
