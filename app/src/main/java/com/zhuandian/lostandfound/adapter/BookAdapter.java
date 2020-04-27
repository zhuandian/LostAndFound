package com.zhuandian.lostandfound.adapter;

import android.content.Context;
import android.widget.TextView;

import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.entity.BookEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-27.
 */
public class BookAdapter extends BaseAdapter<BookEntity, BaseViewHolder> {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_release_user)
    TextView tvReleaseUser;

    public BookAdapter(List<BookEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder myViewHolder, BookEntity bookEntity, int position) {
        ButterKnife.bind(this, myViewHolder.itemView);
        tvTitle.setText(bookEntity.getTitle());
        tvContent.setText(bookEntity.getContent());
        tvTime.setText(bookEntity.getCreatedAt());
        tvReleaseUser.setText("发布人：" + bookEntity.getUserEntity().getUsername());
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_book;
    }
}