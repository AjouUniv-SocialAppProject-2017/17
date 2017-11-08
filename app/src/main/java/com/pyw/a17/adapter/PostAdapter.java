package com.pyw.a17.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pyw.a17.R;
import com.pyw.a17.dto.Post;

import java.util.ArrayList;

/**
 * Created by XNOTE on 2017-11-08.
 */

public class PostAdapter extends BaseAdapter {

    ArrayList<Post> list = new ArrayList<>();

    TextView textViewTitle;
    TextView textViewContent;
    TextView textViewDate;
    TextView textViewWriter;
    TextView textViewReply;

    public PostAdapter() {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.board_post_listview, viewGroup, false);
        }

        textViewTitle = convertView.findViewById(R.id.post_title);
        textViewContent = convertView.findViewById(R.id.post_content);
        textViewDate = convertView.findViewById(R.id.post_date);
        textViewWriter = convertView.findViewById(R.id.post_writer);
        textViewReply = convertView.findViewById(R.id.post_reply);

        textViewTitle.setText(list.get(i).getTitle());
        textViewContent.setText(list.get(i).getContent());
        textViewDate.setText(list.get(i).getDate());
        textViewWriter.setText(list.get(i).getWriter());
        textViewReply.setText(list.get(i).getReply());

        return convertView;
    }

    public void addItem(Post post) {
        list.add(post);
    }
}
