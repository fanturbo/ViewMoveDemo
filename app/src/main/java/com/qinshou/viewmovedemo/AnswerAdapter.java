package com.qinshou.viewmovedemo;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by tubro on 2018/4/27.
 */

public class AnswerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public AnswerAdapter(List<String> data) {
        super(R.layout.answer, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_answer, s);
    }
}
