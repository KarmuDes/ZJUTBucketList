package com.example.zjutbucketlist;

import androidx.fragment.app.Fragment;

public  class bucketListItem extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment(){
        return new bucketListItemFragment();
    }
}
