package org.androidannotations.libzoid;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LibAdapter extends BaseAdapter {

	Context context;

	String[] libTitles;

	String[] libUrls;

	TypedArray libImageIds;

	public LibAdapter(Context context) {
		this.context = context;
		initAdapter();
	}

	void initAdapter() {
		Resources resources = context.getResources();
		libTitles = resources.getStringArray(R.array.lib_titles);
		libUrls = resources.getStringArray(R.array.lib_urls);
		libImageIds = resources.obtainTypedArray(R.array.lib_image_ids);
	}

	@Override
	public int getCount() {
		return libTitles.length;
	}

	@Override
	public Lib getItem(int position) {
		return new Lib(libTitles[position], Html.fromHtml(libUrls[position]), libImageIds.getResourceId(position, -1));
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		TextView libName;
		ImageView libImage;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.lib_list_item, null);
			libName = (TextView) convertView.findViewById(R.id.lib_name);
			libImage = (ImageView) convertView.findViewById(R.id.lib_image);
			convertView.setTag(R.id.lib_name, libName);
			convertView.setTag(R.id.lib_image, libImage);
		} else {
			libName = (TextView) convertView.getTag(R.id.lib_name);
			libImage = (ImageView) convertView.getTag(R.id.lib_image);
		}

		libName.setText(libTitles[position]);
		Drawable photo = libImageIds.getDrawable(position);
		libImage.setImageDrawable(photo);

		return convertView;
	}

}
