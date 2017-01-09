package com.dev_tee.bogblog.addblog;

import com.dev_tee.bogblog.data.Blog;
import com.dev_tee.bogblog.data.BlogRepositories;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Dev_Tee on 1/7/17.
 */

public class AddBlogPresenterTest {

    @Mock
    private BlogRepositories blogRepositories;

    @Mock
    private AddBlogContract.View view;

    private AddBlogPresenter addBlogPresenter;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        addBlogPresenter = new AddBlogPresenter(blogRepositories, view);
    }

    @Test
    public void should_AddBlog_ShowSuccessUI() throws Exception {

        addBlogPresenter.saveBlog("Title", "Description");

        //Save Blog obj to repo
        verify(blogRepositories).addBlog(any(Blog.class));

        //Show new Blog to UI
        verify(view).showBlogList();
    }

    @Test
    public void should_AddEmptyBlog_ShowWarningUI() throws Exception {

        addBlogPresenter.saveBlog("", "");

        //Show waring msg
        verify(view).showEmptyEntryMsg();
    }
}
