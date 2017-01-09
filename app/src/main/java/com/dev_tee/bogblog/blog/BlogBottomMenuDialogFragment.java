package com.dev_tee.bogblog.blog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev_tee.bogblog.R;
import com.dev_tee.bogblog.data.Blog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev_Tee on 1/9/17.
 */

public class BlogBottomMenuDialogFragment extends BottomSheetDialogFragment {

    @BindView(R.id.btnEdit)
    TextView btnEdit;
    @BindView(R.id.btnDelete)
    TextView btnDelete;

    private BlogFragment.BottomMenuClickListener bottomMenuClickListener;
    private Blog blog;

    public static BlogBottomMenuDialogFragment newInstance(BlogFragment.BottomMenuClickListener listener,
                                                           Blog item) {
        BlogBottomMenuDialogFragment fragment = new BlogBottomMenuDialogFragment();
        fragment.bottomMenuClickListener = listener;
        fragment.blog = item;
        return fragment;
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.fragment_bottom_menu, null);
        ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);
    }

    @OnClick(R.id.btnEdit)
    public void onBtnEditClick(View view) {
        bottomMenuClickListener.onEditClick(blog);
    }

    @OnClick(R.id.btnDelete)
    public void onBtnDeleteClick(View view) {
        bottomMenuClickListener.onDeleteClick(blog);
    }
}
