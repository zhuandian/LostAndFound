package com.zhuandian.lostandfound.business.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhuandian.base.BaseFragment;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.adapter.LoastAndFoundAdapter;
import com.zhuandian.lostandfound.business.activity.ReleaseLostAndFoundActivity;
import com.zhuandian.lostandfound.business.utils.BaseRecyclerView;
import com.zhuandian.lostandfound.entity.LostAndFoundEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;


/**
 * desc :
 * author：xiedong
 * date：2020/03/21
 */
public class FoundFragment extends BaseFragment {

    @BindView(R.id.brv_list)
    BaseRecyclerView brvGoodsList;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_new)
    TextView tvNew;
    private List<LostAndFoundEntity> mDatas = new ArrayList<>();
    private LoastAndFoundAdapter loastAndFoundAdapter;
    private int currentCount = -10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_found;
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("捡到物品");
        loastAndFoundAdapter = new LoastAndFoundAdapter(mDatas, actitity);
        brvGoodsList.setRecyclerViewAdapter(loastAndFoundAdapter);
        loadDatas();
        initRefreshListener();
    }


    private void initRefreshListener() {
        brvGoodsList.setRefreshListener(new BaseRecyclerView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentCount = -10; //重新置位
                mDatas.clear();
                loastAndFoundAdapter.notifyDataSetChanged();
                loadDatas();

            }
        });
        brvGoodsList.setLoadMoreListener(new BaseRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadDatas();
            }
        });


    }


    private void loadDatas() {
        currentCount = currentCount + 10;
        BmobQuery<LostAndFoundEntity> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.order("-updatedAt");
        query.addWhereEqualTo("type", 2);
        query.setLimit(10);
        query.setSkip(currentCount);
        query.findObjects(new FindListener<LostAndFoundEntity>() {
            @Override
            public void done(List<LostAndFoundEntity> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        mDatas.add(list.get(i));
                    }
                    loastAndFoundAdapter.notifyDataSetChanged();
                    brvGoodsList.setRefreshLayoutState(false);
                } else {
                    brvGoodsList.setRefreshLayoutState(false);
                }
            }
        });
    }

    @OnClick(R.id.tv_new)
    public void onViewClicked() {
        startActivity(new Intent(actitity, ReleaseLostAndFoundActivity.class));
    }


}
