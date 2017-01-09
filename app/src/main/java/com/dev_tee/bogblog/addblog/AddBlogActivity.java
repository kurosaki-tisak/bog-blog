package com.dev_tee.bogblog.addblog;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dev_tee.bogblog.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev_Tee on 1/8/17.
 */
public class AddBlogActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static final String EXTRA_FLAG_EDIT = "editMode";
    public static final String EXTRA_BLOG_ID = "blogId";

    private AddBlogFragment addBlogFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_blog);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        String editMode = getIntent().getStringExtra(EXTRA_FLAG_EDIT);
        int blogId = getIntent().getIntExtra(EXTRA_BLOG_ID, 0);

        boolean isEditMode;
        if (editMode == null) {
            isEditMode = false;
        } else {
            isEditMode = true;
        }

        addBlogFragment = AddBlogFragment.newInstance(isEditMode, blogId);

        initFragment(addBlogFragment);

    }

    //Add fragment to layout
    private void initFragment(Fragment blogFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content_main, blogFragment);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        addBlogFragment.onActivityResult(requestCode, resultCode, data);
    }

    @OnClick(R.id.btnBack)
    public void onBackClick(View view) {
        onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
