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
    ArrayList<String> items = new ArrayList<>();
    static ArrayList<String> originalSort = new ArrayList<>();

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
                            originalSort.add(item);
                            adapter.notifyItemInserted(adapter.listItems.size() - 1);
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


        items.add("Maitoa");

        ViewAdapter adapter = new ViewAdapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        items.add("Aurinkorasva");
        items.add("Kahvia");
        items.add("Karkkia");
        items.add("Jäätelöä");

    }

    public void addItem(View view) {
        Intent intent = new Intent(this, AddItemActivity.class);
        launchActivityForResult.launch(intent);
    }

    public void sortByTime(View view) {
        if (!originalSort.equals(items)) {
            for (String item : items) {
                if (!originalSort.contains(item)) {
                    originalSort.add(item);
                }
            }
        }

        ViewAdapter adapter = (ViewAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            for (int i = 0; i < items.size(); i++) {
                items.set(i, originalSort.get(i));
            }
            adapter.notifyItemRangeChanged(0, adapter.listItems.size());
        }
    }

    public void sortByAlphabet(View view) {
        if (!originalSort.equals(items)) {
            for (String item : items) {
                if (!originalSort.contains(item)) {
                    originalSort.add(item);
                }
            }
        }

        ViewAdapter adapter = (ViewAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            Collections.sort(items);
            adapter.notifyItemRangeChanged(0, adapter.listItems.size());
        }
    }

}