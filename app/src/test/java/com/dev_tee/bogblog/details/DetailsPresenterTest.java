package com.dev_tee.bogblog.details;

import com.dev_tee.bogblog.data.Blog;
import com.dev_tee.bogblog.data.BlogRepositories;
import com.dev_tee.bogblog.data.BlogRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

/**
 * Created by Dev_Tee on 1/7/17.
 */

public class DetailsPresenterTest {

    public static final String TITLE_TEST = "title";
    public static final String DESCRIPTION_TEST = "description";

    @Mock
    private BlogRepositories blogRepositories;

    @Mock
    private DetailsContract.View view;

    private DetailsPresenter detailsPresenter;

    @Captor
    private ArgumentCaptor<BlogRepository.LoadBlogItemCallBack> loadBlogItemCallBackArgumentCaptor;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        detailsPresenter = new DetailsPresenter(blogRepositories, view);
    }

    @Test
    public void should_ShowBlogDetails() throws Exception {

        Blog details = new Blog(TITLE_TEST, DESCRIPTION_TEST);

        detailsPresenter.openBlog(details.getId());

        verify(blogRepositories).getBlog(eq(details.getId()), loadBlogItemCallBackArgumentCaptor.capture());

        loadBlogItemCallBackArgumentCaptor.getValue().onLoadedSuccess(details);

        verify(view).showContent(details);
    }
}
