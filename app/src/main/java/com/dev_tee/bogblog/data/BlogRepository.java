package com.dev_tee.bogblog.data;

import java.util.Date;
import java.util.List;

/**
 * Created by Dev_Tee on 1/8/17.
 */

public interface BlogRepository {

    void getBlogList(LoadBlogListCallBack callBack);

    void getBlog(int id, LoadBlogItemCallBack callBack);

    void addBlog(Blog item);

    void updateBlog(Blog oldBlog, String title, String content, String contentThumbnail, Date dateTime);

    void deleteBlog(Blog eq, DeleteBlogItemCallBack capture);

    void sortByDate(LoadBlogListCallBack capture);

    void sortByTitle(boolean isSort, LoadBlogListCallBack capture);

    interface LoadBlogListCallBack {

        void onLoadedSuccess(List<Blog> blogList);

        void onLoadedError();
    }
    
    interface LoadBlogItemCallBack {
        
        void onLoadedSuccess(Blog item);
        
        void onLoadedError();
    }

    interface DeleteBlogItemCallBack {

        void onDeleteSuccess(Blog deleteItem);

        void onDeleteError();
    }
}
