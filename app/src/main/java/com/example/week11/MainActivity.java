package com.example.week11;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    static ArrayList<String> items = new ArrayList<>();
    static ArrayList<String> sortedData = new ArrayList<>();

    ActivityResultLauncher<Intent> launchActivityForResult = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result != null && result.getResultCode() == RESULT_OK) {
                    if (result.getData() != null) {
                        String item = result.getData().getStringExtra(AddItemActivity.KEY_NAME);
                        ViewAdapter adapter = (ViewAdapter) recyclerView.getAdapter();
                        if (adapter != null) {
                            items.add(item);
                            sortedData.add(item);
                            adapter.notifyItemInserted(items.size());
                        }
                    }
                }
            }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        items.add("Banaanit");
        items.add("Aurinkorasva");
        items.add("Omenat");
        items.add("Kahvi");
        items.add("Jäätelö");
        items.add("Yhden tähden jallupullo");

        sortedData.addAll(items);

        ViewAdapter adapter = new ViewAdapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void addItem(View view) {
        // launch new activity to add item
        // result handled in onActivityResult
        Intent intent = new Intent(this, AddItemActivity.class);
        launchActivityForResult.launch(intent);
    }

    public void sortByTime(View view) {
        sortedData.clear();
        sortedData.addAll(items);
        System.out.println(sortedData);
        ViewAdapter adapter = (ViewAdapter) recyclerView.getAdapter();
        // sort by time, oldest first
        if (adapter != null) {
            System.out.println(sortedData);
            adapter.listItems = sortedData;
            adapter.notifyDataSetChanged();
        }
    }

    public void sortByAlphabet(View view) {
        sortedData.clear();
        sortedData.addAll(items);
        System.out.println(sortedData);
        ViewAdapter adapter = (ViewAdapter) recyclerView.getAdapter();
        // sort by alphabet, A-z
        if (adapter != null) {
            Collections.sort(sortedData);
            System.out.println(sortedData);
            adapter.listItems = sortedData;
            adapter.notifyDataSetChanged();
        }
    }

}
