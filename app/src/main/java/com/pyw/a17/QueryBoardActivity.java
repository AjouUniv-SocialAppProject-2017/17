package com.pyw.a17;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by XNOTE on 2017-10-12.
 */

public class QueryBoardActivity extends Board {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queryboard);

        toolbarSetting();
    }
}
