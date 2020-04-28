package com.zhuandian.lostandfound.business;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuandian.base.BaseActivity;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.entity.UserEntity;


import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PersonalDataActivity extends BaseActivity {
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private UserEntity userEntity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected void setUpView() {
        tvTitle.setText("修改密码");
        userEntity = BmobUser.getCurrentUser(UserEntity.class);

    }


    @OnClick({R.id.iv_back, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_submit:

                String oldPassword = etOldPassword.getText().toString();
                String newPassword = etNewPassword.getText().toString();

                BmobUser.updateCurrentUserPassword(oldPassword, newPassword, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e==null){
                            Toast.makeText(PersonalDataActivity.this, "修改成功，重启生效", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(PersonalDataActivity.this, "请确保旧密码是否输入正确...", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                break;
        }
    }
}
