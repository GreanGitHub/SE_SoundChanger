package com.example.soundchanger;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Main extends Activity {

	private static final long POLL_INTERVAL = 500;
	TextView textv;
	TextView textA;
	Soundmeter sound;
	Thread runner;
	Handler hand = new Handler();
	boolean Running = false;

	private Runnable Task = new Runnable() {
		public void run() {

			double dB = soundDB();
			/*Toast.makeText(getApplicationContext(), Double.toString(dB),
					Toast.LENGTH_SHORT).show();*/
			updateDisplay(dB);
			hand.postDelayed(Task, POLL_INTERVAL);
		}
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		/*Toast.makeText(getApplicationContext(), "onDestroy", Toast.LENGTH_SHORT).show();*/
		Process.killProcess(Process.myPid());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textv = (TextView) findViewById(R.id.status);
		textA = (TextView) findViewById(R.id.ampli);
		sound = new Soundmeter();
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!Running) {
					Running = true;
					start();
				}
			}
		});
		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (Running) {
					Running = false;
					stop();
				}
			}
		});

	}

	
	public void test(){
		
	
		//test methode by ball while crate repo
	}
	
	public void stop() {
		sound.stop();
		hand.removeCallbacks(Task);
		Running = false;
		double a = 0;
		updateDisplay(a);
		updateDisAmp(a);
	}

	public void updateDisAmp(double amp) {
		textA.setText(Double.toString(amp));
	}

	public void updateDisplay(double dB) {
		textv.setText(Double.toString(dB));
	}

	public double soundDB() {
		double cAmp = sound.getAmplitude();
		updateDisAmp(cAmp);
		cAmp = sound.soundDb(cAmp);
		return cAmp;
	}

	public double getAmp() {
		return sound.getAmplitude();
	}

	public void start() {
		sound.start();
		hand.postDelayed(Task, POLL_INTERVAL);

	}

};
