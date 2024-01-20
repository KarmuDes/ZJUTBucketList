package com.example.zjutbucketlist;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class bucketListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private BucketListAdapter mbucketListAdapter;

    private void updateUI(){
        bucketPool bucketpool = bucketPool.getbucketPool(getActivity());
        ArrayList<BucketList> bucketLists = bucketpool.getBucketLists();
        bucketLists.sort(new sortTaskbyTime());

        if (mbucketListAdapter == null ){
            mbucketListAdapter = new BucketListAdapter(bucketLists);
        }else {
            mbucketListAdapter.notifyDataSetChanged();
        }
        mRecyclerView.setAdapter(mbucketListAdapter);
    }
    @Override
    public void onResume(){
        super.onResume();
        updateUI();
    }
    @NonNull
    @Nullable
   public View onCreateView(
           @NonNull LayoutInflater inflater,
           @Nullable ViewGroup container,
           @Nullable Bundle savedInstanceState
    ){
        View v = inflater.inflate(R.layout.main_activity_fragment,container, false);
        Button btn = (Button) v.findViewById(R.id.add);
        btn.setOnClickListener(view -> {
            Intent addItemIntent = new Intent(getContext(), bucketListItem.class);
            startActivity(addItemIntent);
        });

        mRecyclerView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return v;
    }
    public static class sortTaskbyTime implements Comparator<BucketList>{
        @Override
        public int compare(BucketList bucketList, BucketList bucketList1){
            return bucketList.getDate().compareTo(bucketList1.getDate());
        }
    }
    private class BucketHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private BucketList bucketList;
        private TextView title;

        private TextView subTitle;
        private TextView time;
        private CheckBox isDone;

        BucketHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.todo_item, parent, false));

            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.textViewsubject);
            subTitle = (TextView) itemView.findViewById(R.id.textViewsub_subject);
            time = (TextView) itemView.findViewById(R.id.time);
            isDone = (CheckBox) itemView.findViewById(R.id.checkBoxTodo);

            isDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    bucketList.setComplete(b);
                    if (b){
                        title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    }else{
                        title.setPaintFlags(title.getPaintFlags() & ~ Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                }
            });
        }

        public void bind(BucketList bucketList){
            this.bucketList = bucketList;
            title.setText(this.bucketList.gettitle());
            subTitle.setText(this.bucketList.getDescription());
            SimpleDateFormat sm = new SimpleDateFormat("YYYY-MM-DD");
            String date = sm.format(this.bucketList.getDate());
            time.setText(date);
            isDone.setChecked(this.bucketList.isComplete());
        }

        @Override
        public void onClick(View view){
            Intent intent = BucketListPagerActivity.newIntent(getContext(),this.bucketList.getUuid());
            startActivity(intent);
        }
    }
    private class BucketListAdapter extends RecyclerView.Adapter<BucketHolder>{
        private ArrayList<BucketList> bucketLists;

        public BucketListAdapter(ArrayList<BucketList> bucketList) {
            bucketLists = bucketList;
        }
        @Override
        @Nullable
        public BucketHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new BucketHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull BucketHolder holder, int position){
            BucketList bucketList= bucketLists.get(position);
            holder.bind(bucketList);
        }
        @Override
        public int getItemCount(){
            return bucketLists.size();
        }
    }
}
