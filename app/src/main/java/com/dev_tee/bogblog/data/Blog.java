package com.dev_tee.bogblog.data;


import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Dev_Tee on 1/7/17.
 */
public class Blog extends RealmObject{

    @PrimaryKey
    private int id;

    private String title;

    private String content;

    private String contentThumbnail;

    private Date timeCreated;

    private String ownerName;

    private String ownerThumbnail;

    public Blog() {
    }

    public Blog(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentThumbnail() {
        return contentThumbnail;
    }

    public void setContentThumbnail(String contentThumbnail) {
        this.contentThumbnail = contentThumbnail;
    }

    public Date getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Date timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerThumbnail() {
        return ownerThumbnail;
    }

    public void setOwnerThumbnail(String ownerThumbnail) {
        this.ownerThumbnail = ownerThumbnail;
    }

    public boolean isEmpty() {
        return (title == null || "".equals(title)) &&
                (content == null || "".equals(content));
    }
}
