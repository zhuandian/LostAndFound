package com.zhuandian.lostandfound.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.base.BaseAdapter;
import com.zhuandian.base.BaseViewHolder;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.entity.UserEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author xiedong
 * @desc
 * @date 2020-04-28.
 */
public class ManageUserAdapter extends BaseAdapter<UserEntity, BaseViewHolder> {
    @BindView(R.id.tv_user_name)
    TextView tvUserName;

    public ManageUserAdapter(List<UserEntity> mDatas, Context context) {
        super(mDatas, context);
    }

    @Override
    protected void converData(BaseViewHolder myViewHolder, UserEntity userEntity, int position) {
        ButterKnife.bind(this, myViewHolder.itemView);
        tvUserName.setText("用户：" + userEntity.getUsername());


        tvUserName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                BmobUser.loginByAccount(userEntity.getUsername(), userEntity.getUserPassword(), new LogInListener<UserEntity>() {
                    @Override
                    public void done(UserEntity userEntity, BmobException e) {
                        if (e == null) {
                            userEntity.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {


                                        SharedPreferences sharedPreferences = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
                                        String currentUserName = sharedPreferences.getString("userName","");
                                        String currentUserPassword = sharedPreferences.getString("password","");

                                        BmobUser.loginByAccount(currentUserName, currentUserPassword, new LogInListener<UserEntity>() {
                                            @Override
                                            public void done(UserEntity userEntity, BmobException e) {
                                                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });

                return true;
            }
        });
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.item_manager_user;
    }
}
