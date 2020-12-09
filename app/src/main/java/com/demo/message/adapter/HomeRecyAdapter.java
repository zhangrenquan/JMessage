package com.demo.message.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.message.R;
import com.demo.message.enity.MessageBean;

import java.util.List;

public class HomeRecyAdapter extends BaseMultiItemQuickAdapter<MessageBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeRecyAdapter(List<MessageBean> data) {
        super(data);
        addItemType(MessageBean.LEFT, R.layout.text_left);
        addItemType(MessageBean.RIGHT, R.layout.text_right);
    }

    public void reflush(){
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        switch (helper.getItemViewType()) {
            case MessageBean.LEFT:
                helper.setText(R.id.item_message_left, item.getMessage());
                break;
            case MessageBean.RIGHT:
                helper.setText(R.id.item_message_right, item.getMessage());
                break;
        }
    }
}
