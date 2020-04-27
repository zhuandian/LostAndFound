package com.zhuandian.lostandfound.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.business.activity.LostAndFoundDetailActivity;
import com.zhuandian.lostandfound.entity.LostAndFoundEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-27.
 */
public class LoastAndFoundAdapter extends BaseAdapter<LostAndFoundEntity, BaseViewHolder> {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_release_user)
    TextView tvReleaseUser;
    @BindView(R.id.tv_type)
    TextView tvType;

    public LoastAndFoundAdapter(List<LostAndFoundEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder myViewHolder, LostAndFoundEntity lostAndFoundEntity, int position) {
        ButterKnife.bind(this, myViewHolder.itemView);
        tvTitle.setText(lostAndFoundEntity.getTitle());
        tvContent.setText(lostAndFoundEntity.getContent());
        tvTime.setText(lostAndFoundEntity.getCreatedAt());
        tvReleaseUser.setText("发布人：" + lostAndFoundEntity.getUserEntity().getUsername());
        tvType.setText(lostAndFoundEntity.getType() == 1 ? "丢失" : "捡到");
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LostAndFoundDetailActivity.class);
                intent.putExtra("entity",lostAndFoundEntity);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_loast_and_found;
    }
}
