package com.wits.wistronthermos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wits.wistronthermos.R;

/**
 * Created by Devon on 2016/6/29.
 */
public class SimpleAdapter extends ArrayAdapter<String> {
	public SimpleAdapter(Context context) {
		super(context, 0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		View v = convertView;
		final Holder holder;
		if(v == null){
			v = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_row, null);
			holder = new Holder();
			holder.row_name = (TextView) v.findViewById(R.id.row_name);
			v.setTag(holder);
		}else{
			holder = (Holder) v.getTag();
		}

		holder.row_name.setText(getItem(position));

		return v;
	}

	/**
	 * View holder for the views we need access to
	 */
	private class Holder {
		public TextView row_name;
	}
}
