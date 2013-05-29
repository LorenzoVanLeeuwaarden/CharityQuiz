package com.shuffleblab.charityquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AnswerScreen extends Activity implements View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answerscreen);
		findViewById(R.id.bNextLevel).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bNextLevel:
			Intent toGameOverScreen = new Intent(AnswerScreen.this,
					GameOver.class);
			startActivity(toGameOverScreen);
			break;
		}
	}
}