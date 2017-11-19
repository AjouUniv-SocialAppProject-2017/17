package com.pyw.a17.listview;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pyw.a17.Global;
import com.pyw.a17.R;
import com.pyw.a17.dto.CommentDTO;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by XNOTE on 2017-05-20.
 */

public class CommentListview extends RelativeLayout {

    private TextView commentWriter;
    private TextView commentContent;
    private TextView commentWriteDate;
    private ImageView commentEmoticon;
    private ImageView commentDelete;

    public CommentListview(Context context, CommentDTO commentDTO) {
        super(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.comment_listview, this, true);

        commentWriter = (TextView)findViewById(R.id.commentWriter);
        commentWriter.setText(commentDTO.getWriter());
        commentContent = (TextView)findViewById(R.id.commentContent);
        commentContent.setText(commentDTO.getContent());
        commentWriteDate = (TextView)findViewById(R.id.commentWriteDate);
        commentWriteDate.setText(commentDTO.getWriteDate());
        commentEmoticon = (ImageView)findViewById(R.id.commentEmoticon);
        commentEmoticon.setTag(commentDTO.getNo()); // 댓글 수정, 삭제에 필요한 댓글 번호

        commentDelete = (ImageView)findViewById(R.id.commentDelete);

        // 해당 댓글의 작성자의 경우 수정, 삭제 버튼을 보여줌
        String id = Global.id;
        if(id.equals(commentDTO.getWriter())) {

        }else {
            commentDelete.setAlpha(0);
            commentDelete.setEnabled(false);
        }

        commentDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("알림");
                builder.setMessage("해당 댓글을 삭제하시겠습니까?");
                builder.setPositiveButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // db 접속 후 해당 댓글 삭제
                        new DeleteCommentAsyncTaskClass().execute(commentEmoticon.getTag().toString());
                    }
                });
                builder.show();
            }
        });
    }

    public void setCommentWriter(String writer) {
        this.commentWriter.setText(writer);
    }

    public void setCommentContent(String content) {
        this.commentContent.setText(content);
    }

    public void setCommentWriteDate(String writeDate) {
        this.commentWriteDate.setText(writeDate);
    }

    class DeleteCommentAsyncTaskClass extends AsyncTask<String, Void, String> {

        String commentNo;
        String message;

        @Override
        protected String doInBackground(String... params) {

            commentNo = params[0];

            Log.i("comment No", commentNo);

            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormBody.Builder()
                    .add("commentNo", commentNo)
                    .build();

            //request
            Request request = new Request.Builder()
                    .url("http://" + Global.IP_ADDRESS + "/Project/deleteComment.php")
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
            Log.i("delete Comment Result", s);

            if(s.equals("1")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("알림");
                builder.setMessage("해당 댓글이 삭제되었습니다.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        main.listViewRefresh(CommentListview.this, commentEmoticon.getTag().toString());
                    }
                });
                builder.show();


            }else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("알림");
                builder.setMessage("삭제에 실패하였습니다.");
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        }
    }
}
