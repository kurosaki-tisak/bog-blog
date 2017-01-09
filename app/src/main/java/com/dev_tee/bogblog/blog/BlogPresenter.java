package com.dev_tee.bogblog.blog;

import com.dev_tee.bogblog.data.Blog;
import com.dev_tee.bogblog.data.BlogRepositories;
import com.dev_tee.bogblog.data.BlogRepository;

import java.util.List;

/**
 * Created by Dev_Tee on 1/7/17.
 */
public class BlogPresenter implements BlogContract.UserActions{

    private BlogRepositories repo;
    private BlogContract.View view;

    public BlogPresenter(BlogRepositories repo, BlogContract.View view) {
        this.repo = repo;
        this.view = view;
    }

    @Override
    public void addNewBlog() {
        view.showAddNewBlogUI();
    }

    @Override
    public void openBlogEdit(Blog selectedBlog) {
        view.showBlogEditUI(selectedBlog);
    }

    @Override
    public void deleteBlog(Blog item) {
        repo.deleteBlog(item, new BlogRepository.DeleteBlogItemCallBack() {
            @Override
            public void onDeleteSuccess(Blog deleteBlog) {
                view.showDeleteBlogItem(deleteBlog);
            }

            @Override
            public void onDeleteError() {
                view.showDeleteBlogErrorUI();
            }
        });
    }

    @Override
    public void sortByDate(boolean isSort) {

        if (isSort) {
            repo.sortByDate(new BlogRepository.LoadBlogListCallBack() {
                @Override
                public void onLoadedSuccess(List<Blog> blogList) {
                    view.showBlog(blogList);
                }

                @Override
                public void onLoadedError() {
                    view.showLoadingError();
                }
            });
        } else {
            repo.getBlogList(new BlogRepository.LoadBlogListCallBack() {
                @Override
                public void onLoadedSuccess(List<Blog> blogList) {
                    view.showBlog(blogList);
                }

                @Override
                public void onLoadedError() {
                    view.showLoadingError();
                }
            });
        }
    }

    @Override
    public void sortByTitle(boolean isSort) {

        repo.sortByTitle(isSort, new BlogRepository.LoadBlogListCallBack() {
            @Override
            public void onLoadedSuccess(List<Blog> blogList) {
                view.showBlog(blogList);
            }

            @Override
            public void onLoadedError() {
                view.showLoadingError();
            }
        });
    }

    @Override
    public void showExitBlog() {
        repo.getBlogList(new BlogRepository.LoadBlogListCallBack() {
            @Override
            public void onLoadedSuccess(List<Blog> blogList) {
                view.showBlog(blogList);
            }

            @Override
            public void onLoadedError() {
                view.showLoadingError();
            }
        });
    }

    @Override
    public void openBlogDetails(Blog selectedBlog) {
        view.showBlogDetailsUI(selectedBlog);
    }
}
