package com.example.zjutbucketlist;

import java.util.Date;
import java.util.UUID;

public class BucketList {
    private UUID uuid;
    private String title;
    private String description;
    private Date date;
    private boolean complete;

    public BucketList( String title, String description, Date date) {
        this.uuid = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.date = date;
        this.complete = false;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    public void updateBucket(BucketList bucketList)
    {
        title= bucketList.gettitle();
        date= bucketList.getDate();
        description= bucketList.getDescription();
    }
}
