package com.pyw.a17;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by XNOTE on 2017-11-08.
 */

public class WritePostActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextContent;
    private Button btnWrite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writepost);

        editTextTitle = (EditText)findViewById(R.id.writepost_title);
        editTextContent = (EditText)findViewById(R.id.writepost_content);
        btnWrite = (Button)findViewById(R.id.writepost_btn);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskWritePost taskWritePost = new TaskWritePost();

                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                String writer = Global.id;
                taskWritePost.execute("post_query", title, content, writer);
            }
        });
    }

    class TaskWritePost extends AsyncTask<String, Void, String> {

        String board;
        String message;
        String title;
        String content;
        String writer;

        @Override
        protected String doInBackground(String... params) {

            board = params[0];
            title = params[1];
            content = params[2];
            writer = params[3];

            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("board", board).add("title", title).add("content", content).add("writer", writer)
                    .build();

            //request
            Request request = new Request.Builder()
                    .url("http://" + Global.IP_ADDRESS + "/SocialApp/WritePost.php")
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

            if(s.equals("1")) {
                Toast.makeText(WritePostActivity.this, "글이 등록되었습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
