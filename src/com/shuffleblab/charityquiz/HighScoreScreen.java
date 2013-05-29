package com.shuffleblab.charityquiz;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class HighScoreScreen extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscorescreen);
		findViewById(R.id.bMainmenubutton).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.bMainmenubutton:
			finish();
			break;
		}
	}

}
