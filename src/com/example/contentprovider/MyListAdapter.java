package com.example.contentprovider;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<MyItem> mListRow = null;

    public MyListAdapter (Context context, ArrayList<MyItem> _listRow) {
        mContext = context;
        mListRow = _listRow;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
        return mListRow.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
        return mListRow.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

        if (arg1 == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.list, null);
        }

        TextView rowLevel = (TextView) arg1.findViewById(R.id.idLevel);
        TextView rowIdentifier = (TextView) arg1.findViewById(R.id.idIdentifier);
        TextView rowTitle = (TextView) arg1.findViewById(R.id.idTitle);
        TextView rowDesc = (TextView) arg1.findViewById(R.id.idDesc);
        TextView rowDate = (TextView) arg1.findViewById(R.id.idDate);

        switch (mListRow.get(arg0).getLevel()) {
        case 1:
            rowLevel.setBackgroundColor(Color.parseColor("#FF999933"));
            break;
        case 2:
            rowLevel.setBackgroundColor(Color.parseColor("#FF339933"));
            break;
        case 3:
            rowLevel.setBackgroundColor(Color.parseColor("#FF003399"));
            break;
        default:
            rowLevel.setBackgroundColor(Color.TRANSPARENT);
        }

        rowIdentifier.setText(mListRow.get(arg0).getIdentifier());
        rowTitle.setText(mListRow.get(arg0).getTitle());
        rowDesc.setText(mListRow.get(arg0).getDescrition());
        rowDate.setText(mListRow.get(arg0).getDateTime());

        return arg1;
	}

}
