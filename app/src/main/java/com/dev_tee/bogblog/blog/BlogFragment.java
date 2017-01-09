package com.dev_tee.bogblog.blog;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dev_tee.bogblog.Injection;
import com.dev_tee.bogblog.R;
import com.dev_tee.bogblog.addblog.AddBlogActivity;
import com.dev_tee.bogblog.data.Blog;
import com.dev_tee.bogblog.details.BlogDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Dev_Tee on 1/8/17.
 */

public class BlogFragment extends Fragment implements BlogContract.View {

    @BindView(R.id.iconSortByTitle)
    AppCompatImageView iconSortByTitle;
    @BindView(R.id.txtSortByTitle)
    TextView txtSortByTitle;
    @BindView(R.id.btnSortByTitle)
    LinearLayout btnSortByTitle;
    @BindView(R.id.iconSortByDate)
    AppCompatImageView iconSortByDate;
    @BindView(R.id.txtSortByDate)
    TextView txtSortByDate;
    @BindView(R.id.btnSortByDate)
    LinearLayout btnSortByDate;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private static final int REQUEST_ADD_BLOG = 1;

    private boolean FLAG_IS_SORT_BY_DATE = false;
    private boolean FLAG_IS_SORT_BY_TITLE = false;

    private Context context;
    private BlogPresenter presenter;
    private BlogListAdapter blogListAdapter;
    private BlogBottomMenuDialogFragment bottomSheetDialogFragment;
    private List<Blog> blogList = new ArrayList<>();

    public static BlogFragment newInstance() {
        return new BlogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
        presenter = new BlogPresenter(Injection.provideBlogRepository(), this);
        blogListAdapter = new BlogListAdapter(context, blogList, blogItemListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.showExitBlog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_ADD_BLOG == requestCode && Activity.RESULT_OK == resultCode) {
            Snackbar.make(getView(), "Add Blog Success", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRetainInstance(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(blogListAdapter);
    }

    @Override
    public void showAddNewBlogUI() {
        Intent intent = new Intent(getContext(), AddBlogActivity.class);
        startActivityForResult(intent, REQUEST_ADD_BLOG);
    }

    @Override
    public void showBlog(List<Blog> blogList) {
        blogListAdapter.replaceData(blogList);
    }

    @Override
    public void showBlogDetailsUI(Blog item) {
        Intent intent = new Intent(getContext(), BlogDetailsActivity.class);
        intent.putExtra(BlogDetailsActivity.EXTRA_BLOG_ID, item.getId());
        startActivity(intent);
    }

    @Override
    public void showBlogEditUI(Blog item) {
        Intent intent = new Intent(getContext(), AddBlogActivity.class);
        intent.putExtra(AddBlogActivity.EXTRA_FLAG_EDIT, "edit");
        intent.putExtra(AddBlogActivity.EXTRA_BLOG_ID, item.getId());
        startActivity(intent);
    }

    @Override
    public void showDeleteBlogErrorUI() {
        Snackbar.make(getView(), "Ops! Something went wrong!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadingError() {
        Snackbar.make(getView(), "Ops! Something went wrong!", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showDeleteBlogItem(Blog deleteBlog) {
        blogListAdapter.deleteItem(deleteBlog);
    }

    @OnClick(R.id.fab)
    public void onFabMenuClick(View view) {
        presenter.addNewBlog();
    }

    @OnClick(R.id.btnSortByDate)
    public void onBtnSortByDateClick(View view) {

        if (FLAG_IS_SORT_BY_DATE) {
            presenter.sortByDate(false);
            FLAG_IS_SORT_BY_DATE = false;

            txtSortByDate.setTextColor(ContextCompat.getColor(context,R.color.colorSortIcon));
            iconSortByDate.setColorFilter(ContextCompat.getColor(context, R.color.colorSortIcon),
                    PorterDuff.Mode.SRC_IN);

        } else {
            presenter.sortByDate(true);
            FLAG_IS_SORT_BY_DATE = true;

            txtSortByDate.setTextColor(ContextCompat.getColor(context,R.color.colorAccent));
            iconSortByDate.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
        }

    }

    @OnClick(R.id.btnSortByTitle)
    public void onBtnSortByTitle(View view) {

        if (FLAG_IS_SORT_BY_TITLE) {
            presenter.sortByTitle(false);
            FLAG_IS_SORT_BY_TITLE = false;

            txtSortByTitle.setTextColor(ContextCompat.getColor(context, R.color.colorSortIcon));
            iconSortByTitle.setColorFilter(ContextCompat.getColor(context, R.color.colorSortIcon),
                    PorterDuff.Mode.SRC_IN);
        } else {
            presenter.sortByTitle(true);
            FLAG_IS_SORT_BY_TITLE = true;

            txtSortByTitle.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            iconSortByTitle.setColorFilter(ContextCompat.getColor(context, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
        }
    }

    /**
     * Listener for clicks on blog in the RecyclerView.
     */
    BlogItemListener blogItemListener = new BlogItemListener() {
        @Override
        public void onBlogClick(Blog blog) {
            presenter.openBlogDetails(blog);
        }

        @Override
        public void onDeleteClick(Blog blog) {
        //    confirmDeleteDialog(blog);
            bottomSheetDialogFragment = BlogBottomMenuDialogFragment.newInstance(bottomMenuClickListener, blog);
            bottomSheetDialogFragment.show(getChildFragmentManager(), bottomSheetDialogFragment.getTag());
        }
    };

    BottomMenuClickListener bottomMenuClickListener = new BottomMenuClickListener() {
        @Override
        public void onEditClick(Blog blog) {

            presenter.openBlogEdit(blog);

            bottomSheetDialogFragment.dismiss();
        }

        @Override
        public void onDeleteClick(Blog blog) {
            confirmDeleteDialog(blog);

            bottomSheetDialogFragment.dismiss();
        }
    };

    public interface BlogItemListener {
        void onBlogClick(Blog blog);
        void onDeleteClick(Blog blog);
    }

    public interface BottomMenuClickListener {
        void onEditClick(Blog blog);
        void onDeleteClick(Blog blog);
    }

    /**
     * Confirm delete dialog
     */
    private void confirmDeleteDialog(final Blog item) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("Do you want to delete this blog?");
        dialog.setCancelable(false);
        dialog.setNegativeButton(("No"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton(("Confirm"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.deleteBlog(item);

                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
