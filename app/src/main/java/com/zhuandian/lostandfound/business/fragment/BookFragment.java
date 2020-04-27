package com.zhuandian.lostandfound.business.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.zhuandian.base.BaseFragment;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.adapter.BookAdapter;
import com.zhuandian.lostandfound.adapter.LoastAndFoundAdapter;
import com.zhuandian.lostandfound.business.activity.ReleaseBookActivity;
import com.zhuandian.lostandfound.business.activity.ReleaseLostAndFoundActivity;
import com.zhuandian.lostandfound.business.utils.BaseRecyclerView;
import com.zhuandian.lostandfound.entity.BookEntity;
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
 */
public class BookFragment extends BaseFragment {

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
    private List<BookEntity> mDatas = new ArrayList<>();
    private BookAdapter loastAndFoundAdapter;
    private int currentCount = -10;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("二手书");
        loastAndFoundAdapter = new BookAdapter(mDatas, actitity);
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
        BmobQuery<BookEntity> query = new BmobQuery<>();
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.order("-updatedAt");
        query.include("userEntity");
        query.setLimit(10);
        query.setSkip(currentCount);
        query.findObjects(new FindListener<BookEntity>() {
            @Override
            public void done(List<BookEntity> list, BmobException e) {
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
        startActivity(new Intent(actitity, ReleaseBookActivity.class));
    }


}
