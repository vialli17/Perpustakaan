package com.example.perpustakamobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_Name = "Perpustakaan_DB";
    public static final int DB_VERSION = 2;

    public static final String TABLE_NAME = "tabel_perpus";

    public static final String id_perpus = "_id";
    public static final String nama_pinjam = "Nama_pinjamTEXTTEXT";
    public static final String judul_buku = "Judul_bukuTEXTTEXT";
    public static final String pinjam_buku = "Tgl_pinjamTEXTTEXT";
    public static final String Kembalian_buku = "Tgl_KembaliTEXTTEXT";
    public static final String Status_buku = "StatusTEXTTEXT";


    public static final String createPerpus = "CREATE TABLE IF NOT EXISTS "
            + TABLE_NAME + " (" + id_perpus + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + nama_pinjam + " TEXT," + judul_buku + " TEXT, " + pinjam_buku +
            " TEXT, " + Kembalian_buku + " TEXT, " + Status_buku + " TEXT)";

    public static final String dropPerpustakaan = "DROP TABLE IF EXISTS " +
            TABLE_NAME;

    private SQLiteDatabase db;


    public DBHelper(Context context) {
        super(context, DB_Name, null, DB_VERSION);
        db = getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createPerpus);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropPerpustakaan);
        onCreate(db);

    }

    public Cursor allData() {
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + id_perpus + " DESC ", null);
        return cur;
    }

    public Cursor oneData(long id) {
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + id_perpus + "=" + id, null);
        return cur;
    }

    public void insertData(ContentValues values) {
        db.insert(TABLE_NAME, null, values);
    }

    public void updateData(ContentValues values, long id) {
        db.update(TABLE_NAME, values, id_perpus + "=" + id, null);
    }

    public void deleteData(long id) {
        db.delete(TABLE_NAME, id_perpus + "=" + id, null);
    }

}
