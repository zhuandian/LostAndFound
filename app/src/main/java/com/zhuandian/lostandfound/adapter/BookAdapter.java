package com.zhuandian.lostandfound.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.entity.BookEntity;
import com.zhuandian.lostandfound.entity.UserEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

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

        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                UserEntity currentUser = BmobUser.getCurrentUser(UserEntity.class);
                if (currentUser.getType() == 1||currentUser.getObjectId() .equals(bookEntity.getUserEntity().getObjectId()) ) {
                    bookEntity.delete(new UpdateListener() {
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
        return R.layout.item_book;
    }
}
