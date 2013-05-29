package com.shuffleblab.charityquiz;

import java.util.ArrayList;
import java.util.Random;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainMenu extends Activity implements View.OnClickListener {
	Button bStart, bHighscore;
	TextView tvTitle;
	ImageView ivCloud, logo;
	Typeface tfFont;
	RelativeLayout rlMain;
	RelativeLayout rlSecondary;
	private Handler hTimer;
	private boolean hActive;
	int iCloudCounter;
	protected int topMargins;
	ArrayList<ImageView> lCloudArray = new ArrayList<ImageView>();
	int cloudCase;
	Integer[] logos = new Integer[] { R.drawable.aids, R.drawable.astmafonds,
			R.drawable.cf, R.drawable.diabetes, R.drawable.hartstichting,
			R.drawable.hiv, R.drawable.kika, R.drawable.kwf, R.drawable.ms,
			R.drawable.nierstichting, R.drawable.pinkribbon, R.drawable.rfonds,
			R.drawable.vumc };
	Integer[] clouds = new Integer[] { R.drawable.cloud, R.drawable.cloud2,
			R.drawable.cloud3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu);
		initialize();
		hTimer.post(newView);
	}

	private void initialize() {
		/* Assign value to the declared variables */
		bStart = (Button) findViewById(R.id.bStartbutton);
		bHighscore = (Button) findViewById(R.id.bHighscore);
		tvTitle = (TextView) findViewById(R.id.tvgametitle);
		rlMain = (RelativeLayout) findViewById(R.id.mainlayout);
		rlSecondary = (RelativeLayout) findViewById(R.id.rlMainButton);
		iCloudCounter = 0;
		/* create View ArrayList */
		lCloudArray = new ArrayList<ImageView>();
		/* set onTouch events */
		bStart.setOnClickListener(this);
		bHighscore.setOnClickListener(this);
		/* set Font */
		tfFont = Typeface.createFromAsset(getAssets(), "HOBOSTD.OTF");
		tvTitle.setTypeface(tfFont);
		/* create handler variables */
		hTimer = new Handler();
		hActive = false;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) { // getID from touched view
		case (R.id.bStartbutton):
			Intent startGame = new Intent(MainMenu.this, QuestionScreen.class);
			startActivity(startGame);
			break;
		case (R.id.bHighscore):
			Intent toHighScoreScreen = new Intent(MainMenu.this,
					HighScoreScreen.class);
			startActivity(toHighScoreScreen);
			break;

		}
	}

	private void animationSetUpClouds(ImageView view, int n) { // setup
																// cloudanimation
		int animation;
		switch (n) {
		case 1:
			animation = 0x7f040000;
			break;
		case 2:
			animation = 0x7f040001;

			break;
		case 3:
			animation = 0x7f040002;
			break;
		default:
			animation = 0x7f040003;
			break;
		}
		Animation translation = AnimationUtils.loadAnimation(this, animation);
		translation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				startStrobe(); // start Handler Method
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				ImageView ivCloudView = lCloudArray.get(iCloudCounter);
				removeViews(ivCloudView);
			}
		});
		ivCloud.startAnimation(translation); // start the animation

	}

	private void removeViews(ImageView ivCloudView) {
		// ((ViewManager)ivCloudView.getParent()).removeView(ivCloudView);
		ivCloudView.setImageResource(0);
		iCloudCounter++;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	private final Runnable hRunnable = new Runnable() {

		@Override
		public void run() {
			if (hActive) {
				try {
					cloudCase = new Random().nextInt(4);
					switch (cloudCase) {
					case 0:
						hTimer.postDelayed(newView, 1000); // after 1 seconds
															// start newView
															// Runnable
						hActive = false;
						break;
					case 1:
						hTimer.postDelayed(newView, 2000); // after 2 seconds
															// start newView
															// Runnable
						hActive = false;
						break;
					case 2:
						hTimer.postDelayed(newView, 3000); // after 3 seconds
															// start newView
															// Runnable
						hActive = false;
						break;
					case 3:
						hTimer.postDelayed(newView, 2500); // after 2.5 seconds
															// start newView
															// Runnable
						hActive = false;
						break;
					default:
						hTimer.postDelayed(newView, 1000); // after 1 seconds
						hActive = false;
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	};
	private final Runnable newView = new Runnable() { // create new Cloud

		@Override
		public void run() {
			try {
				Random r = new Random();
				ivCloud = new ImageView(MainMenu.this);
				logo = new ImageView(MainMenu.this);
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
				ivCloud.setImageResource(clouds[r.nextInt(2)]);
				logo.setImageResource(logos[r.nextInt(12)]);
				topMargins = (rlMain.getHeight() - rlSecondary.getHeight()) * 2;

				if (topMargins <= 0) {
					topMargins = 1;
				}
				int random = r.nextInt(topMargins);
				// ImageView
				lp.setMargins(0 - ivCloud.getWidth() * 2, random, 0, 0); // set
				// cloud
				// outside
				// the
				// screen
				// (left)
				ivCloud.setLayoutParams(lp); // set new margins
				logo.setLayoutParams(lp); // set new margins
				rlMain.addView(ivCloud); // add View to Layout
				rlMain.addView(logo); // add View to Layout
				animationSetUpClouds(ivCloud, cloudCase); // go to animation
															// setup
				animationSetUp(logo, cloudCase); // go to animation setup

				lCloudArray.add(ivCloud); // Add to arrayList
				lCloudArray.add(logo); // Add to arrayList
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		private void animationSetUp(ImageView logo, int n) {
			int animation;
			switch (n) {
			case 1:
				animation = 0x7f040000;
				break;
			case 2:
				animation = 0x7f040001;

				break;
			case 3:
				animation = 0x7f040002;
				break;
			default:
				animation = 0x7f040003;
				break;
			}
			Animation translation = AnimationUtils.loadAnimation(
					getApplicationContext(), animation);
			translation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					ImageView logoView = lCloudArray.get(iCloudCounter);
					removeViews(logoView);
				}
			});
			logo.startAnimation(translation); // start the animation
		}

	};

	private void startStrobe() {
		try {
			hActive = true;
			hTimer.post(hRunnable); // go to timer event
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



}
