package com.shuffleblab.charityquiz;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HighScoreScreen extends Activity implements View.OnClickListener {
	TextView goed, fout, score;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscorescreen);
		findViewById(R.id.bMainmenubutton).setOnClickListener(this);
		initialize();
		setData();
	}

	// get data from database
	private void setData() {
		// TODO Auto-generated method stub
		try {
			Database database = new Database(this);
			database.open();
			Cursor c = database.ourDatabase.query(Database.DATABASE_TABLE,
					database.columns, null, null, null, null, null);
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				goed.setText(database.getGoed(c));
				fout.setText(database.getFout(c));
				score.setText(Integer.toString(database.getScore(c)));
			}
			c.close();
			database.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Initialize new variables with Highscore views
	private void initialize() {
		// TODO Auto-generated method stub
		goed = (TextView) findViewById(R.id.tvHighscoreRightNumber);
		fout = (TextView) findViewById(R.id.tvHighscoreWrongNumber);
		score = (TextView) findViewById(R.id.tvHighscoreHsNumber);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bMainmenubutton:
			finish();
			break;
		}
	}

}
