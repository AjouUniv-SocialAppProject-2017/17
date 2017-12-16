package com.pyw.a17.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pyw.a17.R;
import com.pyw.a17.dto.CrawlingDTO;

import java.util.ArrayList;

public class WebCrawlingAdapter extends BaseAdapter{

    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<CrawlingDTO> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public CrawlingDTO getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.webcrawling_listview, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        TextView c_content = (TextView) convertView.findViewById(R.id.contents);
        TextView c_cop = (TextView) convertView.findViewById(R.id.cop);
        TextView c_date = (TextView) convertView.findViewById(R.id.date);

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        CrawlingDTO myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        c_content.setText("주최사: " + myItem.getTitle1());
        c_cop.setText("공모명: " + myItem.getTitle2());
        c_date.setText("기간: " + myItem.getTitle3());

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */

        return convertView;
    }

//    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
//    public void addItem(Drawable img, CrawlingDTO i) {
//
//        ContestItem mItem = new ContestItem();
//
//        /* MyItem에 아이템을 setting한다. */
////        mItem.setIcon(img);
//        mItem.setName(name);
//        mItem.setContents(contents);
//
//        /* mItems에 MyItem을 추가한다. */
//        mItems.add(mItem);
//    }

    public void setmItems(ArrayList<CrawlingDTO> list) {
        this.mItems = list;
    }
}