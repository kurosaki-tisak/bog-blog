package com.dev_tee.bogblog.addblog;

import com.dev_tee.bogblog.data.Blog;

/**
 * Created by Dev_Tee on 1/7/17.
 */
public interface AddBlogContract {

    interface View {

        void showBlogList();

        void showExistBlog(Blog item);

        void showEmptyEntryMsg();
    }
    
    interface UserActions {

        void saveBlog(String title, String description);

        void updateBlog(Blog oldBlog, String title, String description);

        void getExistBlog(int id);
    }
}
