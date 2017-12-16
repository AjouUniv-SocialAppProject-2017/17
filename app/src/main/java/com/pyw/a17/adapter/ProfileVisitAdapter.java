package com.pyw.a17.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pyw.a17.R;
import com.pyw.a17.dto.VisitDTO;

import java.util.ArrayList;

/**
 * Created by XNOTE on 2017-12-16.
 */

public class ProfileVisitAdapter extends BaseAdapter {

    ArrayList<VisitDTO> list = new ArrayList<>();

    TextView title;
    TextView content;
    TextView username;

    public ProfileVisitAdapter() {

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
            convertView = inflater.inflate(R.layout.profile_listview, viewGroup, false);
        }

        title = convertView.findViewById(R.id.title);
        content = convertView.findViewById(R.id.content);
        username = convertView.findViewById(R.id.username);

        title.setText(list.get(i).getTitle());
        content.setText(list.get(i).getContent());
        username.setText(list.get(i).getUsername());

        return convertView;
    }

    public void addItem(VisitDTO visit) {
        list.add(visit);
    }

    public void empty() {
        list.clear();
    }
}
