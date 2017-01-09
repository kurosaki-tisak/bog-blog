package com.dev_tee.bogblog;

import com.dev_tee.bogblog.data.BlogRepositories;

/**
 * Created by Dev_Tee on 1/8/17.
 */

public class Injection {

    public static BlogRepositories provideBlogRepository() {
        return new BlogRepositories();
    }
}
