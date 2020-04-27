package com.zhuandian.lostandfound.business.activity;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.zhuandian.base.BaseActivity;
import com.zhuandian.lostandfound.R;
import com.zhuandian.lostandfound.adapter.MessageCommentAdapter;
import com.zhuandian.lostandfound.entity.LostAndFoundEntity;
import com.zhuandian.lostandfound.entity.MessageCommentEntity;
import com.zhuandian.lostandfound.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class LostAndFoundDetailActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_content_title)
    TextView tvContentTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_release_user)
    TextView tvReleaseUser;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.comment_listview)
    RecyclerView commentRecyclewView;
    @BindView(R.id.comment_content)
    EditText commentContent;
    @BindView(R.id.submit_comment)
    TextView submitComment;
    @BindView(R.id.submit_comment_layout)
    LinearLayout submitCommentLayout;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private LostAndFoundEntity lostAndFoundEntity;
    private List<MessageCommentEntity> commentDatas = new ArrayList<>();
    private MessageCommentAdapter userCommentAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lost_and_found_detail;
    }

    @Override
    protected void setUpView() {
        lostAndFoundEntity = ((LostAndFoundEntity) getIntent().getSerializableExtra("entity"));
        tvTitle.setText("失物招领详情");
        tvContentTitle.setText(lostAndFoundEntity.getTitle());
        tvContent.setText(lostAndFoundEntity.getContent());
        tvTime.setText(lostAndFoundEntity.getCreatedAt());
        tvReleaseUser.setText("发布人：" + lostAndFoundEntity.getUserEntity().getUsername());
        tvType.setText(lostAndFoundEntity.getType() == 1 ? "丢失" : "捡到");

        userCommentAdapter = new MessageCommentAdapter(commentDatas, this);
        commentRecyclewView.setAdapter(userCommentAdapter);
        commentRecyclewView.setLayoutManager(new LinearLayoutManager(this));
        getAllUserComment();  //得到所有参与该动态评论的用户信息和内容
    }

    /**
     * 绑定当前动态下的所有评论信息
     */
    private void getAllUserComment() {
        BmobQuery<MessageCommentEntity> query = new BmobQuery<>();
        //用此方式可以构造一个BmobPointer对象。只需要设置objectId就行
        LostAndFoundEntity post = new LostAndFoundEntity();
        post.setObjectId(lostAndFoundEntity.getObjectId());   //得到当前动态的Id号，
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.order("-updatedAt");
        query.addWhereEqualTo("lostAndFoundEntity", new BmobPointer(post));
        query.include("userEntity,lostAndFoundEntity.userEntity");
        query.findObjects(new FindListener<MessageCommentEntity>() {
            @Override
            public void done(List<MessageCommentEntity> objects, BmobException e) {
                if (e == null) {
                    //插入数据，通知列表更新
//                    commentDatas = objects;  直接赋值，因为前后绑定的apapter对应的list地址不是同一个，所以 notifyDataSetChanged无效，
                    commentDatas.clear();
                    commentDatas.addAll(objects); //把数据添加进集合
                    userCommentAdapter.notifyDataSetChanged();
                } else {
                    Log.e("查询数据失败", "tag");
                }
            }
        });


    }
    @OnClick({R.id.iv_back, R.id.submit_comment, R.id.fab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.submit_comment:
                submitUserComment();
                break;
            case R.id.fab:
                commentRecyclewView.setVisibility(View.GONE);
                submitCommentLayout.setVisibility(View.VISIBLE);
                fab.setVisibility(View.GONE);
                break;
        }
    }

    private void submitUserComment() {
        String userComment = commentContent.getText().toString();  //得到用户输入框的评论内容
        if (TextUtils.isEmpty(userComment)) {
            Snackbar.make(tvTitle, "评论内容不允许为空...", Snackbar.LENGTH_LONG).show();
        } else {

            UserEntity user = BmobUser.getCurrentUser(UserEntity.class);  //得到当前用户
            LostAndFoundEntity post = new LostAndFoundEntity();   //当前动态内容
            post.setObjectId(lostAndFoundEntity.getObjectId());  //得到当前的动态的id，与评论建立关联
            final MessageCommentEntity commentEntity = new MessageCommentEntity();
            commentEntity.setContent(userComment);
            commentEntity.setUserEntity(user);
            commentEntity.setLostAndFoundEntity(lostAndFoundEntity);
            commentEntity.save(new SaveListener<String>() {
                @SuppressLint("RestrictedApi")
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null) {
                        Snackbar.make(tvTitle, "评论成功", Snackbar.LENGTH_LONG).show();
                        commentContent.setText(""); //清空输入框，防止用户二次评论时影响用户体验
                        commentRecyclewView.setVisibility(View.VISIBLE);
                        submitCommentLayout.setVisibility(View.GONE);
                        fab.setVisibility(View.VISIBLE);

                        getAllUserComment();  //重新加载一遍数据
                    } else {
                        Log.e("评论失败", "-----");
                    }
                }

            });
        }
    }

}
