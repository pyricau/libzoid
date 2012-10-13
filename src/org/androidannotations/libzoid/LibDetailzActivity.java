package org.androidannotations.libzoid;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LibDetailzActivity extends Activity {

	String libTitle;

	CharSequence libUrl;

	int libImageId;

	Animation slideOutToLeft;

	Animation slideInToRight;

	Animation slideOutToBottom;

	Animation slideInToTop;

	TextView libNameTextView;

	TextView libUrlTextView;

	View startProgressButton;

	ProgressBar progressBar;

	ImageView libImageView;

	// Ma longue méthode d'init
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		libTitle = intent.getStringExtra("libTitle");
		libUrl = intent.getCharSequenceExtra("libUrl");
		libImageId = intent.getIntExtra("libImageId", -1);

		slideOutToLeft = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_left);
		slideInToRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_to_right);
		slideOutToBottom = AnimationUtils.loadAnimation(this, R.anim.slide_out_to_bottom);
		slideInToTop = AnimationUtils.loadAnimation(this, R.anim.slide_in_to_top);

		setContentView(R.layout.lib_detailz);

		// Tu pars du Nord Ouest pour aller au Sud Est, et tu cast !
		libNameTextView = (TextView) findViewById(R.id.libNameTextView);
		libUrlTextView = (TextView) findViewById(R.id.libUrlTextView);
		startProgressButton = findViewById(R.id.startProgressButton);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		libImageView = (ImageView) findViewById(R.id.libImageView);

		startProgressButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				startProgressButtonClicked();
			}

		});

		findViewById(R.id.libNameTextView).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				showHidePhoto();
			}

		});

		findViewById(R.id.libImageView).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				showHidePhoto();
			}

		});

		findViewById(R.id.libUrlTextView).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				libUrlTextViewClicked();
			}
		});

		init();
	}

	void startProgressButtonClicked() {
		startProgressButton.startAnimation(slideOutToBottom);
		startProgressButton.setVisibility(View.GONE);
		startProgressButton.setClickable(false);
		computeAnswerToUltimateQuestion();
	}

	void showHidePhoto() {
		if (libNameTextView.getVisibility() == View.VISIBLE) {
			libNameTextView.startAnimation(slideOutToLeft);
			libNameTextView.setVisibility(View.GONE);
		} else {
			libNameTextView.startAnimation(slideInToRight);
			libNameTextView.setVisibility(View.VISIBLE);
		}
	}

	void libUrlTextViewClicked() {
		Uri libUri = Uri.parse(libUrl.toString());
		Intent urlIntent = new Intent(Intent.ACTION_VIEW, libUri);
		startActivity(urlIntent);
	}

	void init() {
		libNameTextView.setText(libTitle);
		libUrlTextView.setText(libUrl);
		libImageView.setImageDrawable(getResources().getDrawable(libImageId));
	}

	void computeAnswerToUltimateQuestion() {

		// AsyncTask<TOO, MUCH, PARAMS>
		AsyncTask<Void, Integer, Void> task = new AsyncTask<Void, Integer, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				for (int i = 0; i <= 100; i++) {
					publishProgress(i);
					try {
						Thread.sleep(50);
					} catch (InterruptedException e) {
						Log.e("Libzoid", "Quelqu'un m'a volé mon slip !", e);
					}
				}
				return null;
			}

			@Override
			protected void onProgressUpdate(Integer... values) {
				Integer progress = values[0];
				updateProgress(progress);
			}

			@Override
			protected void onPostExecute(Void result) {
				countDone();
			}

		};

		task.execute();
	}

	void updateProgress(int progress) {
		progressBar.setProgress(progress);
	}

	void countDone() {
		if (!isFinishing()) {
			startProgressButton.setVisibility(View.VISIBLE);
			startProgressButton.setClickable(true);
			startProgressButton.startAnimation(slideInToTop);
			View toastView = View.inflate(getApplicationContext(), R.layout.answer, null);

			Toast toast = new Toast(getApplicationContext());
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(toastView);
			toast.show();
		}
	}

}