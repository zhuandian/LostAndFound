package com.zhuandian.lostandfound;


import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.base.BaseFragment;
import com.zhuandian.lostandfound.adapter.HomePageAdapter;
import com.zhuandian.lostandfound.business.fragment.BookFragment;
import com.zhuandian.lostandfound.business.fragment.FoundFragment;
import com.zhuandian.lostandfound.business.fragment.LostFragment;
import com.zhuandian.lostandfound.business.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.tab_bottom)
    BottomNavigationView tabBottom;
    public static final int PAGE_LOST = 0;
    public static final int PAGE_FOUND = 1;
    public static final int PAGE_BOOK = 2;
    public static final int PAGE_MY = 3;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setUpView() {
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(new LostFragment());
        fragmentList.add(new FoundFragment());
        fragmentList.add(new BookFragment());
        fragmentList.add(new MineFragment());
        vpHome.setAdapter(new HomePageAdapter(getSupportFragmentManager(), fragmentList));
        vpHome.setOffscreenPageLimit(4);

        vpHome.setCurrentItem(PAGE_LOST);
        initBottomTab();
    }

    public void setCurrentPage(int position) {
        vpHome.setCurrentItem(position);
    }

    private void initBottomTab() {
        vpHome.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabBottom.getMenu().getItem(position).setChecked(true);
            }
        });

        tabBottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.tab_lost:
                        vpHome.setCurrentItem(PAGE_LOST);
                        break;
                    case R.id.tab_found:
                        vpHome.setCurrentItem(PAGE_FOUND);
                        break;
                    case R.id.tab_my:
                        vpHome.setCurrentItem(PAGE_MY);
                        break;
                    case R.id.tab_book:
                        vpHome.setCurrentItem(PAGE_BOOK);
                        break;
                }

                return true;
            }
        });
    }
}
