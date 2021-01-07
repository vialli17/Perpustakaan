package com.example.perpustakamobile;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    DBHelper dbHelper;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(MainActivity.this, AddPeminjaman.class));
            }
        });

        dbHelper = new DBHelper(this);
        listView = (ListView)findViewById(R.id.list_pepus);
        listView.setOnItemClickListener(this);

        setupListview();
    }

    private void setupListview() {
        Cursor cursor = dbHelper.allData();
        CustomeCusorAdapter customeCusorAdapter = new CustomeCusorAdapter(this,cursor,1);
        listView.setAdapter(customeCusorAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.maps) {
            Intent intent = new Intent(MainActivity.this, maps_perpustakan.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TextView GETid  = (TextView)view.findViewById(R.id.IDList);
        final long id = Long.parseLong(GETid.getText().toString());
        Cursor cursor = dbHelper.oneData(id);
        cursor.moveToFirst();

        Intent peminjaman_id = new Intent(MainActivity.this,AddPeminjaman.class);
        peminjaman_id.putExtra(DBHelper.id_perpus,id);
        startActivity(peminjaman_id);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupListview();
    }
}