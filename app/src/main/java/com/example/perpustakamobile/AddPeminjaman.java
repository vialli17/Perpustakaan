package com.example.perpustakamobile;

import androidx.activity.OnBackPressedDispatcherOwner;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddPeminjaman extends AppCompatActivity {

    DBHelper dbHelper;
    TextView status;
    Button proses;
    EditText ID, Nama, Judul, Pinjam,Kembali, txStatus;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_peminjaman);

        dbHelper = new DBHelper(this);

        id = getIntent().getLongExtra(DBHelper.id_perpus,0);

        ID = (EditText)findViewById(R.id.IDadd);
        Nama = (EditText)findViewById(R.id.AddNamaAnggo);
        Judul= (EditText)findViewById(R.id.Addjdlbk);
        Pinjam = (EditText)findViewById(R.id.Add_tgl_pinjam);
        Kembali = (EditText)findViewById(R.id.AddPegembalian);
        txStatus = (EditText)findViewById(R.id.Addstatus);

        status = (TextView)findViewById(R.id.txv_status);
        proses = (Button)findViewById(R.id.btn_Prosess);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        getData();

        Kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDateDialog();
            }
        });
        proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proseskembali();
            }
        });

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);

    }

    private void proseskembali() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddPeminjaman.this);
        builder.setMessage("Proses pemgembalian buku ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Proses", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String idpinjaman = ID.getText().toString().trim();
                String kembalianbuku = "Dikembalikan";

                ContentValues values = new ContentValues();

                values.put(DBHelper.Status_buku, kembalianbuku);
                dbHelper.updateData(values,id);
                Toast.makeText(AddPeminjaman.this, "Prosess pengembalian buku berhasil" , Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void ShowDateDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar Date = Calendar.getInstance();
                Date.set(year, month, dayOfMonth);
                Kembali.setText(dateFormat.format(Date.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getData() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        String tglpinjam = simpleDateFormat.format(calendar.getTime());
        Pinjam.setText(tglpinjam);

        Cursor cursor = dbHelper.oneData(id);
        if (cursor.moveToFirst()){
            String pinjamId = cursor.getString(cursor.getColumnIndex(DBHelper.id_perpus));
            String namapinjam = cursor.getString(cursor.getColumnIndex(DBHelper.nama_pinjam));
            String judulbuku = cursor.getString(cursor.getColumnIndex(DBHelper.judul_buku));
            String pinjambuku = cursor.getString(cursor.getColumnIndex(DBHelper.pinjam_buku));
            String kembalian_buku = cursor.getString(cursor.getColumnIndex(DBHelper.Kembalian_buku));
            String statusbuku = cursor.getString(cursor.getColumnIndex(DBHelper.Status_buku));

            ID.setText(pinjamId);
            Nama.setText(namapinjam);
            Judul.setText(judulbuku);
            Pinjam.setText(pinjambuku);
            Kembali.setText(kembalian_buku);
            txStatus.setText(statusbuku);

            if (ID.equals("")){
                status.setVisibility(View.GONE);
                txStatus.setVisibility(View.GONE);
                proses.setVisibility(View.GONE);
            }else {
                status.setVisibility(View.VISIBLE);
                txStatus.setVisibility(View.VISIBLE);
                proses.setVisibility(View.VISIBLE);
            }

            if (statusbuku.equals("Pinjam")){
                proses.setVisibility(View.VISIBLE);
            }else
                proses.setVisibility(View.GONE);
                Nama.setEnabled(true);
                Judul.setEnabled(true);
                Pinjam.setEnabled(false);
                Kembali.setEnabled(true);
                txStatus.setEnabled(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        String idpinjam = ID.getText().toString().trim();
        String status = txStatus.getText().toString().trim();

        MenuItem itemdelete = menu.findItem(R.id.action_delete);
        MenuItem itemclear = menu.findItem(R.id.action_clear);
        MenuItem itemsaved = menu.findItem(R.id.action_save);

        if (idpinjam.equals("")){
            itemdelete.setVisible(false);
            itemclear.setVisible(true);
        }else {
            itemdelete.setVisible(false);
            itemclear.setVisible(false);
        }

        if (status.equals("Dikembalikan")){
            itemsaved.setVisible(false);
            itemdelete.setVisible(true);
            itemclear.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_save :
                insertUpdate();
        }
        switch (item.getItemId()){
            case R.id.action_clear:
                Nama.setText("");
                Judul.setText("");
                Kembali.setText("");
        }
        switch (item.getItemId()){
            case R.id.action_delete:
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddPeminjaman.this);
                builder.setMessage("Data ini mau di hapus ? ");
                builder.setCancelable(true);
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteData(id);
                        Toast.makeText(AddPeminjaman.this,"Terhapus", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();;
        }
        return super.onOptionsItemSelected(item);
    }

    public void insertUpdate(){
        String IDPInjam = ID.getText().toString().trim();
        String Nama_Pinjam = Nama.getText().toString().trim();
        String Judul_buku = Judul.getText().toString().trim();
        String Tgl_Pinjam = Pinjam.getText().toString().trim();
        String Tgl_Kembali = Kembali.getText().toString().trim();
        String Status = "Pinjam";

        ContentValues values = new ContentValues();
        values.put(DBHelper.nama_pinjam, Nama_Pinjam);
        values.put(DBHelper.judul_buku,Judul_buku);
        values.put(DBHelper.Kembalian_buku,Tgl_Kembali);
        values.put(DBHelper.Status_buku,Status);

        if (Nama_Pinjam.equals("") || Judul_buku.equals("") || Tgl_Kembali.equals("")){
            Toast.makeText(AddPeminjaman.this, "isi data yang lengkap" ,Toast.LENGTH_SHORT).show();
        }else if (Nama_Pinjam.length()<8 || Nama_Pinjam.length()>20) {
            Toast.makeText(AddPeminjaman.this, "Nama maksimal 8 - 20 character ", Toast.LENGTH_SHORT).show();
        } else {
            if (IDPInjam.equals("")){
                values.put(DBHelper.pinjam_buku, Tgl_Pinjam);
                dbHelper.insertData(values);
            }else {
                dbHelper.updateData(values,id);
            }
            Toast.makeText(AddPeminjaman.this,"Data Tersimpan", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}