package com.qinshou.viewmovedemo;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by tubro on 2018/4/27.
 */

public class QuestionAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public QuestionAdapter(List<String> data) {
        super(R.layout.question, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_answer, "_____" + s);
    }
}
