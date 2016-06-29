package com.wits.wistronthermos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wits.wistronthermos.R;

/**
 * Created by Devon on 2016/6/29.
 */
public class SimpleImageAdapter extends ArrayAdapter<SimpleImageAdapter.SampleItem> {
	public SimpleImageAdapter(Context context) {
		super(context, 0);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		View v = convertView;
		final Holder holder;
		if(v == null){
			v = LayoutInflater.from(getContext()).inflate(R.layout.simple_image_list_row, null);
			holder = new Holder();
			holder.row_title = (TextView) v.findViewById(R.id.row_title);
			holder.row_icon = (ImageView)v.findViewById(R.id.row_icon);
			v.setTag(holder);
		}else{
			holder = (Holder) v.getTag();
		}

		holder.row_title.setText(getItem(position).text);
		if ( position ==1 ) {
			holder.row_icon.setVisibility(View.GONE);
		} else {
			holder.row_icon.setVisibility(View.VISIBLE);
			holder.row_icon.setImageResource(getItem(position).iconRes);
		}

		return v;
	}
	/**
	 * View holder for the views we need access to
	 */
	private class Holder {
		public TextView row_title;
		public ImageView row_icon;
	}

	public static class SampleItem {
		public String text;
		public int iconRes;

		public SampleItem(String text, int iconRes) {
			this.text = text;
			this.iconRes = iconRes;
		}
	}
}

