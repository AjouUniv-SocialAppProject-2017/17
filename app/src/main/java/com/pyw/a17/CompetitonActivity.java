package com.pyw.a17;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.pyw.a17.adapter.WebCrawlingAdapter;
import com.pyw.a17.dto.CrawlingDTO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by XNOTE on 2017-10-12.
 */

public class CompetitonActivity extends Board {
    private String htmlPageUrl = "http://contests.saramin.co.kr/contests/list?cate=11"; //파싱할 홈페이지의 URL주소
    private ListView mListView;

    private ArrayList<CrawlingDTO> list= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);

        toolbarSetting();
        getSupportActionBar().setTitle("공모전");

        mListView = (ListView) findViewById(R.id.post_listview);
        //crawlingDTOArrayList를 받아와야함. onpost에서
        CompetitonActivity.JsoupAsyncTask task = new CompetitonActivity.JsoupAsyncTask();
        task.execute();

    }

    private class JsoupAsyncTask extends AsyncTask< Void, Void, ArrayList<CrawlingDTO>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<CrawlingDTO> doInBackground(Void... params) {
            ArrayList<CrawlingDTO> crawlingDTOArrayList = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(htmlPageUrl).get();

                // tr들 가져오기
                Elements titles = doc.select("tr p");
                Elements contents = doc.select("tr a");
                Elements dates = doc.select("tr td:eq(2)");

                for(int i = 0 ; i < titles.size() ; i ++) {
                    CrawlingDTO c = new CrawlingDTO();
                    list.add(c);
                }
                // title
                for(int j = 0 ; j < titles.size() ; j ++){
                    String cop = titles.get(j).ownText();
                    list.get(j).setTitle1(cop);
                }
                // contents
                for(int j = 0 ; j < contents.size() ; j ++){
                    String link = contents.get(j).ownText();
                    list.get(j).setTitle2(link);
                }
                // date
                for(int j = 0 ; j < dates.size() ; j ++){
                    String day = dates.get(j).ownText();
                    list.get(j).setTitle3(day);
                }
            }

            catch (IOException e) {
                e.printStackTrace();
            }
            return crawlingDTOArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<CrawlingDTO> result) {
            WebCrawlingAdapter adapter = new WebCrawlingAdapter();
         /* 위젯과 멤버변수 참조 획득 */
            adapter.setmItems(list);
            mListView.setAdapter(adapter);
        }
    }
}