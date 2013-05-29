package com.shuffleblab.charityquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOver extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gameover);
		findViewById(R.id.bToMainMenu).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bToMainMenu:
			Intent toGameOverScreen = new Intent(GameOver.this,
					MainMenu.class);
			startActivity(toGameOverScreen);
			break;
		}
	}
}