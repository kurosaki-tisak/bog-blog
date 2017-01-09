package com.dev_tee.bogblog.blog;

import com.dev_tee.bogblog.data.Blog;

import java.util.List;

/**
 * Created by Dev_Tee on 1/7/17.
 */

public interface BlogContract {

    interface View {

        void showAddNewBlogUI();

        void showBlog(List<Blog> blogList);

        void showBlogDetailsUI(Blog item);

        void showBlogEditUI(Blog item);

        void showDeleteBlogErrorUI();

        void showLoadingError();

        void showDeleteBlogItem(Blog deleteBlog);
    }
    
    interface UserActions {

        void showExitBlog();

        void openBlogDetails(Blog selectedBlog);

        void addNewBlog();

        void openBlogEdit(Blog selectedBlog);

        void deleteBlog(Blog item);

        void sortByDate(boolean isSort);

        void sortByTitle(boolean b);
    }
}
