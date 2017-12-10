package com.pyw.a17;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class WritePostActivity extends Board {

    private EditText editTextTitle;
    private EditText editTextContent;
    private Button btnWrite;
    private Spinner categorySpinner;

    private String boardKind;
    private ArrayAdapter<CharSequence> sAdapter;

    private String category;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writepost);

        toolbarSetting();
        getSupportActionBar().setTitle("글쓰기");

        editTextTitle = (EditText)findViewById(R.id.writepost_title);
        editTextContent = (EditText)findViewById(R.id.writepost_content);
        btnWrite = (Button)findViewById(R.id.writepost_btn);
        categorySpinner = (Spinner)findViewById(R.id.category_spinner);


        boardKind = (String)getIntent().getStringExtra("board_kind");

        if(boardKind.equals("post_query")) {
            sAdapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_dropdown_item);
        }else if(boardKind.equals("post_study")){
            sAdapter = ArrayAdapter.createFromResource(this, R.array.category_study, android.R.layout.simple_spinner_dropdown_item);
        }else {
            sAdapter = ArrayAdapter.createFromResource(this, R.array.category_free, android.R.layout.simple_spinner_dropdown_item);
        }

        categorySpinner.setAdapter(sAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                category = sAdapter.getItem(position).toString();
            }
            public void onNothingSelected(AdapterView  parent) {
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TaskWritePost taskWritePost = new TaskWritePost();

                String title = editTextTitle.getText().toString();
                String content = editTextContent.getText().toString();
                String writer = Global.id;
                taskWritePost.execute(boardKind, title, content, writer, category);
            }
        });
    }

    class TaskWritePost extends AsyncTask<String, Void, String> {

        String board;
        String message;
        String title;
        String content;
        String writer;
        String category;

        @Override
        protected String doInBackground(String... params) {

            board = params[0];
            title = params[1];
            content = params[2];
            writer = params[3];
            category = "[" + params[4] + "]";

            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("board", board).add("title", title).add("content", content).add("writer", writer).add("category", category)
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
                setResult(RESULT_OK);
                finish();
            }
        }
    }
}
