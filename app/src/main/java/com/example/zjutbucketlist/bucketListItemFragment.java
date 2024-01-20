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

import java.util.ArrayList;
import java.util.Calendar;

public class bucketListItemFragment extends Fragment {

    private EditText title;

    private EditText description;

    private DatePicker date;

    private Button saveButton;
    private TextView home;
    private ArrayList<BucketList> bucketLists;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bucketPool bucketPool  = com.example.zjutbucketlist.bucketPool.getbucketPool(getActivity()) ;
        bucketLists = bucketPool.getBucketLists();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_item_fragment, container, false);


        title = (EditText) v.findViewById(R.id.title);
        description = (EditText) v.findViewById(R.id.description);
        date = (DatePicker) v.findViewById(R.id.date);
        date.setMinDate(System.currentTimeMillis() - 1000);
        saveButton = (Button) v.findViewById(R.id.addButton);


        home = (TextView) v.findViewById(R.id.home);
        home.setOnClickListener(
                view -> {
                    Intent home = new Intent(getContext(), MainActivity.class);
                    startActivity(home);
                }
        );
        saveButton.setOnClickListener(view->{
            if( !title.getText().toString().matches("")
                && !description.getText().toString().matches("")) {

                BucketList item = new BucketList(title.getText().toString(), description.getText().toString(), getDate(date));
                bucketLists.add(item);
                Intent intent = new Intent();
                getActivity().setResult(getActivity().RESULT_OK, intent);
                getActivity().finish();
            }
        });




        return v;
    }

    public static java.util.Date getDate(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }
}
