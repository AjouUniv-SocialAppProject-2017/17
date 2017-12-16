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
import android.widget.ImageView;
import android.widget.TextView;

import com.pyw.a17.Global;
import com.pyw.a17.QueryBoardActivity;
import com.pyw.a17.R;
import com.pyw.a17.ViewPostActivity;
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
 * Created by XNOTE on 2017-12-16.
 */

public class FragmentMy extends Fragment {

    TextView profile_id;
    TextView profile_lan;
    TextView profile_intro;
    ImageView linearlayout_imageview;

    FrgmentMyInfo frgmentMyInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_my, container, false);

        frgmentMyInfo = new FrgmentMyInfo();

        profile_id = (TextView)rootView.findViewById(R.id.profile_id);
        profile_lan = (TextView)rootView.findViewById(R.id.profile_lan);
        profile_intro = (TextView)rootView.findViewById(R.id.profile_intro);
        linearlayout_imageview = (ImageView)rootView.findViewById(R.id.linearlayout_imageview);

        linearlayout_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, frgmentMyInfo).commit();
            }
        });

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
                    .add("username", board)
                    .build();

            //request
            Request request = new Request.Builder()
                    .url("http://" + Global.IP_ADDRESS + "/SocialApp/GetMy.php")
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

                    String username = jsonObj.getString("username");
                    String lan1 = jsonObj.getString("lan1");
                    String lan2 = jsonObj.getString("lan2");
                    String lan3 = jsonObj.getString("lan3");
                    String intro = jsonObj.getString("intro");

                    profile_id.setText(username);
                    profile_lan.setText(lan1 + " | " + lan2 + " | " + lan3);
                    profile_intro.setText(intro);
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
