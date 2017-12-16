package com.pyw.a17.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pyw.a17.Global;
import com.pyw.a17.R;
import com.pyw.a17.adapter.ProfileVisitAdapter;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_visit, container, false);

        listview = (ListView)rootView.findViewById(R.id.profile_listview);
        adapter = new ProfileVisitAdapter();

        TaskGetPost thread = new TaskGetPost();
        thread.execute(Global.id);

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
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
