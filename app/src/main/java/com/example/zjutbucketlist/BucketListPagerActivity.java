package com.example.zjutbucketlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.UUID;

public class BucketListPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ArrayList<BucketList> bucketLists;

    public static Intent newIntent(Context packageContext, UUID id){
        Intent intent = new Intent(packageContext,
                BucketListPagerActivity.class);
        intent.putExtra("ITEM_ID", id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_bucket_list_pager);

        viewPager = (ViewPager) findViewById(R.id.bucket_list_pager);
        bucketLists = bucketPool.getbucketPool(this).getBucketLists();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentStatePagerAdapter fragmentStatePagerAdapter = new FragmentStatePagerAdapter(fragmentManager) {


            @NonNull
            @Override
            public Fragment getItem(int position) {
                UUID id = bucketLists.get(position).getUuid();
                ItemInfoFragment itemInfoFragment = ItemInfoFragment.newInstance(id);
                return itemInfoFragment;
            }

            @Override
            public int getCount() {
                return bucketLists.size();
            }
        };

        viewPager.setAdapter(fragmentStatePagerAdapter);

        viewPager.setOffscreenPageLimit(10);

        UUID id = (UUID) getIntent().getSerializableExtra("ITEM_ID");
        for (int i = 0; i<bucketLists.size(); i++){
            if (bucketLists.get(i).getUuid().equals(id)){
                viewPager.setCurrentItem(i);
            }
        }

    }
}