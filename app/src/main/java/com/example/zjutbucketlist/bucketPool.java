package com.example.zjutbucketlist;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;

public class bucketPool {
    private static bucketPool bucketPool;
    private ArrayList<BucketList> bucketLists;

    public static bucketPool getbucketPool(Context context) {

        if(bucketPool == null ){
            bucketPool = new bucketPool(context);
        }
        return bucketPool;
    }

    private bucketPool(Context context){
        bucketLists = new ArrayList<>();
        bucketLists.add(new BucketList("graduation from zjut ", "completing the project and submit it to pass the defence ", new Date()));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, 1, 04);
        bucketLists.add(new BucketList("Android Programing Project", "finishing the project for the android Programming laguage", calendar.getTime()));
    }
    
    public  ArrayList<BucketList> getBucketLists(){
        return bucketLists;
    }
    public BucketList getBucket(UUID id){
        for (BucketList bucketList: bucketLists){
            if (bucketList.getUuid().equals(id)){
                return bucketList;
            }
        }
        return null;
    }



    public void deleteItem(UUID id) {
        Iterator<BucketList> iterator = bucketLists.iterator();
        while (iterator.hasNext()) {
            BucketList bucketList = iterator.next();
            if (bucketList.getUuid().equals(id)) {
                iterator.remove(); // Use iterator to safely remove the item
                return;
            }
        }
    }

}
