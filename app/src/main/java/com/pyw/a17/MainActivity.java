package com.pyw.a17;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String[] gridColor ={

            "#47b8e0",
            "#47b8e0",
            "#ff7473",
            "#47b8e0",
            "#47b8e0",
            "#ffc952"
    };

    DisplayMetrics mMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this, gridColor));
        gridview.setOnItemClickListener(gridviewOnItemClickListener);
    }

    private GridView.OnItemClickListener gridviewOnItemClickListener
            = new GridView.OnItemClickListener() {

        public void onItemClick(AdapterView<?> adapter, View view, int position,
                                long arg3) {

            switch(position) {
                case 0 : {
                    Intent intent = new Intent(MainActivity.this, QueryBoardActivity.class);
                    startActivity(intent);
                    break;
                }
                case 1 : {
                    Intent intent = new Intent(MainActivity.this, StudyBoardActivity.class);
                    startActivity(intent);
                    break;
                }
                case 2 : {
                    Intent intent = new Intent(MainActivity.this, FreeBoardActivity.class);
                    startActivity(intent);
                    break;
                }
                case 3 : {
                    Intent intent = new Intent(MainActivity.this, MyActivity.class);
                    startActivity(intent);
                    break;
                }
                case 4 : {
                    Intent intent = new Intent(MainActivity.this, CrawlingActivity.class);
                    startActivity(intent);
                    break;
                }
                case 5 : {
                    Intent intent = new Intent(MainActivity.this, CompetitonActivity.class);
                    startActivity(intent);
                    break;
                }
            }
        }
    };

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;
        private String[] gridColor ={};

        public ImageAdapter(Context c, String[] gridColor) {
            mContext = c;
            this.gridColor = gridColor;
        }

        public int getCount() {
            return gridColor.length;
        }

        public Object getItem(int position) {
            return gridColor[position];
        }

        public long getItemId(int position) {
            return position;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {

            View grid;
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null) {
                grid = new View(mContext);
                grid = inflater.inflate(R.layout.grid_layout, null);
                grid.setBackgroundColor(Color.parseColor(gridColor[position]));
            } else {
                grid = convertView;
            }

            TextView textView = grid.findViewById(R.id.textView);
            switch(position) {
                case 0 : {
                    textView.setText("질문게시판");
                    break;
                }
                case 1 : {
                    textView.setText("스터디게시판");
                    break;
                }
                case 2 : {
                    textView.setText("자유게시판");
                    break;
                }
                case 3 : {
                    textView.setText("PR페이지");
                    break;
                }
                case 4 : {
                    textView.setText("학습정보");
                    break;
                }
                case 5 : {
                    textView.setText("공모전게시판");
                    break;
                }
            }
            return grid;
        }
    }
}