package com.dev_tee.bogblog.data;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Dev_Tee on 1/7/17.
 */
public class BlogRepositories implements BlogRepository {

    @Override
    public void getBlogList(final BlogRepository.LoadBlogListCallBack callBack) {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Blog> results = realm.where(Blog.class)
                .findAll();

        if (results == null) {
            callBack.onLoadedError();
        } else {
            RealmList<Blog> newBlog = new RealmList<>();
            newBlog.addAll(results);
            callBack.onLoadedSuccess(newBlog);
        }

    //    realm.close();
    }

    @Override
    public void getBlog(int id, final LoadBlogItemCallBack callBack) {

        Realm realm = Realm.getDefaultInstance();
        Blog result = realm.where(Blog.class)
                .equalTo("id", id)
                .findFirst();

        callBack.onLoadedSuccess(result);

        if (result == null) {
            callBack.onLoadedError();
        }

     //   realm.close();
    }

    @Override
    public void addBlog(final Blog item) {

        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Number currentIdNum = realm.where(Blog.class).max("id");
                int nextId;
                if(currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }
                item.setId(nextId);
                realm.insertOrUpdate(item);
            }
        });
        realm.close();
    }

    @Override
    public void updateBlog(final Blog oldBlog, final String title, final String content,
                           final String contentThumbnail, final Date dateTime) {

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                oldBlog.setTitle(title);
                oldBlog.setContent(content);
                oldBlog.setContentThumbnail(contentThumbnail);
                oldBlog.setTimeCreated(dateTime);

                realm.insertOrUpdate(oldBlog);
            }
        });

        realm.close();
    }

    @Override
    public void deleteBlog(final Blog item, final DeleteBlogItemCallBack capture) {

        Realm realm = Realm.getDefaultInstance();
        final Blog result = realm.where(Blog.class)
                .equalTo("id", item.getId())
                .findFirst();

        if (result == null) {
            capture.onDeleteError();
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                capture.onDeleteSuccess(item);
                result.deleteFromRealm();
            }
        });

        realm.close();
    }

    @Override
    public void sortByDate(final LoadBlogListCallBack capture) {

        Realm realm = Realm.getDefaultInstance();

        RealmResults<Blog> results = realm.where(Blog.class)
                .findAllSorted("timeCreated", Sort.DESCENDING);

        RealmList<Blog> newBlog = new RealmList<>();
        newBlog.addAll(results);

        capture.onLoadedSuccess(newBlog);

   //     realm.close();
    }

    @Override
    public void sortByTitle(boolean isSort, final LoadBlogListCallBack capture) {

        Realm realm = Realm.getDefaultInstance();

        if (isSort) {

            RealmResults<Blog> results = realm.where(Blog.class)
                    .findAllSorted("title", Sort.ASCENDING);


            RealmList<Blog> newBlog = new RealmList<>();
            newBlog.addAll(results);

            capture.onLoadedSuccess(newBlog);

        } else {

            RealmResults<Blog> results = realm.where(Blog.class)
                    .findAllSorted("title", Sort.DESCENDING);

            RealmList<Blog> newBlog = new RealmList<>();
            newBlog.addAll(results);

            capture.onLoadedSuccess(newBlog);
        }

   //     realm.close();
    }

}
