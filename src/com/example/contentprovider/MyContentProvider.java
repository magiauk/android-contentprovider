package com.example.contentprovider;

import java.io.File;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyContentProvider extends ContentProvider {

    private static final String TAG = "MyContentProvider";
    private static final String DATABASE = "sample.db";
    public static final String AUTHORITY = "com.example.contentprovider.MyContentProvider";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/items");
    
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		
        ContentValues values;

        // insertする値があるかどうか
        if (arg1 == null) {
            values = new ContentValues();
        } else {
            values = arg1;
        }
        
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File(
                "/data/data/" + getContext().getPackageName() + "/databases/",
                DATABASE), null);
        long id = db.insert("items", null, values);
        db.close();
        android.util.Log.d(TAG, "insert value:" + values.toString());

        // データがinsertされなければ例外を
        if (id < 1) {
            throw new SQLException();
        }

        Uri uri = ContentUris.withAppendedId(CONTENT_URI, id);
        getContext().getContentResolver().notifyChange(uri, null);

        return uri;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub

        File dbDir = new File("/data/data/" + getContext().getPackageName()
                + "/databases/");
        dbDir.mkdirs();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File(
                "/data/data/" + getContext().getPackageName() + "/databases/",
                DATABASE), null);
        final String sql = "" +
                "CREATE TABLE IF NOT EXISTS `items`(" +
                " `_id` INTEGER PRIMARY KEY AUTOINCREMENT," +
                " `title` VARCHAR(255), `description` TEXT," +
                " `level` INTEGER, `identifier` TEXT," +
                " `datetime` VARCHAR(255), `created_at` INTEGER" +
                ")";

        db.execSQL(sql);
        db.close();

        return true;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(new File(
                "/data/data/" + getContext().getPackageName() + "/databases/",
                DATABASE), null);
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables("items");
        Cursor c = qb.query(db, arg1, arg2, arg3, null, null, arg4);

        return c;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

}
