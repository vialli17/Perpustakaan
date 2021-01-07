package com.example.perpustakamobile;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import static com.example.perpustakamobile.R.*;

public class CustomeCusorAdapter extends CursorAdapter {


    private LayoutInflater layoutInflater;
    private SparseBooleanArray selectedItem;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomeCusorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        selectedItem = new SparseBooleanArray();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = layoutInflater.inflate(layout.data_perpustakaan, viewGroup, false);
        Holder holder = new Holder();

        holder.IDbuku = (TextView)view.findViewById(id.IDList);
        holder.judulBuku = (TextView)view.findViewById(id.ListJudul);
        holder.ListNama = (TextView)view.findViewById(id.Listname);
        holder.listPinjam = (TextView)view.findViewById(id.ListTgglpinjam);
        holder.liststatus = (TextView)view.findViewById(id.ListStatus);

        view.setTag(holder);
        return view;

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder = (Holder)view.getTag();

        holder.IDbuku.setText(cursor.getString(cursor.getColumnIndex(DBHelper.id_perpus)));
        holder.judulBuku.setText(cursor.getString(cursor.getColumnIndex(DBHelper.judul_buku)));
        holder.ListNama.setText(cursor.getString(cursor.getColumnIndex(DBHelper.nama_pinjam)));
        holder.listPinjam.setText(cursor.getString(cursor.getColumnIndex(DBHelper.pinjam_buku)) + " - " + cursor.getString(cursor.getColumnIndex(DBHelper.Kembalian_buku)));
        holder.liststatus.setText(cursor.getString(cursor.getColumnIndex(DBHelper.Status_buku)));

    }


    class Holder{
        TextView IDbuku;
        TextView judulBuku;
        TextView ListNama;
        TextView listPinjam;
        TextView liststatus;



    }
}
