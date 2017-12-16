package com.pyw.a17.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.pyw.a17.Global;
import com.pyw.a17.MyActivity;
import com.pyw.a17.R;
import com.pyw.a17.StudyBoardActivity;
import com.pyw.a17.ViewPostActivity;
import com.pyw.a17.WritePostActivity;
import com.pyw.a17.adapter.ProfileVisitAdapter;
import com.pyw.a17.dto.Post;
import com.pyw.a17.dto.VisitDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by XNOTE on 2017-12-16.
 */

public class FrgmentMyVisit extends Fragment {

    ListView listview;
    ProfileVisitAdapter adapter;
    Button writevisit_button;
    EditText edittext_v;
    LinearLayout linearlayout;
    String who = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_visit, container, false);

        if(getArguments() != null) {
            who = getArguments().getString("who");
            if(who == null || who.equals("")) {
                who = Global.id;
            }
        }else {
            who = Global.id;
        }

        listview = (ListView)rootView.findViewById(R.id.profile_listview);
        adapter = new ProfileVisitAdapter();
        linearlayout = (LinearLayout)rootView.findViewById(R.id.linearlayout);
        if(who.equals(Global.id)) {
            linearlayout.setVisibility(View.GONE);
        }else {
            writevisit_button = (Button)rootView.findViewById(R.id.writevisit_button);
            writevisit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TaskGetPostWrite thread = new TaskGetPostWrite();
                    thread.execute("안녕하세요", edittext_v.getText().toString(), Global.id, who);
                }
            });
            edittext_v = (EditText)rootView.findViewById(R.id.edittext_v);
        }

        TaskGetPost thread = new TaskGetPost();
        thread.execute(who);

        return rootView;
    }

    class TaskGetPost extends AsyncTask<String, Void, String> {

        String board;
        String message;

        @Override
        protected String doInBackground(String... params) {

            board = params[0];

            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("who", board)
                    .build();

            //request
            Request request = new Request.Builder()
                    .url("http://" + Global.IP_ADDRESS + "/SocialApp/GetVisit.php")
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

                    String title = jsonObj.getString("title");
                    String content = jsonObj.getString("content");
                    String username = jsonObj.getString("username");

                    VisitDTO visitDTO = new VisitDTO(title, content, username, Global.id);
                    adapter.addItem(visitDTO);

                    listview.setAdapter(adapter);
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            VisitDTO visitDTO = (VisitDTO)adapter.getItem(position);
                            String who = visitDTO.getUsername();

                            Intent intent = new Intent(getActivity(), MyActivity.class);
                            intent.putExtra("who", who);
                            startActivity(intent);
                        }
                    });
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    class TaskGetPostWrite extends AsyncTask<String, Void, String> {

        String title;
        String content;
        String username;
        String who;
        String message;

        @Override
        protected String doInBackground(String... params) {

            title = params[0];
            content = params[1];
            username = params[2];
            who = params[3];

            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("title", title)
                    .add("content", content)
                    .add("username", username)
                    .add("who", who)
                    .build();

            //request
            Request request = new Request.Builder()
                    .url("http://" + Global.IP_ADDRESS + "/SocialApp/WriteVisit.php")
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
                Toast.makeText(getActivity(), "글이 등록되었습니다", Toast.LENGTH_SHORT).show();
                adapter.empty();
                TaskGetPost thread = new TaskGetPost();
                thread.execute(who);
                edittext_v.setText("");
            }
        }
    }
}
