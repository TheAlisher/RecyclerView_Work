package com.example.homework3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    static final String GET_TEXT_KEY = "get_text_key";

    private static int CALCULATOR_ACTIVITY_CODE = 15;

    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MainAdapter();

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        if (intent != null) {
            String get = intent.getStringExtra(GET_TEXT_KEY);
            adapter.data.add(get);
            adapter.notifyDataSetChanged();
        }

        Button share = findViewById(R.id.shareButton);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentShare = new Intent();
                intentShare.setAction(Intent.ACTION_SEND);
                intentShare.setType("text/plain");
                intentShare.putExtra(Intent.EXTRA_TEXT, adapter.toString());
                if (intentShare.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentShare);
                }
            }
        });

        Button toCalculator = findViewById(R.id.toCalculatorActivityButton);
        toCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivityForResult(intent, CALCULATOR_ACTIVITY_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CALCULATOR_ACTIVITY_CODE) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }
}
