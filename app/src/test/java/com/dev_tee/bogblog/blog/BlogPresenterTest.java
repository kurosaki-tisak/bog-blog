package com.dev_tee.bogblog.blog;

import com.dev_tee.bogblog.data.Blog;
import com.dev_tee.bogblog.data.BlogRepositories;
import com.dev_tee.bogblog.data.BlogRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by Dev_Tee on 1/7/17.
 */

public class BlogPresenterTest {

    private static final List<Blog> SAMPLE_BLOG_LIST = new ArrayList<>();

    @Mock
    private BlogRepositories blogRepositories;

    @Mock
    private BlogContract.View blogView;

    private BlogPresenter blogPresenter;

    @Captor
    private ArgumentCaptor<BlogRepository.LoadBlogListCallBack> loadBlogListCallBackArgumentCaptor;

    @Captor
    private ArgumentCaptor<BlogRepository.DeleteBlogItemCallBack> deleteBlogCallBackArgumentCaptor;

    @Before
    public void setUpBlogPresenter() throws Exception {

        MockitoAnnotations.initMocks(this);

        blogPresenter = new BlogPresenter(blogRepositories, blogView);

        Blog sample1 = new Blog("Title", "Content");
        Blog sample2 = new Blog("Title2", "Content2");

        SAMPLE_BLOG_LIST.add(sample1);
        SAMPLE_BLOG_LIST.add(sample2);
    }

    @Test
    public void should_LoadBlog_ShowBlogList() throws Exception {

        blogPresenter.showExitBlog();

        verify(blogRepositories).getBlogList(loadBlogListCallBackArgumentCaptor.capture());
        loadBlogListCallBackArgumentCaptor.getValue().onLoadedSuccess(SAMPLE_BLOG_LIST);

        verify(blogView).showBlog(SAMPLE_BLOG_LIST);
    }

    @Test
    public void should_ClickOnBlogItem_ShowBlogDetails() throws Exception {

        Blog selectedBlog = new Blog("Title", "Content");

        blogPresenter.openBlogDetails(selectedBlog);

        verify(blogView).showBlogDetailsUI(selectedBlog);
    }

    @Test
    public void should_ClickOnEditBlog_ShowBlogEdit() throws Exception {

        Blog selectedBlog = new Blog("Title", "Content");

        blogPresenter.openBlogEdit(selectedBlog);

        verify(blogView).showBlogEditUI(selectedBlog);
    }

    @Test
    public void should_ClickOnDelete_ShowRemainList() throws Exception {

        blogPresenter.showExitBlog();

        verify(blogRepositories).getBlogList(loadBlogListCallBackArgumentCaptor.capture());
        loadBlogListCallBackArgumentCaptor.getValue().onLoadedSuccess(SAMPLE_BLOG_LIST);

        verify(blogView).showBlog(SAMPLE_BLOG_LIST);

        Blog item = SAMPLE_BLOG_LIST.get(0);

        blogPresenter.deleteBlog(item);

        verify(blogRepositories).deleteBlog(eq(item), deleteBlogCallBackArgumentCaptor.capture());
        deleteBlogCallBackArgumentCaptor.getValue().onDeleteSuccess(item);

        verify(blogView).showDeleteBlogItem(item);

    }

    @Test
    public void should_ClickOnSortingByDate_ShowFilteredBlogList() throws Exception {

        blogPresenter.sortByDate(true);

        verify(blogRepositories).sortByDate(loadBlogListCallBackArgumentCaptor.capture());
        loadBlogListCallBackArgumentCaptor.getValue().onLoadedSuccess(SAMPLE_BLOG_LIST);

        verify(blogView).showBlog(SAMPLE_BLOG_LIST);
    }

    @Test
    public void should_ClickOnSortingByTitle_ShowFilteredBlogList() throws Exception {

        //DESCENDING
     //   blogPresenter.sortByTitle(true);
        //ASCENDING
        blogPresenter.sortByTitle(false);

        verify(blogRepositories).sortByTitle(eq(false), loadBlogListCallBackArgumentCaptor.capture());
        loadBlogListCallBackArgumentCaptor.getValue().onLoadedSuccess(SAMPLE_BLOG_LIST);

        verify(blogView).showBlog(SAMPLE_BLOG_LIST);
    }

    @Test
    public void should_ClickOnFab_ShowAddNewBlog() throws Exception {

        blogPresenter.addNewBlog();

        verify(blogView).showAddNewBlogUI();
    }
}
