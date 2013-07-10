package com.example.contentprovider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnItemClickListener {
    private static final String TAG = "MainActivity";
    private static final Uri CONTENT_URI = Uri.parse("content://com.example.contentprovider.Provider");
    private ArrayList<MyItem> mArrayList;
    private ListView mListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        // データ生成しDBに保存
        asyncTask();
        
        mListView = (ListView) findViewById(R.id.listView);
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    private void showListView() {
        mArrayList = new ArrayList<MyItem>();

        Cursor c = managedQuery(CONTENT_URI, null, null, null,
                "`datetime` DESC, `_id` DESC");
        while (c.moveToNext()) {
            MyItem myitem = new MyItem();
            myitem.setLevel(c.getInt(3));
            myitem.setIdentifier(c.getString(4));
            myitem.setTitle(c.getString(1));
            myitem.setDescription(c.getString(2));
            myitem.setDateTime(c.getString(5));
            mArrayList.add(myitem);
        }

        MyListAdapter adapter = new MyListAdapter(
                this.getApplicationContext(), mArrayList);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
    }

    /**
     * 非同期でデータを取得してくる
     *
     */
    private void asyncTask() {
        RssTask goTask = new RssTask(this);
        goTask.execute();
    }
    
    /**
     * 非同期で、RSSファイルを取得しDBに保存する
     *
     */
    protected class RssTask extends AsyncTask<Object, Integer, Boolean> {
        private ProgressDialog pDialog;
        private Activity activity;

        public RssTask(Activity a) {
            activity = a;
        }

        @Override
        protected Boolean doInBackground(Object... arg0) {
            // TODO Auto-generated method stub
        	
        	ArrayList<String> randomList = new ArrayList<String>(20);
        	
        	for (int i = 0; i < 20; i++) {
        		randomList.add(RandomStringUtils.randomAlphabetic(12));
        	}
        	for (String val: randomList) {
        		android.util.Log.v(TAG, val);
        	}
        	
            if (randomList.size() <= 0) {
                return false;
            }

            ContentValues values = new ContentValues();

            final String[] columnQuery = new String[] { "_id" };
            SimpleDateFormat today = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            for (int i = 0; i < randomList.size(); i++) {
                // DBに登録
                values.put("title", randomList.get(i));
                values.put("level", RandomStringUtils.random(1, "12345678"));
                values.put("identifier", RandomStringUtils.randomAlphabetic(2));
                values.put("datetime", today.format(new Date()));
                values.put("description", RandomStringUtils.random(20,
                		"あいうえおかくけこさしすせそたちつてとなにぬねのはひふへほ"));
                getContentResolver().insert(CONTENT_URI, values);
            }

            return true;
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(activity);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage(getString(R.string.message_progress));
            pDialog.setIndeterminate(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            if (result) {
                showListView();
            }

            pDialog.dismiss();

            // エラーだった場合
            if (!result) {
                Toast.makeText(getApplicationContext(),
                        getString(R.string.message_error), Toast.LENGTH_LONG)
                        .show();
            }

        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();

            pDialog.dismiss();
        }

    }

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
	}
}
