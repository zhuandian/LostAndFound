package com.zhuandian.lostandfound.business.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.entity.BookEntity;
import com.zhuandian.lostandfound.entity.LostAndFoundEntity;
import com.zhuandian.lostandfound.entity.UserEntity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class ReleaseBookActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_release)
    TextView tvRelease;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_release_book;
    }

    @Override
    protected void setUpView() {

        tvTitle.setText("新建发布");
    }


    @OnClick({R.id.iv_back, R.id.tv_release})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_release:
                newRelease();
                break;
        }
    }

    private void newRelease() {
        String title = etTitle.getText().toString();
        String content = etContent.getText().toString();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content) ) {
            Toast.makeText(this, "发布内容不允许为空...", Toast.LENGTH_SHORT).show();
            return;
        }

        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(title);
        bookEntity.setContent(content);
        bookEntity.setUserEntity(BmobUser.getCurrentUser(UserEntity.class));
        bookEntity.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Toast.makeText(ReleaseBookActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
