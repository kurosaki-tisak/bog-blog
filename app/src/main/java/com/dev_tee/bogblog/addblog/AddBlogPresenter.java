package com.dev_tee.bogblog.addblog;

import android.support.annotation.NonNull;

import com.dev_tee.bogblog.data.Blog;
import com.dev_tee.bogblog.data.BlogRepositories;
import com.dev_tee.bogblog.data.BlogRepository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Dev_Tee on 1/7/17.
 */
public class AddBlogPresenter implements AddBlogContract.UserActions{

    @NonNull
    private BlogRepositories repository;

    @NonNull
    private AddBlogContract.View view;

    public AddBlogPresenter(@NonNull BlogRepositories repository, @NonNull AddBlogContract.View view) {
        this.repository = repository;
        this.view = view;
    }

    @Override
    public void saveBlog(String title, String description) {

        Blog saveBlog = extractItem(title, description);

        if (!saveBlog.isEmpty()) {
            repository.addBlog(saveBlog);
            view.showBlogList();
        } else {
            view.showEmptyEntryMsg();
        }
    }

    @Override
    public void updateBlog(Blog oldBlog, String title, String description) {

        Document document = Jsoup.parse(description);
        Element img = document.select("img").first();

        String contentThumbnail = "";

        if (img != null) {
            contentThumbnail = img.attr("src");
        }

        Date newDate = null;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = sdf.format(c.getTime());

        try {
            newDate = sdf.parse(currentDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!oldBlog.isEmpty()) {
            repository.updateBlog(oldBlog, title, description, contentThumbnail, newDate );
            view.showBlogList();
        } else {
            view.showEmptyEntryMsg();
        }
    }

    private Blog extractItem(String title, String description) {
        Blog newBlog = new Blog(title, description);

        Document document = Jsoup.parse(description);
        Element img = document.select("img").first();

        if (img != null) {
            String src = img.attr("src");
            newBlog.setContentThumbnail(src);
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateTime = sdf.format(c.getTime());

        try {
            Date newDate = sdf.parse(currentDateTime);
            newBlog.setTimeCreated(newDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newBlog;
    }

    @Override
    public void getExistBlog(int id) {

        repository.getBlog(id, new BlogRepository.LoadBlogItemCallBack() {
            @Override
            public void onLoadedSuccess(Blog item) {
                view.showExistBlog(item);
            }

            @Override
            public void onLoadedError() {
                view.showEmptyEntryMsg();
            }
        });
    }
}
