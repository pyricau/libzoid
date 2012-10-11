package org.androidannotations.libzoid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LibListActivity extends Activity {

	LibAdapter adapter;

	ListView prezList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.lib_list);

		adapter = new LibAdapter(this);

		prezList = (ListView) findViewById(R.id.lib_list);

		prezList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				prezListItemClicked(adapter.getItem(position));
			}

		});

		fillList();
	}

	void prezListItemClicked(Lib prez) {
		Intent intent = new Intent(this, LibDetailzActivity.class);
		intent.putExtra("libTitle", prez.libTitle);
		intent.putExtra("libUrl", prez.libUrl);
		intent.putExtra("libImageId", prez.libImageId);
		startActivity(intent);
	}

	void fillList() {
		prezList.setAdapter(adapter);
	}

}