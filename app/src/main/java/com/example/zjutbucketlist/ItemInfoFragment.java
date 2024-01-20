package com.example.zjutbucketlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ItemInfoFragment extends Fragment {

    private EditText title;
    private EditText description;
    private DatePicker date;
    private Button saveChanges;
    private Button deleteItem;
    private BucketList bucketList;
    private TextView home;

    public static ItemInfoFragment newInstance(UUID id){
        Bundle  args = new Bundle();
        args.putSerializable("ITEM_ID",id);

        ItemInfoFragment fragment = new ItemInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(
            @NonNull Bundle savedInstanceState
    ){
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        UUID id = (UUID) intent.getSerializableExtra("ITEM_ID");

        id = (UUID) getArguments().getSerializable("ITEM_ID");
        bucketList = bucketPool.getbucketPool(getActivity()).getBucket(id);
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.edit_item, container, false);

        title = (EditText) v.findViewById(R.id.title);
        description = (EditText) v.findViewById(R.id.description);
        date = (DatePicker) v.findViewById(R.id.date);
        saveChanges = (Button) v.findViewById(R.id.savechanges);
        deleteItem = (Button) v.findViewById(R.id.deleteItem);

        title.setText(bucketList.gettitle());
        description.setText(bucketList.getDescription());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(bucketList.getDate());

        date.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        if (calendar.getTimeInMillis()< System.currentTimeMillis()){
            date.setMinDate(calendar.getTimeInMillis()-1000);
        }else {
            date.setMinDate(System.currentTimeMillis()-1000);
        }

        home = (TextView) v.findViewById(R.id.home);
        home.setOnClickListener(
                view -> {
                    Intent goBackHome = new Intent(getContext(), MainActivity.class);
                    startActivity(goBackHome);
                }
        );

        saveChanges.setOnClickListener(
                view -> {
                    BucketList editeBucketList = new BucketList(title.getText().toString(), description.getText().toString(), getDateFromDatePicker(date));

                    if (!title.getText().toString().matches("")
                    && !description.getText().toString().matches("")){
                        bucketList.updateBucket(editeBucketList);
                        Intent data = new Intent();
                        getActivity().setResult(getActivity().RESULT_OK, data);
                        getActivity().finish();
                    }
                }
        );

        deleteItem.setOnClickListener(view -> {
            bucketPool.getbucketPool(getActivity()).deleteItem(bucketList.getUuid());
            Intent data = new Intent();
            getActivity().setResult(getActivity().RESULT_OK ,data);
            getActivity().finish();
        });
        return v;
    }
    public static Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
