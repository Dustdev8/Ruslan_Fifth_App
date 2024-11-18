package com.example.ruslanfifthapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText filterInput;
    private ListView currencyListView;
    private ArrayAdapter<String> adapter;
    private List<String> currencyList = new ArrayList<>();
    private List<String> filteredList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        filterInput = findViewById(R.id.filterInput);
        currencyListView = findViewById(R.id.currencyListView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredList);
        currencyListView.setAdapter(adapter);


        filterInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterCurrencies(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        new DataLoader(data -> {
            if (data != null) {
                currencyList.clear();
                currencyList.addAll(Parser.parseXML(data));
                filteredList.clear();
                filteredList.addAll(currencyList);
                adapter.notifyDataSetChanged();
            }
        }).execute("http://www.floatrates.com/daily/usd.xml");
    }

    private void filterCurrencies(String query) {
        filteredList.clear();
        for (String currency : currencyList) {
            if (currency.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(currency);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
