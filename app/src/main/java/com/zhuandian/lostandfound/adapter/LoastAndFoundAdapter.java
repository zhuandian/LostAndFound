package com.zhuandian.lostandfound.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.business.activity.LostAndFoundDetailActivity;
import com.zhuandian.lostandfound.entity.LostAndFoundEntity;
import com.zhuandian.lostandfound.entity.UserEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

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
    @BindView(R.id.tv_like_count)
    TextView tvLikeCount;
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
        tvLikeCount.setText("点赞数：" + lostAndFoundEntity.getLikeCount());
        tvType.setText(lostAndFoundEntity.getType() == 1 ? "丢失" : "捡到");
        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, LostAndFoundDetailActivity.class);
                intent.putExtra("entity", lostAndFoundEntity);
                mContext.startActivity(intent);
            }
        });

        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                UserEntity currentUser = BmobUser.getCurrentUser(UserEntity.class);
                if (currentUser.getType() == 1||currentUser.getObjectId() .equals(lostAndFoundEntity.getUserEntity().getObjectId()) ) {
                    lostAndFoundEntity.delete(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(mContext, "删除成功...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(mContext, "无删除权限...", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });


    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_loast_and_found;
    }
}
