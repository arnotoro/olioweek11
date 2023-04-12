package com.example.week11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddItemActivity extends AppCompatActivity {
    EditText inputField;
    public static final String KEY_NAME = "item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);


    }

    public void goBack(View view) {
        finish();
    }

    public void addItem(View view) {
        // get the input from the EditText field and pass it back to main activity
        inputField = findViewById(R.id.inputItem);
        String input = inputField.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(KEY_NAME, input);
        setResult(RESULT_OK, intent);
        finish();
    }
}
