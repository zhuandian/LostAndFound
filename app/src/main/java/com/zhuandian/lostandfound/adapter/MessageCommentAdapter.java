package com.zhuandian.lostandfound.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.entity.MessageCommentEntity;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * desc :
 * author：xiedong
 */
public class MessageCommentAdapter extends BaseAdapter<MessageCommentEntity, BaseViewHolder> {

    @BindView(R.id.name)
    TextView commenterName;
    @BindView(R.id.time)
    TextView tvCommentTime;
    @BindView(R.id.content)
    TextView tvContent;

    public MessageCommentAdapter(List<MessageCommentEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder holder, MessageCommentEntity comment, int position) {
        ButterKnife.bind(this, holder.itemView);
        tvCommentTime.setText(comment.getCreatedAt());
        tvContent.setText(comment.getContent());
        commenterName.setText(comment.getUserEntity().getUsername());   //设置评论者信息
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.comment_listview_item;
    }
}
