package com.dev_tee.bogblog.details;

import com.dev_tee.bogblog.data.Blog;

/**
 * Created by Dev_Tee on 1/7/17.
 */
public interface DetailsContract {

    interface View {

        void showContent(Blog item);

        void showMissingBlog();
    }

    interface UserActions {
        void openBlog(int id);
    }
}
