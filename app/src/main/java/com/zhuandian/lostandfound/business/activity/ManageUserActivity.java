package com.zhuandian.lostandfound.business.activity;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.adapter.LoastAndFoundAdapter;
import com.zhuandian.lostandfound.adapter.ManageUserAdapter;
import com.zhuandian.lostandfound.business.utils.BaseRecyclerView;
import com.zhuandian.lostandfound.entity.LostAndFoundEntity;
import com.zhuandian.lostandfound.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ManageUserActivity extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.brv_list)
    BaseRecyclerView brvList;
    private List<UserEntity> mDatas = new ArrayList<>();
    private ManageUserAdapter manageUserAdapter;
    private int currentCount = -10;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_user;
    }

    @Override
    protected void setUpView() {
        tvTitle.setText("管理用户");
        manageUserAdapter = new ManageUserAdapter(mDatas, this);
        brvList.setRecyclerViewAdapter(manageUserAdapter);
        loadDatas();
        initRefreshListener();
    }
    private void initRefreshListener() {
        brvList.setRefreshListener(new BaseRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentCount = -10; //重新置位
                mDatas.clear();
                manageUserAdapter.notifyDataSetChanged();
                loadDatas();

            }
        });
        brvList.setLoadMoreListener(new BaseRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadDatas();
            }
        });


    }


    private void loadDatas() {
        currentCount = currentCount + 10;
        BmobQuery<UserEntity> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.order("-updatedAt");
        query.addWhereNotEqualTo("type", 1);
        query.addWhereNotEqualTo("isUserAvaiable", 1);
        query.setLimit(10);
        query.setSkip(currentCount);
        query.findObjects(new FindListener<UserEntity>() {
            @Override
            public void done(List<UserEntity> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        mDatas.add(list.get(i));
                    }
                    manageUserAdapter.notifyDataSetChanged();
                    brvList.setRefreshLayoutState(false);
                } else {
                    brvList.setRefreshLayoutState(false);
                }
            }
        });
    }


    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
