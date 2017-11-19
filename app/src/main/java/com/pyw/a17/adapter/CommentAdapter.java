package com.pyw.a17.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pyw.a17.dto.CommentDTO;
import com.pyw.a17.listview.CommentListview;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XNOTE on 2017-05-20.
 */

public class CommentAdapter extends BaseAdapter {

    private Context mContext;
    private List<CommentDTO> commentDTOList = new ArrayList<>();

    public CommentAdapter(Context context) {
        mContext = context;
    }

    public void addItem(CommentDTO commentDTO) {
        commentDTOList.add(commentDTO);
    }

    public void deleteItemByCommentNo(int commentNo) {
        for(CommentDTO commentDTO : commentDTOList) {
            if(commentNo == commentDTO.getNo()) {
                commentDTOList.remove(commentDTO);
                break;
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommentListview commentListview;

        if(convertView == null) {
            commentListview = new CommentListview(mContext, commentDTOList.get(position));
        }else {
            commentListview = (CommentListview) convertView;
        }

        commentListview.setCommentWriter(commentDTOList.get(position).getWriter());
        commentListview.setCommentWriter(commentDTOList.get(position).getWriter());

        commentListview.setCommentContent(commentDTOList.get(position).getContent());
        commentListview.setCommentWriteDate("작성 날짜 : " +commentDTOList.get(position).getWriteDate());

        return commentListview;
    }

    @Override
    public int getCount() {
        return commentDTOList.size();
    }

    @Override
    public Object getItem(int position) {
        return commentDTOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
