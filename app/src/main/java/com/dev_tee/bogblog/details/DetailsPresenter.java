package com.dev_tee.bogblog.details;

import android.support.annotation.Nullable;

import com.dev_tee.bogblog.data.Blog;
import com.dev_tee.bogblog.data.BlogRepositories;
import com.dev_tee.bogblog.data.BlogRepository;

/**
 * Created by Dev_Tee on 1/7/17.
 */
public class DetailsPresenter implements DetailsContract.UserActions{

    private BlogRepositories repo;
    private DetailsContract.View view;

    public DetailsPresenter(BlogRepositories blogRepositories, DetailsContract.View view) {
        this.repo = blogRepositories;
        this.view = view;
    }

    @Override
    public void openBlog(@Nullable int id) {

        repo.getBlog(id, new BlogRepository.LoadBlogItemCallBack() {
            @Override
            public void onLoadedSuccess(Blog item) {
                view.showContent(item);
            }

            @Override
            public void onLoadedError() {
                view.showMissingBlog();
            }
        });

    }
}
