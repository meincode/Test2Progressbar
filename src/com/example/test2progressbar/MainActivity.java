package com.example.test2progressbar;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;

public class MainActivity extends Activity {
	private ProgressBar progress;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		progress = (ProgressBar) findViewById(R.id.progress);
		Button ImageView01 = (Button) findViewById(R.id.btn);
		ImageView01.setOnClickListener(mCorkyListener);
		Button ImageView02 = (Button) findViewById(R.id.btnCancel);
		ImageView02.setOnClickListener(mCorkyListener);

	}

	private OnClickListener mCorkyListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			ProgressTask task = new ProgressTask();
			switch (v.getId()) {
			case R.id.btn:
				task.execute(10);
				break;
			case R.id.btnCancel:
				task.cancel(true);
				break;
			}

		}

	};

	// /////////////////////////////////////////////////
	class ProgressTask extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected void onPreExecute() {
			// initialize the progress bar
			// set maximum progress to 100.
			progress.setMax(100);

		}

		@Override
		protected void onCancelled() {
			// stop the progress
			progress.setMax(0);

		}

		@Override
		protected Void doInBackground(Integer... params) {
			// get the initial starting value
			int start = params[0];
			// increment the progress
			for (int i = start; i <= 100; i += 5) {
				try {
					boolean cancelled = isCancelled();
					// if async task is not cancelled, update the progress
					if (!cancelled) {
						publishProgress(i);
						SystemClock.sleep(100);

					}

				} catch (Exception e) {
					Log.e("Error", e.toString());
				}

			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// increment progress bar by progress value
			progress.setProgress(values[0]);

		}

		@Override
		protected void onPostExecute(Void result) {
			// async task finished
			Log.v("Progress", "Finished");
		}

	}
}